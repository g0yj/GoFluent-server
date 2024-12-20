package com.lms.api.migration.service;

import static com.lms.api.migration.MigrationRunner.DIR_PATH;

import com.lms.api.migration.mapping.CourseHistoryFieldMapping;
import com.lms.api.migration.mapping.FieldMappingProvider;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CourseHistoryMigrationService extends BaseMigrationService {

  private final String SQL = """
      INSERT INTO course_history (
          id,
          module,
          module_id,
          created_by,
          modified_by,
          type,
          title,
          content,
          course_id,
          created_on,
          modified_on
      ) VALUES (
          ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?
      )
      """.trim();

  public CourseHistoryMigrationService(JdbcTemplate jdbcTemplate) {
    super(jdbcTemplate);
  }

  @Override
  protected String getDirPath() {
    return DIR_PATH;
  }

  @Override
  protected String getTableName() {
    return "course_history";
  }

//  @Override
//  protected List<String> getTruncateTableName() {
//    return List.of(getTableName());
//  }

  @Override
  protected String getCsvFileName() {
    return "수강관리이력.csv";
  }

  @Override
  protected FieldMappingProvider getFieldMappingProvider() {
    return new CourseHistoryFieldMapping();
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
      ps.setString(idx++, getValue(row, mappingProvider, "module", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "moduleId", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "createdBy", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "modifiedBy", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "type", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "title", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "content", String.class));
      ps.setObject(idx++, getValue(row, mappingProvider, "courseId", Long.class));
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
        getValue(row, mappingProvider, "module", String.class),
        getValue(row, mappingProvider, "moduleId", String.class),
        getValue(row, mappingProvider, "createdBy", String.class),
        getValue(row, mappingProvider, "modifiedBy", String.class),
        getValue(row, mappingProvider, "type", String.class),
        getValue(row, mappingProvider, "title", String.class),
        getValue(row, mappingProvider, "content", String.class),
        getValue(row, mappingProvider, "courseId", Long.class),
        getValue(row, mappingProvider, "createdOn", LocalDateTime.class),
        getValue(row, mappingProvider, "modifiedOn", LocalDateTime.class)
    };
  }
}
