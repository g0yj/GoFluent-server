package com.lms.api.migration.service;

import static com.lms.api.migration.MigrationRunner.DIR_PATH;

import com.lms.api.migration.mapping.ConsultationFieldMapping;
import com.lms.api.migration.mapping.FieldMappingProvider;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class ConsultationMigrationService extends BaseMigrationService {

  private final String SQL = """
      INSERT INTO consultation (
          id,
          institution_id,
          consultation_date,
          created_by,
          modified_by,
          name,
          gender,
          job,
          company,
          phone,
          cell_phone,
          found_path,
          found_path_note,
          visit_date,
          details,
          is_member,
          created_on,
          modified_on,
          type,
          study_purpose,
          etc_study_purpose,
          call_time,
          email,
          status,
          level_test_type
      ) VALUES (
          ?, ?, ?, ?, ?,  ?, ?, ?, ?, ?,  ?, ?, ?, ?, ?,  ?, ?, ?, ?, ?,  ?, ?, ?, ?, ?
      )
      """.trim();

  public ConsultationMigrationService(JdbcTemplate jdbcTemplate) {
    super(jdbcTemplate);
  }

  @Override
  protected String getDirPath() {
    return DIR_PATH;
  }

  @Override
  protected String getTableName() {
    return "consultation";
  }

//  @Override
//  protected List<String> getTruncateTableName() {
//    return List.of(getTableName());
//  }

  @Override
  protected String getCsvFileName() {
    return "전화상담관리.csv";
  }

  @Override
  protected FieldMappingProvider getFieldMappingProvider() {
    return new ConsultationFieldMapping();
  }

  @Override
  protected boolean useAutoIncrementId() {
    return true;
  }

  @Override
  protected void processRow(String[] row, FieldMappingProvider fieldMappingProvider) {
    jdbcTemplate.update(connection -> {
      PreparedStatement ps = connection.prepareStatement(SQL);
      int idx = 1;

      ps.setLong(idx++, getValue(row, fieldMappingProvider, "id", Long.class));
      ps.setString(idx++, getValue(row, fieldMappingProvider, "institutionId", String.class));
      ps.setObject(idx++, getValue(row, fieldMappingProvider, "consultationDate", LocalDateTime.class));
      ps.setString(idx++, getValue(row, fieldMappingProvider, "createdBy", String.class));
      ps.setString(idx++, getValue(row, fieldMappingProvider, "modifiedBy", String.class));
      ps.setString(idx++, getValue(row, fieldMappingProvider, "name", String.class));
      ps.setString(idx++, getValue(row, fieldMappingProvider, "gender", String.class));
      ps.setString(idx++, getValue(row, fieldMappingProvider, "job", String.class));
      ps.setString(idx++, getValue(row, fieldMappingProvider, "company", String.class));
      ps.setString(idx++, getValue(row, fieldMappingProvider, "phone", String.class));
      ps.setString(idx++, getValue(row, fieldMappingProvider, "cellPhone", String.class));
      ps.setString(idx++, getValue(row, fieldMappingProvider, "foundPath", String.class));
      ps.setString(idx++, getValue(row, fieldMappingProvider, "foundPathNote", String.class));
      ps.setObject(idx++, getValue(row, fieldMappingProvider, "visitDate", LocalDateTime.class));
      ps.setString(idx++, getValue(row, fieldMappingProvider, "details", String.class));
      ps.setBoolean(idx++, getValue(row, fieldMappingProvider, "isMember", Boolean.class));
      ps.setObject(idx++, getValue(row, fieldMappingProvider, "createdOn", LocalDateTime.class));
      ps.setObject(idx++, getValue(row, fieldMappingProvider, "modifiedOn", LocalDateTime.class));
      ps.setString(idx++, getValue(row, fieldMappingProvider, "type", String.class));
      ps.setString(idx++, getValue(row, fieldMappingProvider, "studyPurpose", String.class));
      ps.setString(idx++, getValue(row, fieldMappingProvider, "etcStudyPurpose", String.class));
      ps.setString(idx++, getValue(row, fieldMappingProvider, "callTime", String.class));
      ps.setString(idx++, getValue(row, fieldMappingProvider, "email", String.class));
      ps.setString(idx++, getValue(row, fieldMappingProvider, "status", String.class));
      ps.setString(idx++, getValue(row, fieldMappingProvider, "levelTestType", String.class));

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
        getValue(row, mappingProvider, "institutionId", String.class),
        getValue(row, mappingProvider, "consultationDate", LocalDateTime.class),
        getValue(row, mappingProvider, "createdBy", String.class),
        getValue(row, mappingProvider, "modifiedBy", String.class),
        getValue(row, mappingProvider, "name", String.class),
        getValue(row, mappingProvider, "gender", String.class),
        getValue(row, mappingProvider, "job", String.class),
        getValue(row, mappingProvider, "company", String.class),
        getValue(row, mappingProvider, "phone", String.class),
        getValue(row, mappingProvider, "cellPhone", String.class),
        getValue(row, mappingProvider, "foundPath", String.class),
        getValue(row, mappingProvider, "foundPathNote", String.class),
        getValue(row, mappingProvider, "visitDate", LocalDateTime.class),
        getValue(row, mappingProvider, "details", String.class),
        getValue(row, mappingProvider, "isMember", Boolean.class),
        getValue(row, mappingProvider, "createdOn", LocalDateTime.class),
        getValue(row, mappingProvider, "modifiedOn", LocalDateTime.class),
        getValue(row, mappingProvider, "type", String.class),
        getValue(row, mappingProvider, "studyPurpose", String.class),
        getValue(row, mappingProvider, "etcStudyPurpose", String.class),
        getValue(row, mappingProvider, "callTime", String.class),
        getValue(row, mappingProvider, "email", String.class),
        getValue(row, mappingProvider, "status", String.class),
        getValue(row, mappingProvider, "levelTestType", String.class),
    };
  }

  @Override
  protected List<String> getReplaceMultipleSpacesWithNewlineFieldNames() {
    return List.of("details");
  }
}
