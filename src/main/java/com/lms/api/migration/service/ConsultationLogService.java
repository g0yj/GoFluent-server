package com.lms.api.migration.service;

import static com.lms.api.migration.MigrationRunner.DIR_PATH;

import com.lms.api.migration.mapping.ConsultationLogFieldMapping;
import com.lms.api.migration.mapping.FieldMappingProvider;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class ConsultationLogService extends BaseMigrationService {

  private final String SQL = """
    INSERT INTO consultation_log (`date`, name, gender, cell_phone)
    VALUES (?, ?,
        CASE
            WHEN ? = '남' THEN 'M'
            WHEN ? = '여' THEN 'F'
            ELSE ?  -- 성별이 '남' 또는 '여'가 아닌 경우 원래 값을 사용
        END,
        ?)
    """;

  public ConsultationLogService(JdbcTemplate jdbcTemplate) {
    super(jdbcTemplate);
  }

  @Override
  protected String getDirPath() {
    return DIR_PATH;
  }

  @Override
  protected String getTableName() {
    return "consultation_log";
  }

//  @Override
//  protected List<String> getTruncateTableName() {
//    return List.of(getTableName());
//  }

  @Override
  protected String getCsvFileName() {
    return "상담일지분석.csv";
  }

  protected void processRow(String[] row, FieldMappingProvider fieldMappingProvider) {
    jdbcTemplate.update(connection -> {
      PreparedStatement ps = connection.prepareStatement(SQL);
      int idx = 1;

      ps.setString(idx++, getValue(row, fieldMappingProvider, "date", String.class));
      ps.setString(idx++, getValue(row, fieldMappingProvider, "name", String.class));
      ps.setString(idx++, getValue(row, fieldMappingProvider, "gender", String.class));
      ps.setString(idx++, getValue(row, fieldMappingProvider, "gender", String.class));
      ps.setString(idx++, getValue(row, fieldMappingProvider, "gender", String.class));
      ps.setString(idx++, getValue(row, fieldMappingProvider, "cellPhone", String.class));

      return ps;
    });
  }

  @Override
  protected FieldMappingProvider getFieldMappingProvider() {
    return new ConsultationLogFieldMapping();
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
        getValue(row, mappingProvider, "date", LocalDate.class),
        getValue(row, mappingProvider, "name", String.class),
        getValue(row, mappingProvider, "gender", String.class),
        getValue(row, mappingProvider, "gender", String.class),
        getValue(row, mappingProvider, "gender", String.class),
        getValue(row, mappingProvider, "cellPhone", String.class),
    };
  }


}

