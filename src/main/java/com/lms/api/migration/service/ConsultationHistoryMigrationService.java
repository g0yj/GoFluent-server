package com.lms.api.migration.service;

import static com.lms.api.migration.MigrationRunner.DIR_PATH;

import com.lms.api.migration.mapping.ConsultationHistoryFieldMapping;
import com.lms.api.migration.mapping.FieldMappingProvider;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class ConsultationHistoryMigrationService extends BaseMigrationService {

  private final String SQL = """
        INSERT INTO consultation_history
        (id, consultation_id, `date`, details, created_by, created_on, modified_on)
        VALUES(?, ?, ?, ?, ?, ?, ?);
      """;

  public ConsultationHistoryMigrationService(JdbcTemplate jdbcTemplate) {
    super(jdbcTemplate);
  }

  @Override
  protected String getDirPath() {
    return DIR_PATH;
  }

  @Override
  protected String getTableName() {
    return "consultation_history";
  }

//  @Override
//  protected List<String> getTruncateTableName() {
//    return List.of(getTableName());
//  }

  @Override
  protected String getCsvFileName() {
    return "추가상담내역.csv";
  }

  protected void processRow(String[] row, FieldMappingProvider fieldMappingProvider) {
    jdbcTemplate.update(connection -> {
      PreparedStatement ps = connection.prepareStatement(SQL);
      int idx = 1;

      ps.setLong(idx++, getValue(row, fieldMappingProvider, "id", Long.class));
      ps.setLong(idx++, getValue(row, fieldMappingProvider, "consultationId", Long.class));
      ps.setObject(idx++, getValue(row, fieldMappingProvider, "date", LocalDateTime.class));
      ps.setString(idx++, getValue(row, fieldMappingProvider, "details", String.class));
      ps.setString(idx++, getValue(row, fieldMappingProvider, "createdBy", String.class));
      ps.setObject(idx++, getValue(row, fieldMappingProvider, "createdOn", LocalDateTime.class));
      ps.setObject(idx++, getValue(row, fieldMappingProvider, "modifiedOn", LocalDateTime.class));

      return ps;
    });
  }

  @Override
  protected FieldMappingProvider getFieldMappingProvider() {
    return new ConsultationHistoryFieldMapping();
  }

  @Override
  protected boolean useAutoIncrementId() {
    return false;
  }

  @Override
  protected String getInsertSql() {
    return SQL;
  }

  @Override
  protected Object[] prepareArgs(String[] row, FieldMappingProvider mappingProvider) {
    return new Object[]{
        getValue(row, mappingProvider, "id", Long.class),
        getValue(row, mappingProvider, "consultationId", Long.class),
        getValue(row, mappingProvider, "date", LocalDateTime.class),
        getValue(row, mappingProvider, "details", String.class),
        getValue(row, mappingProvider, "createdBy", String.class),
        getValue(row, mappingProvider, "createdOn", LocalDateTime.class),
        getValue(row, mappingProvider, "modifiedOn", LocalDateTime.class)
    };
  }

  @Override
  protected List<String> getReplaceMultipleSpacesWithNewlineFieldNames() {
    return List.of("details");
  }
}

