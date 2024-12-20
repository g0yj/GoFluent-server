package com.lms.api.migration.service;

import static com.lms.api.migration.MigrationRunner.DIR_PATH;

import com.lms.api.migration.mapping.FieldMappingProvider;
import com.lms.api.migration.mapping.MemberConsultationFieldMapping;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MemberConsultationMigrationService extends BaseMigrationService {

  private final String SQL = """
      INSERT INTO member_consultation (
          id, 
          consultation_date, 
          user_id, 
          type, 
          created_by, 
          modified_by, 
          details, 
          created_on, 
          modified_on
      ) VALUES (
          ?, ?, ?, ?, ?, ?, ?, ?, ?
      )
      """.trim();

  public MemberConsultationMigrationService(JdbcTemplate jdbcTemplate) {
    super(jdbcTemplate);
  }

  @Override
  protected String getDirPath() {
    return DIR_PATH;
  }

  @Override
  protected String getTableName() {
    return "member_consultation";
  }

//  @Override
//  protected List<String> getTruncateTableName() {
//    return List.of(getTableName());
//  }

  @Override
  protected String getCsvFileName() {
    return "내부회원_상담관리.csv";
  }

  @Override
  protected FieldMappingProvider getFieldMappingProvider() {
    return new MemberConsultationFieldMapping();
  }

  @Override
  protected boolean useAutoIncrementId() {
    return true;
  }

  @Override
  protected void processRow(String[] row, FieldMappingProvider mappingProvider) {
    jdbcTemplate.update(connection -> {
      PreparedStatement ps = connection.prepareStatement(SQL);
      int idx = 1;

      ps.setLong(idx++, getValue(row, mappingProvider, "id", Long.class));
      ps.setObject(idx++, getValue(row, mappingProvider, "consultationDate", LocalDateTime.class));
      ps.setString(idx++, getValue(row, mappingProvider, "userId", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "type", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "createdBy", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "modifiedBy", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "details", String.class));
      ps.setObject(idx++, getValue(row, mappingProvider, "createdOn", LocalDateTime.class));
      ps.setObject(idx++, getValue(row, mappingProvider, "modifiedOn", LocalDateTime.class));

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
        getValue(row, mappingProvider, "consultationDate", LocalDateTime.class),
        getValue(row, mappingProvider, "userId", String.class),
        getValue(row, mappingProvider, "type", String.class),
        getValue(row, mappingProvider, "createdBy", String.class),
        getValue(row, mappingProvider, "modifiedBy", String.class),
        getValue(row, mappingProvider, "details", String.class),
        getValue(row, mappingProvider, "createdOn", LocalDateTime.class),
        getValue(row, mappingProvider, "modifiedOn", LocalDateTime.class)
    };
  }

  @Override
  protected List<String> getReplaceMultipleSpacesWithNewlineFieldNames() {
    return List.of("details");
  }
}
