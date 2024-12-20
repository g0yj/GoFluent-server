package com.lms.api.migration.service;

import static com.lms.api.migration.MigrationRunner.DIR_PATH;

import com.lms.api.migration.mapping.FieldMappingProvider;
import com.lms.api.migration.mapping.ReservationFieldMapping;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ReservationMigrationService extends BaseMigrationService {

  private final String SQL = """
      INSERT INTO reservation (
          id, 
          course_id, 
          user_id, 
          date, 
          start_time, 
          end_time, 
          teacher_id, 
          attendance_status, 
          report,
          created_by, 
          modified_by, 
          created_on, 
          modified_on
      ) VALUES (
          ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?
      )
      """.trim();

  public ReservationMigrationService(JdbcTemplate jdbcTemplate) {
    super(jdbcTemplate);
  }

  @Override
  protected String getDirPath() {
    return DIR_PATH;
  }

  @Override
  protected String getTableName() {
    return "reservation";
  }

//  @Override
//  protected List<String> getTruncateTableName() {
//    return List.of(getTableName());
//  }

  @Override
  protected String getCsvFileName() {
    return "수강예약관리.csv";
  }

  @Override
  protected FieldMappingProvider getFieldMappingProvider() {
    return new ReservationFieldMapping();
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
      ps.setLong(idx++, getValue(row, mappingProvider, "courseId", Long.class));
      ps.setString(idx++, getValue(row, mappingProvider, "userId", String.class));
      ps.setObject(idx++, getValue(row, mappingProvider, "date", LocalDate.class));
      ps.setObject(idx++, getValue(row, mappingProvider, "startTime", LocalTime.class));
      ps.setObject(idx++, getValue(row, mappingProvider, "endTime", LocalTime.class));
      ps.setString(idx++, getValue(row, mappingProvider, "teacherId", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "attendanceStatus", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "report", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "createdBy", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "modifiedBy", String.class));
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
        getValue(row, mappingProvider, "courseId", Long.class),
        getValue(row, mappingProvider, "userId", String.class),
        getValue(row, mappingProvider, "date", LocalDate.class),
        getValue(row, mappingProvider, "startTime", LocalTime.class),
        getValue(row, mappingProvider, "endTime", LocalTime.class),
        getValue(row, mappingProvider, "teacherId", String.class),
        getValue(row, mappingProvider, "attendanceStatus", String.class),
        getValue(row, mappingProvider, "report", String.class),
        getValue(row, mappingProvider, "createdBy", String.class),
        getValue(row, mappingProvider, "modifiedBy", String.class),
        getValue(row, mappingProvider, "createdOn", LocalDateTime.class),
        getValue(row, mappingProvider, "modifiedOn", LocalDateTime.class)
    };
  }
}
