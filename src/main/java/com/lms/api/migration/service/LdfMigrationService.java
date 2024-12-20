package com.lms.api.migration.service;

import static com.lms.api.migration.MigrationRunner.DIR_PATH;

import com.lms.api.migration.mapping.FieldMappingProvider;
import com.lms.api.migration.mapping.LdfFieldMapping;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LdfMigrationService extends BaseMigrationService {

  private final String SQL = """
      INSERT INTO ldf (
          id,
          reservation_id, 
          user_id, 
          lesson, 
          content_sp, 
          content_v, 
          content_sg, 
          content_c, 
          created_on, 
          modified_on, 
          created_by, 
          modified_by, 
          grade, 
          evaluation, 
          email_id
      ) VALUES (
          ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 
          ?, ?, ?, ?, ?
      )
      """.trim();

  public LdfMigrationService(JdbcTemplate jdbcTemplate) {
    super(jdbcTemplate);
  }

  @Override
  protected String getDirPath() {
    return DIR_PATH;
  }

  @Override
  protected String getTableName() {
    return "ldf";
  }

//  @Override
//  protected List<String> getTruncateTableName() {
//    return List.of(getTableName());
//  }

  @Override
  protected String getCsvFileName() {
    return "LDF_2019.csv;LDF_2020.csv;LDF_2021.csv;LDF_2022.csv;LDF_2023.csv;LDF_2024_(상반기).csv";
  }

  @Override
  protected FieldMappingProvider getFieldMappingProvider() {
    return new LdfFieldMapping();
  }

  @Override
  protected boolean useAutoIncrementId() {
    return true;
  }

  @Override
  protected void processRow(String[] row, FieldMappingProvider mappingProvider) {

//    resetAutoIncrement("ldf", 1);
    jdbcTemplate.update(connection -> {
      PreparedStatement ps = connection.prepareStatement(SQL);
      int idx = 1;

      ps.setLong(idx++, getValue(row, mappingProvider, "id", Long.class));
      ps.setLong(idx++, getValue(row, mappingProvider, "reservationId", Long.class));
      ps.setString(idx++, getValue(row, mappingProvider, "userId", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "lesson", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "contentSp", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "contentV", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "contentSg", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "contentC", String.class));
      ps.setObject(idx++, getValue(row, mappingProvider, "createdOn", LocalDateTime.class));
      ps.setObject(idx++, getValue(row, mappingProvider, "modifiedOn", LocalDateTime.class));
      ps.setString(idx++, getValue(row, mappingProvider, "createdBy", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "modifiedBy", String.class));
      ps.setFloat(idx++, getValue(row, mappingProvider, "grade", Float.class));
      ps.setString(idx++, getValue(row, mappingProvider, "evaluation", String.class));
      ps.setObject(idx++, getValue(row, mappingProvider, "emailId", Long.class));

      return ps;
    });
  }

  @Override
  protected String getInsertSql() {
    return SQL;
  }

  @Override
  protected Object[] prepareArgs(String[] row, FieldMappingProvider mappingProvider) {
    return new Object[]{
        getValue(row, mappingProvider, "id", Long.class),
        getValue(row, mappingProvider, "reservationId", Long.class),
        getValue(row, mappingProvider, "userId", String.class),
        getValue(row, mappingProvider, "lesson", String.class),
        getValue(row, mappingProvider, "contentSp", String.class),
        getValue(row, mappingProvider, "contentV", String.class),
        getValue(row, mappingProvider, "contentSg", String.class),
        getValue(row, mappingProvider, "contentC", String.class),
        getValue(row, mappingProvider, "createdOn", LocalDateTime.class),
        getValue(row, mappingProvider, "modifiedOn", LocalDateTime.class),
        getValue(row, mappingProvider, "createdBy", String.class),
        getValue(row, mappingProvider, "modifiedBy", String.class),
        getValue(row, mappingProvider, "grade", Float.class),
        getValue(row, mappingProvider, "evaluation", String.class),
        getValue(row, mappingProvider, "emailId", Long.class)
    };
  }
}
