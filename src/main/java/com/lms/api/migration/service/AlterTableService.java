package com.lms.api.migration.service;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlterTableService {

  private final JdbcTemplate jdbcTemplate;

  public void alter() {
    // SQL 스크립트를 실행하는 코드
    try {
      // teacher 테이블에 컬럼 추가
      jdbcTemplate.execute("ALTER TABLE teacher ADD COLUMN memo TEXT");
      jdbcTemplate.execute("ALTER TABLE teacher ADD COLUMN file_size BIGINT");

      // level_test 테이블에 컬럼 추가
      jdbcTemplate.execute("ALTER TABLE level_test ADD COLUMN file_size BIGINT");

      // consultation 테이블에 컬럼 추가
      jdbcTemplate.execute("ALTER TABLE consultation ADD COLUMN file_size BIGINT");

      // product 테이블에 컬럼 추가
      jdbcTemplate.execute("ALTER TABLE product ADD COLUMN curriculumYN VARCHAR(1)");

      System.out.println("Migration executed successfully!");

    } catch (Exception e) {
      throw new RuntimeException("Migration failed", e);
    }
  }
}
