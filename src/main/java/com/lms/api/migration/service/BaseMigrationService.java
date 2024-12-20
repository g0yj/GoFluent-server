package com.lms.api.migration.service;

import com.lms.api.migration.mapping.FieldMapping;
import com.lms.api.migration.mapping.FieldMappingProvider;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvMalformedLineException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;

@SuppressWarnings("unchecked")
@Slf4j
@RequiredArgsConstructor
public abstract class BaseMigrationService {

  private static final String FILE_NAME_SEPARATOR = ";";
  private static final char QUOTE_CHAR = '"'; // 인용 부호 문자
  private static final char SEPARATOR = ','; // 필드 구분자

  protected final JdbcTemplate jdbcTemplate;

  // 레코드 카운터 추가
  private int totalRecordCount = 0;
  private int failedRecordCount = 0;
  private int batchSize = 1000;

  /**
   * 배치 처리
   */
  public void migrationBatch() {
    log.info("{} 테이블 배치 마이그레이션 시작", getTableName());
    String[] fileNames = getCsvFileName().split(FILE_NAME_SEPARATOR);

    int overallTotalRecordCount = 0;

    // 전체 레코드 수 계산
    for (String fileName : fileNames) {
      String filePath = getDirPath() + fileName;
      try (BufferedReader lineCounter = new BufferedReader(new FileReader(filePath))) {
        while (lineCounter.readLine() != null) {
          overallTotalRecordCount++;
        }
      } catch (IOException e) {
        log.error("파일을 읽는 중 오류 발생: {}", filePath, e);
        throw new RuntimeException("파일을 읽는 중 오류 발생", e);
      }
    }

    int globalRecordCount = 0;

//    truncateTables(getTruncateTableName());
    truncateTable();

    for (String fileName : fileNames) {
      String filePath = getDirPath() + fileName;
      log.info("{} CSV 파일 로딩", filePath);

      List<Object[]> batchArgs = new ArrayList<>();

      try (FileReader fileReader = new FileReader(filePath);
          CSVReader reader = new CSVReaderBuilder(fileReader)
              .withCSVParser(new CSVParserBuilder()
                  .withSeparator(SEPARATOR)
                  .withQuoteChar(QUOTE_CHAR)
                  .withStrictQuotes(false)
                  .build())
              .build()) {

        String[] row;
        FieldMappingProvider mappingProvider = getFieldMappingProvider();
        int fileRecordCount = 0;
        int totalFileRecordCount = 0;

        // 현재 파일의 총 레코드 수 계산
        try (BufferedReader lineCounter = new BufferedReader(new FileReader(filePath))) {
          while (lineCounter.readLine() != null) {
            totalFileRecordCount++;
          }
        }

        while ((row = reader.readNext()) != null) {
          fileRecordCount++;
          globalRecordCount++;
          totalRecordCount++;

          try {
            Object[] args = prepareArgs(row, mappingProvider);
            batchArgs.add(args);

            if (batchArgs.size() >= batchSize) {
              processBatch(batchArgs);
              batchArgs.clear();
            }
          } catch (Exception e) {
            log.error("행 처리 오류 {}, 해당 행을 건너뜁니다: {}", globalRecordCount, e.getMessage());
            failedRecordCount++;  // 실패한 레코드 수 증가
          }

          // 각 행이 처리될 때마다 진행률 출력
//          printProgress(fileRecordCount, totalFileRecordCount, globalRecordCount,
//              overallTotalRecordCount, fileName);
        }

        if (!batchArgs.isEmpty()) {
          processBatch(batchArgs);
        }
      } catch (CsvMalformedLineException e) {
        log.error("CSV 파일 오류", e);
      } catch (Exception e) {
        log.error("예상치 못한 오류 발생", e);
        throw new RuntimeException("예상치 못한 오류 발생", e);
      }
    }

    // 모든 파일 처리가 완료된 후 최종 진행률 출력
    printProgress(totalRecordCount, overallTotalRecordCount, totalRecordCount,
        overallTotalRecordCount, fileNames);
  }

