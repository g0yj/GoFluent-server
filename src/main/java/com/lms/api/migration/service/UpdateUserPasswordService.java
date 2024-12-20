package com.lms.api.migration.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateUserPasswordService {

  private final JdbcTemplate jdbcTemplate;

  // 업데이트할 비밀번호
  private final String PASSWORD = "$2a$10$bnxOzQZpdKqRcKfAkL2TxuogsqenWLAmYuQzurt1cKtMpBtJyvPD2";

  public void update() {
    try {
      // 비밀번호 업데이트 쿼리 실행
      int rowsUpdated = jdbcTemplate.update("""
          UPDATE user_
          SET password = ?
          """, PASSWORD
      );
    } catch (Exception e) {
      log.error("Failed to update user passwords", e);
      throw new RuntimeException(e);
    }
  }
}
