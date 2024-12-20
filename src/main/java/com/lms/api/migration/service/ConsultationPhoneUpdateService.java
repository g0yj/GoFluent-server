package com.lms.api.migration.service;

import static com.lms.api.migration.MigrationRunner.DIR_PATH;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsultationPhoneUpdateService {

  private final JdbcTemplate jdbcTemplate;

  @Transactional
  public void updateConsultationPhones() {
    String csvFilePath = DIR_PATH + "/cellphone_cons.csv";
    int resetCount = resetAllPhoneNumbers();
    log.info("모든 상담의 전화번호를 null로 초기화했습니다. 초기화된 레코드 수: {}", resetCount);

    int totalProcessed = 0;
    int totalUpdated = 0;
    int totalSkipped = 0;

    try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
      String line;
      while ((line = br.readLine()) != null) {
        line = removeUTF8BOM(line);
        String[] values = line.split(",");
        if (values.length == 2) {
          String id = values[0].trim();
          String phoneNumber = values[1].trim();

          totalProcessed++;

          if (!phoneNumber.equals("없음")) {
            // String encryptedPhoneNumber = encryptionService.encrypt(phoneNumber);
            totalUpdated += updateConsultationPhone(Long.valueOf(id), phoneNumber);
          } else {
            totalSkipped++;
          }
        }
      }
    } catch (IOException e) {
      throw new RuntimeException("CSV 파일 읽기 실패", e);
    }

    log.info("총 처리된 레코드: {}", totalProcessed);
    log.info("업데이트된 레코드: {}", totalUpdated);
    log.info("건너뛴 레코드 (전화번호 '없음'): {}", totalSkipped);
  }

  private int resetAllPhoneNumbers() {
    String sql = "UPDATE consultation SET cell_phone = NULL";
    return jdbcTemplate.update(sql);
  }

  private int updateConsultationPhone(Long id, String encryptedPhoneNumber) {
    String sql = "UPDATE consultation SET cell_phone = ? WHERE id = ?";
//    log.debug("## id:{}, cellPhone:{}", id, encryptedPhoneNumber);
    int updatedRows = jdbcTemplate.update(sql, encryptedPhoneNumber, id);
    if (updatedRows == 0) {
//      log.debug("사용자 ID가 존재하지 않음: {}", id);
    }
    return updatedRows;
  }

  private String removeUTF8BOM(String s) {
    if (s.startsWith("\uFEFF")) {
      s = s.substring(1);
    }
    return s;
  }
}