  private void printProgress(int fileRecordCount, int totalFileRecordCount, int globalRecordCount,
      int overallTotalRecordCount, String[] fileNames) {
    double filePercentCompleted = (fileRecordCount * 100.0) / totalFileRecordCount;
    double overallPercentCompleted = (globalRecordCount * 100.0) / overallTotalRecordCount;

    // 실패한 레코드 수와 실패율 계산
    double failureRate =
        (totalRecordCount > 0) ? (failedRecordCount * 100.0) / totalRecordCount : 0.0;

    // 진행률 및 실패율을 포함한 메시지 출력
    String line = "################################################################################";
    log.info("\n{}\n파일명: {}\n대상 테이블: {}\n파일 진행률: {}/{} ({}%)\n전체 진행률: {}%\n실패: {}\n실패율: {}%\n{}\n",
        line,
        Arrays.toString(fileNames),
        getTableName(),
        fileRecordCount, totalFileRecordCount, String.format("%.2f", filePercentCompleted),
        String.format("%.2f", overallPercentCompleted),
        failedRecordCount, String.format("%.2f", failureRate),
        line);
  }

  protected void processBatch(List<Object[]> batchArgs) {
    try {
      jdbcTemplate.batchUpdate(getInsertSql(), batchArgs);
    } catch (Exception e) {
      handleBatchException(batchArgs);
    }
  }

  protected void handleBatchException(List<Object[]> batchArgs) {
    for (Object[] args : batchArgs) {
      try {
        jdbcTemplate.update(getInsertSql(), args);
      } catch (Exception e) {
        String sqlCode = "";
        if (e.getCause() instanceof java.sql.SQLException) {
          sqlCode = ((java.sql.SQLException) e.getCause()).getSQLState();
        }
//        e.printStackTrace();
        log.error("레코드 처리 중 오류 발생. SQL 상태코드: {}, 레코드: {}", sqlCode, convertArgsToString(args));
        failedRecordCount++;  // 실패한 레코드 수 증가
      }
    }
  }

  private String convertArgsToString(Object[] args) {
    StringBuilder sb = new StringBuilder();
    for (Object arg : args) {
      sb.append(arg).append(", ");
    }
    // 마지막 ", "를 제거
    if (sb.length() > 2) {
      sb.setLength(sb.length() - 2);
    }
    return sb.toString();
  }

//  protected void truncateTables(List<String> tableNames) {
//    for (String tableName : tableNames) {
//      String sql = String.format("DELETE FROM %s", tableName);
//      jdbcTemplate.update(sql);
//      log.info("{} 테이블 데이터 삭제", tableName);
//    }
//  }
  private void truncateTable() {
    try {
      jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 0");
      String sql = "TRUNCATE TABLE " + getTableName();
      jdbcTemplate.execute(sql);
    } finally {
      jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 1");
    }
  }

  protected void autoIncrementTable(String tableName, Long lastId) {
    String sql = "ALTER TABLE " + tableName + " AUTO_INCREMENT = ?";
    jdbcTemplate.update(sql, lastId + 1);
  }

