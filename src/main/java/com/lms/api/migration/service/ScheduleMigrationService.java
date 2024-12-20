package com.lms.api.migration.service;

import static com.lms.api.migration.MigrationRunner.DIR_PATH;

import com.lms.api.migration.mapping.FieldMappingProvider;
import com.lms.api.migration.mapping.ScheduleFieldMapping;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ScheduleMigrationService extends BaseMigrationService {

  private final String SQL = """
      INSERT INTO schedule(
        id, teacher_id, `date`, start_time, created_by, 
        modified_by, created_on, modified_on, work_time
      ) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?);
          """.trim();

  public ScheduleMigrationService(JdbcTemplate jdbcTemplate) {
    super(jdbcTemplate);
  }

  @Override
  protected String getDirPath() {
    return DIR_PATH;
  }

  @Override
  protected String getTableName() {
    return "schedule";
  }

//  @Override
//  protected List<String> getTruncateTableName() {
//    return List.of(getTableName());
//  }

  @Override
  protected String getCsvFileName() {
    return "강사스케쥴관리.csv";
  }

  @Override
  protected FieldMappingProvider getFieldMappingProvider() {
    return new ScheduleFieldMapping();
  }

  @Override
  protected boolean useAutoIncrementId() {
    return false;
  }

  @Override
  protected void processRow(String[] row, FieldMappingProvider mappingProvider) {
    jdbcTemplate.update(connection -> {
      PreparedStatement ps = connection.prepareStatement(SQL);
      int idx = 1;

      ps.setString(idx++, getValue(row, mappingProvider, "id", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "teacherId", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "date", String.class));
      ps.setObject(idx++, getValue(row, mappingProvider, "startTime", LocalTime.class));
      ps.setString(idx++, getValue(row, mappingProvider, "createdBy", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "modifiedBy", String.class));
      ps.setObject(idx++, getValue(row, mappingProvider, "createdOn", LocalDateTime.class));
      ps.setObject(idx++, getValue(row, mappingProvider, "modifiedOn", LocalDateTime.class));
      ps.setString(idx++, getValue(row, mappingProvider, "workTime", String.class));

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
        getValue(row, mappingProvider, "id", String.class),
        getValue(row, mappingProvider, "teacherId", String.class),
        getValue(row, mappingProvider, "date", String.class),
        getValue(row, mappingProvider, "startTime", LocalTime.class),
        getValue(row, mappingProvider, "createdBy", String.class),
        getValue(row, mappingProvider, "modifiedBy", String.class),
        getValue(row, mappingProvider, "createdOn", LocalDateTime.class),
        getValue(row, mappingProvider, "modifiedOn", LocalDateTime.class),
        getValue(row, mappingProvider, "workTime", String.class)
    };
  }
}
