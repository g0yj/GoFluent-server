package com.lms.api.migration.service;

import static com.lms.api.migration.MigrationRunner.DIR_PATH;

import com.lms.api.migration.mapping.FieldMappingProvider;
import com.lms.api.migration.mapping.LevelTestFieldMapping;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LevelTestMigrationService extends BaseMigrationService {

  private final String SQL = """
      INSERT INTO level_test (
          id,
          user_id,
          test_start_time,
          lbt,
          rbt,
          obt,
          note,
          created_on,
          modified_on,
          purpose,
          study_type, 
          family_background,
          usage_type,
          occupation,
          spare_time,
          travel_abroad,
          future_plans,
          consonants,
          vowels,
          clarity,
          intonation,
          vocabulary,
          verbs_tense,
          agreement,
          prepositions,
          articles,
          plurals,
          others,
          strong_point,
          weak_point,
          comprehension,
          confidence,
          comments,
          recommended_level,
          recommended_level_etc,
          interviewer
      ) VALUES (
          ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?
      )
      """.trim();

  public LevelTestMigrationService(JdbcTemplate jdbcTemplate) {
    super(jdbcTemplate);
  }

  @Override
  protected String getDirPath() {
    return DIR_PATH;
  }

  @Override
  protected String getTableName() {
    return "level_test";
  }

//  @Override
//  protected List<String> getTruncateTableName() {
//    return List.of(getTableName());
//  }

  @Override
  protected String getCsvFileName() {
    return "레벨테스트.csv";
  }

  @Override
  protected FieldMappingProvider getFieldMappingProvider() {
    return new LevelTestFieldMapping();
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
      ps.setObject(idx++, getValue(row, mappingProvider, "testStartTime", LocalDateTime.class));
      ps.setString(idx++, getValue(row, mappingProvider, "lbt", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "rbt", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "obt", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "note", String.class));
      ps.setObject(idx++, getValue(row, mappingProvider, "createdOn", LocalDateTime.class));
      ps.setObject(idx++, getValue(row, mappingProvider, "modifiedOn", LocalDateTime.class));
      ps.setObject(idx++, getValue(row, mappingProvider, "purpose", LocalDateTime.class));
      ps.setObject(idx++, getValue(row, mappingProvider, "studyType", LocalDateTime.class));
      ps.setString(idx++, getValue(row, mappingProvider, "familyBackground", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "usageType", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "occupation", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "spareTime", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "travelAbroad", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "futurePlans", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "consonants", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "vowels", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "clarity", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "intonation", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "vocabulary", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "verbsTense", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "agreement", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "prepositions", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "articles", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "plurals", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "others", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "strongPoint", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "weakPoint", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "comprehension", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "confidence", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "comments", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "recommendedLevel", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "recommendedLevelEtc", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "interviewer", String.class));

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
        getValue(row, mappingProvider, "testStartTime", LocalDateTime.class),
        getValue(row, mappingProvider, "lbt", String.class),
        getValue(row, mappingProvider, "rbt", String.class),
        getValue(row, mappingProvider, "obt", String.class),
        getValue(row, mappingProvider, "note", String.class),
        getValue(row, mappingProvider, "createdOn", LocalDateTime.class),
        getValue(row, mappingProvider, "modifiedOn", LocalDateTime.class),
        getValue(row, mappingProvider, "purpose", String.class),
        getValue(row, mappingProvider, "studyType", String.class),
        getValue(row, mappingProvider, "familyBackground", String.class),
        getValue(row, mappingProvider, "usageType", String.class),
        getValue(row, mappingProvider, "occupation", String.class),
        getValue(row, mappingProvider, "spareTime", String.class),
        getValue(row, mappingProvider, "travelAbroad", String.class),
        getValue(row, mappingProvider, "futurePlans", String.class),
        getValue(row, mappingProvider, "consonants", String.class),
        getValue(row, mappingProvider, "vowels", String.class),
        getValue(row, mappingProvider, "clarity", String.class),
        getValue(row, mappingProvider, "intonation", String.class),
        getValue(row, mappingProvider, "vocabulary", String.class),
        getValue(row, mappingProvider, "verbsTense", String.class),
        getValue(row, mappingProvider, "agreement", String.class),
        getValue(row, mappingProvider, "prepositions", String.class),
        getValue(row, mappingProvider, "articles", String.class),
        getValue(row, mappingProvider, "plurals", String.class),
        getValue(row, mappingProvider, "others", String.class),
        getValue(row, mappingProvider, "strongPoint", String.class),
        getValue(row, mappingProvider, "weakPoint", String.class),
        getValue(row, mappingProvider, "comprehension", String.class),
        getValue(row, mappingProvider, "confidence", String.class),
        getValue(row, mappingProvider, "comments", String.class),
        getValue(row, mappingProvider, "recommendedLevel", String.class),
        getValue(row, mappingProvider, "recommendedLevelEtc", String.class),
        getValue(row, mappingProvider, "interviewer", String.class)
    };
  }
}