  <V> V getValue(String[] csvData, FieldMappingProvider mappingProvider, String fieldName,
      Class<V> type) {
    FieldMapping fieldMapping = mappingProvider.getFieldMapping(fieldName);
    if (fieldMapping == null) {
      throw new IllegalArgumentException("No mapping found for field: " + fieldName);
    }

    String value = csvData[fieldMapping.getCvsFieldSeq()];
    value = removeUnwantedCharacters(value);

    List<String> fieldNames = getReplaceMultipleSpacesWithNewlineFieldNames();
    for (String name : fieldNames) {
      if (name.equals(fieldName)) {
        value = replaceMultipleSpacesWithNewline(value);
      }
    }

    try {
      if (value.isEmpty() || value.equals("NULL")) {
        if (fieldMapping.getDefaultValue() != null) {
          return type.cast(fieldMapping.getDefaultValue());
        }
        return null;
      }

      if (type.isEnum()) {
        return (V) Enum.valueOf((Class<Enum>) type, value);
      }

      if (type == Long.class) {
        return type.cast(Long.valueOf(value));
      } else if (type == Integer.class) {
        return type.cast(Integer.valueOf(value));
      } else if (type == Boolean.class) {
        if (value.equals("0")) {
          return type.cast(Boolean.FALSE);
        } else if (value.equals("1")) {
          return type.cast(Boolean.TRUE);
        }
        return type.cast(Boolean.valueOf(value));
      } else if (type == LocalDate.class) {
        DateTimeFormatter formatter;
        // yyyyMMdd 형식 처리
        if (value.matches("\\d{8}")) {
          formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        }
        // yyyy-MM-dd 형식 처리
        else if (value.matches("\\d{4}-\\d{2}-\\d{2}")) {
          formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        } else {
          throw new RuntimeException("날짜 형식 오류: " + value);
        }
        return (V) LocalDate.parse(value, formatter);
      } else if (type == LocalTime.class) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmmss");
        return (V) LocalTime.parse(value, formatter);
      } else if (type == LocalDateTime.class) {
        try {
          // T로 시작하는 경우 처리
          if (value.startsWith("T")) {
            value = value.substring(1); // 'T' 제거
          }

          // yyyyMMddHHmmss 형식으로 변환
          DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
          return (V) LocalDateTime.parse(value, formatter);

        } catch (DateTimeParseException e) {
          if (value.matches("\\d{8}")) {  // 'yyyyMMdd' 형식
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            LocalDate date = LocalDate.parse(value, formatter);
            return (V) LocalDateTime.of(date, LocalTime.MIDNIGHT);
          } else if (value.matches("[\\d\\.E\\+]+")) {  // 과학적 표기법 확인
            try {
              BigDecimal bigDecimal = new BigDecimal(value);
              String plainString = fixDateTimeString(bigDecimal.toPlainString());
              DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
              return (V) LocalDateTime.parse(plainString, formatter);
            } catch (NumberFormatException nfe) {
              throw new RuntimeException("날짜 형식 오류: " + value, nfe);
            }
          } else {
            throw new RuntimeException("날짜 형식 오류: " + value);
          }
        }
      } else if (type == Float.class) {
        return type.cast(Float.valueOf(value));
      } else if (type == Double.class) {
        return type.cast(Double.valueOf(value));
      }
      return type.cast(value);
    } catch (Exception e) {
      log.error("필드 처리 오류: {} 값: {} 타입: {}. 오류: {}", fieldName, value, type.getName(),
          e.getMessage());
      throw e;
    }
  }

  private static String fixDateTimeString(String dateTime) {
    // 월이 00이면 01로 변경
    if (dateTime.substring(4, 6).equals("00")) {
      dateTime = dateTime.substring(0, 4) + "01" + dateTime.substring(6);
    }
    // 일이 00이면 01로 변경
    if (dateTime.substring(6, 8).equals("00")) {
      dateTime = dateTime.substring(0, 6) + "01" + dateTime.substring(8);
    }
    return dateTime;
  }

  private String removeUnwantedCharacters(String value) {
    if (value == null) {
      return null;
    }
    if (value.isBlank()) {
      value = value.trim();
    }
    value = value.replace("\uFEFF", ""); // BOM 제거o
//    return value.replaceAll("[\\p{Z}\\p{C}]", ""); // 공백 및 제어 문자 제거
    return value;
  }

  private String replaceMultipleSpacesWithNewline(String input) {
    // 띄어쓰기 두번 -> 줄바꿈
    input = input.replaceAll(" {2}(?! )", "\n");
    // 띄어쓰기 두번 + 한번 -> 줄바꿈 한 번에 띄어쓰기
    input = input.replaceAll(" {3}", "\n ");
    // 띄어쓰기 네번 -> 줄바꿈 두 번
    input = input.replaceAll(" {4,}", "\n\n");

    return input;
  }

  protected List<String> getReplaceMultipleSpacesWithNewlineFieldNames() {
    return List.of();
  }

  protected abstract String getInsertSql();

  protected abstract Object[] prepareArgs(String[] row, FieldMappingProvider mappingProvider);

  protected abstract String getDirPath();

  protected abstract String getTableName();

//  protected abstract List<String> getTruncateTableName();

  protected abstract String getCsvFileName();

  protected abstract FieldMappingProvider getFieldMappingProvider();

  protected abstract boolean useAutoIncrementId();

  protected abstract void processRow(String[] row, FieldMappingProvider mappingProvider);
}