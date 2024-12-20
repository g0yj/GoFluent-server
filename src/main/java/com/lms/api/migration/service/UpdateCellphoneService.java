package com.lms.api.migration.service;

import static com.lms.api.migration.MigrationRunner.DIR_PATH;

import com.opencsv.CSVReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateCellphoneService {

  private final JdbcTemplate jdbcTemplate;

  private final static int DATE = 1;
  private final static int NAME = 3;
  private final static int GENDER = 4;
  private final static int PHONE_NUMBER = 5;

  private final String CSV_PATH = DIR_PATH + "상담일지분석.csv";

  // 전역 변수로 선언
  private static final Map<String, String> genderMap;

  // static 블록에서 초기화
  static {
    genderMap = new HashMap<>();
    genderMap.put("남", "M");
    genderMap.put("여", "F");
  }

  public void updateUsersFromCsv() {
    int totalUpdatedWithGender = 0;
    int totalUpdatedWithoutGender = 0;

    try (CSVReader csvReader = new CSVReader(new FileReader(CSV_PATH))) {
      String[] nextRecord;
      while ((nextRecord = csvReader.readNext()) != null) {
        if (nextRecord.length < 6) {
          log.warn("열 부족으로 인한 건너뛰기...");
          continue;
        }

        String date = nextRecord[DATE].trim(); //일자
        String name = nextRecord[NAME].trim(); //이름
        String gender = nextRecord[GENDER].trim(); //성별
        String phoneNumber = nextRecord[PHONE_NUMBER].trim();

//        log.debug("일시: {}, 이름: {}, 성별: {}, 전화번호: {}", date, name, gender, phoneNumber);

        if (!date.isEmpty() && !name.isEmpty() && !phoneNumber.isEmpty()) {
          if (!gender.isEmpty()) {
            // 상담일, 이름, 성별 일치
            int rowCountWithGender = jdbcTemplate.queryForObject("""
                SELECT COUNT(*) 
                FROM consultation
                WHERE DATE(created_on) = ? 
                AND name LIKE ? 
                AND gender = ?
                """, Integer.class, date, name + "%", convertGender(gender)
            );

            if (rowCountWithGender > 0) {
              int rowsUpdatedWithGender = jdbcTemplate.update("""
                  UPDATE consultation 
                  SET cell_phone = ? 
                  WHERE DATE(created_on) = ? 
                  AND name LIKE ? 
                  AND gender = ?
                  """, phoneNumber, date, name + "%", convertGender(gender)
              );
              log.debug("Updated {} row(s) with gender 일시:{}, 이름: {}, 전화번호: {}, 성별: {}",
                  rowsUpdatedWithGender, date, name, phoneNumber, gender);
              totalUpdatedWithGender += rowsUpdatedWithGender;
            }
          } else {
            // 상담일, 이름 일치
            int rowCountWithoutGender = jdbcTemplate.queryForObject("""
                SELECT COUNT(*) 
                FROM consultation
                WHERE DATE(created_on) = ? 
                AND name LIKE ?
                """, Integer.class, date, name + "%"
            );

            if (rowCountWithoutGender > 0) {
              int rowsUpdatedWithoutGender = jdbcTemplate.update("""
                  UPDATE consultation 
                  SET cell_phone = ? 
                  WHERE DATE(created_on) = ? 
                  AND name LIKE ? 
                  """, phoneNumber, date, name + "%"
              );
              log.debug("Updated {} row(s) without gender 일시:{}, 이름: {}, 전화번호: {}",
                  rowsUpdatedWithoutGender, date, name, phoneNumber);
              totalUpdatedWithoutGender += rowsUpdatedWithoutGender;
            }
          }
        }
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    // 최종 업데이트 결과 로그
    log.info("Total updated rows with gender: {}", totalUpdatedWithGender);
    log.info("Total updated rows without gender: {}", totalUpdatedWithoutGender);
  }

  private String convertGender(String gender) {
    return genderMap.getOrDefault(gender, "");
  }
}
