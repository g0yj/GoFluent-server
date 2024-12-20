package com.lms.api.migration.service;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DatabaseResetService {

  private final JdbcTemplate jdbcTemplate;

  @Transactional
  public void truncateTables() {
    // FOREIGN_KEY_CHECKS를 0으로 설정하여 외래 키 제약 조건 비활성화
    jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 0");

    // 각 테이블을 개별적으로 TRUNCATE
    jdbcTemplate.execute("TRUNCATE TABLE `consultation`");
    jdbcTemplate.execute("TRUNCATE TABLE `course_history`");
    jdbcTemplate.execute("TRUNCATE TABLE `email`");
    jdbcTemplate.execute("TRUNCATE TABLE `member_consultation`");
    jdbcTemplate.execute("TRUNCATE TABLE `order_`");
    jdbcTemplate.execute("TRUNCATE TABLE `payment`");
    jdbcTemplate.execute("TRUNCATE TABLE `refund`");
    jdbcTemplate.execute("TRUNCATE TABLE `salary`");
    jdbcTemplate.execute("TRUNCATE TABLE `salary_note`");
    jdbcTemplate.execute("TRUNCATE TABLE `teacher`");
    jdbcTemplate.execute("TRUNCATE TABLE `teacher_file`");
    jdbcTemplate.execute("TRUNCATE TABLE `user_`");
    jdbcTemplate.execute("TRUNCATE TABLE `user_login`");
    jdbcTemplate.execute("TRUNCATE TABLE `consultation_history`");
    jdbcTemplate.execute("TRUNCATE TABLE `course`");
    jdbcTemplate.execute("TRUNCATE TABLE `evaluation_status`");
    jdbcTemplate.execute("TRUNCATE TABLE `ldf`");
    jdbcTemplate.execute("TRUNCATE TABLE `level_test`");
    jdbcTemplate.execute("TRUNCATE TABLE `order_product`");
    jdbcTemplate.execute("TRUNCATE TABLE `reservation`");
    jdbcTemplate.execute("TRUNCATE TABLE `schedule`");
    jdbcTemplate.execute("TRUNCATE TABLE `sms_target`");

    // FOREIGN_KEY_CHECKS를 1로 설정하여 외래 키 제약 조건 다시 활성화
    jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 1");
  }
}
