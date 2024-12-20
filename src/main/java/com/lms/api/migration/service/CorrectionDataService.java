package com.lms.api.migration.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CorrectionDataService {

  private final JdbcTemplate jdbcTemplate;

  @Transactional
  public void update() {
    updateUserPassword();
    updateConsultationIsMember();
//    updateReservationReportYn();
  }

  /**
   * 사용자 비밀번호 일괄 변경
   */
  private void updateUserPassword() {
    final String PASSWORD = "$2a$10$bnxOzQZpdKqRcKfAkL2TxuogsqenWLAmYuQzurt1cKtMpBtJyvPD2";
    int rowsUpdated = jdbcTemplate.update("UPDATE user_ SET password = ?", PASSWORD);
  }

  /**
   * 학사보고서 report_yn
   */
  private void updateReservationReportYn() {
    // 학사보고서 작성여부
    String updateReportYn = """
     UPDATE reservation 
     SET report_yn = IF(
         report IS NOT NULL 
         OR today_lesson IS NOT NULL 
         OR next_lesson IS NOT NULL, 
         1, 0)
     """;
    int rowsAffected = jdbcTemplate.update(updateReportYn);
    log.info("{} : {}", updateReportYn, rowsAffected);
  }

  /**
   * 상담 is_member
   */
  private void updateConsultationIsMember() {
    // 첫 번째 업데이트: '1' 이면 'Y' 변경
    String updateIsMemberY = "UPDATE consultation SET is_member = 'Y' WHERE is_member = '1'";
    int rowsAffected = jdbcTemplate.update(updateIsMemberY);
    log.info("{} : {}건 업데이트", updateIsMemberY, rowsAffected);

    // 두 번째 업데이트: '0' 이면 'N' 변경
    String updateIsMemberN = "UPDATE consultation SET is_member = 'N' WHERE is_member = '0'";
    rowsAffected = jdbcTemplate.update(updateIsMemberN);
    log.info("{} : {}건 업데이트", updateIsMemberN, rowsAffected);
  }
}
