package com.lms.api.migration.service;

import static com.lms.api.migration.MigrationRunner.DIR_PATH;

import com.lms.api.migration.mapping.CourseFieldMapping;
import com.lms.api.migration.mapping.FieldMappingProvider;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CourseMigrationService extends BaseMigrationService {

  private final String SQL = """
      INSERT INTO course (
          id,
          user_id,
          lesson_count,
          assignment_count,
          attendance_count,
          start_date,
          end_date,
          teacher_id,
          assistant_teacher_id,
          count_change_reason,
          is_completion,
          created_by,
          created_on,
          modified_by,
          modified_on,
          order_product_id,
          is_reservation,
          date_change_reason
      ) VALUES (
          ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 
          ?, ?, ?, ?, ?, ?, ?, ?
      )
      """.trim();

  public CourseMigrationService(JdbcTemplate jdbcTemplate) {
    super(jdbcTemplate);
  }

  @Override
  protected String getDirPath() {
    return DIR_PATH;
  }

  @Override
  protected String getTableName() {
    return "course";
  }

//  @Override
//  protected List<String> getTruncateTableName() {
//    return List.of(getTableName());
//  }

  @Override
  protected String getCsvFileName() {
    return "수강관리.csv";
  }

  @Override
  protected FieldMappingProvider getFieldMappingProvider() {
    return new CourseFieldMapping();
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
      ps.setString(idx++, getValue(row, mappingProvider, "userId", String.class));
      ps.setFloat(idx++, getValue(row, mappingProvider, "lessonCount", Float.class));
      ps.setFloat(idx++, getValue(row, mappingProvider, "assignmentCount", Float.class));
      ps.setFloat(idx++, getValue(row, mappingProvider, "attendanceCount", Float.class));
      ps.setObject(idx++, getValue(row, mappingProvider, "startDate", LocalDate.class));
      ps.setObject(idx++, getValue(row, mappingProvider, "endDate", LocalDate.class));
      ps.setString(idx++, getValue(row, mappingProvider, "teacherId", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "assistantTeacherId", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "countChangeReason", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "isCompletion", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "createdBy", String.class));
      ps.setObject(idx++, getValue(row, mappingProvider, "createdOn", LocalDateTime.class));
      ps.setString(idx++, getValue(row, mappingProvider, "modifiedBy", String.class));
      ps.setObject(idx++, getValue(row, mappingProvider, "modifiedOn", LocalDateTime.class));
      ps.setString(idx++, getValue(row, mappingProvider, "orderProductId", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "isReservation", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "dateChangeReason", String.class));

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
        getValue(row, mappingProvider, "userId", String.class),
        getValue(row, mappingProvider, "lessonCount", Float.class),
        getValue(row, mappingProvider, "assignmentCount", Float.class),
        getValue(row, mappingProvider, "attendanceCount", Float.class),
        getValue(row, mappingProvider, "startDate", LocalDate.class),
        getValue(row, mappingProvider, "endDate", LocalDate.class),
        getValue(row, mappingProvider, "teacherId", String.class),
        getValue(row, mappingProvider, "assistantTeacherId", String.class),
        getValue(row, mappingProvider, "countChangeReason", String.class),
        getValue(row, mappingProvider, "isCompletion", String.class),
        getValue(row, mappingProvider, "createdBy", String.class),
        getValue(row, mappingProvider, "createdOn", LocalDateTime.class),
        getValue(row, mappingProvider, "modifiedBy", String.class),
        getValue(row, mappingProvider, "modifiedOn", LocalDateTime.class),
        getValue(row, mappingProvider, "orderProductId", String.class),
        getValue(row, mappingProvider, "isReservation", String.class),
        getValue(row, mappingProvider, "dateChangeReason", String.class)
    };
  }
}
