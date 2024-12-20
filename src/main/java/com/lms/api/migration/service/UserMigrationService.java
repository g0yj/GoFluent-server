package com.lms.api.migration.service;

import static com.lms.api.migration.MigrationRunner.DIR_PATH;

import com.lms.api.migration.mapping.FieldMappingProvider;
import com.lms.api.migration.mapping.UserFieldMapping;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserMigrationService extends BaseMigrationService {

  private final String ENC_PASS = "$2a$10$bnxOzQZpdKqRcKfAkL2TxuogsqenWLAmYuQzurt1cKtMpBtJyvPD2";
  private final String SQL = """
    INSERT INTO user_
    (id, login_id, name, name_en, last_name_en, password, `type`, gender, phone, phone_type, 
      cell_phone, is_receive_sms, email, is_receive_email, zipcode, address, detailed_address, 
      address_type, is_office_worker, company, `position`, join_path, `language`, etc_language, 
      language_skill, is_active, foreign_country, foreign_period, foreign_purpose, 
      course_purpose, withdrawal_reason, note, nickname, textbook, created_on, modified_on)
    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 
      ?, ?, ?, ?, ?, ?, ?)
    """;

  public UserMigrationService(JdbcTemplate jdbcTemplate) {
    super(jdbcTemplate);
  }

  @Override
  protected String getDirPath() {
    return DIR_PATH;
  }

  @Override
  protected String getTableName() {
    return "user_";
  }

//  @Override
//  protected List<String> getTruncateTableName() {
//    return List.of("ldf", "level_test", "course", getTableName());
//  }

  @Override
  protected String getCsvFileName() {
    return "사용자관리.csv";
  }

  protected void processRow(String[] row, FieldMappingProvider fieldMappingProvider) {
    jdbcTemplate.update(connection -> {
      PreparedStatement ps = connection.prepareStatement(SQL);
      int idx = 1;

      ps.setString(idx++, getValue(row, fieldMappingProvider, "id", String.class));
      ps.setString(idx++, getValue(row, fieldMappingProvider, "loginId", String.class));
      ps.setString(idx++, getValue(row, fieldMappingProvider, "name", String.class));
      ps.setString(idx++, getValue(row, fieldMappingProvider, "nameEn", String.class));
      ps.setString(idx++, getValue(row, fieldMappingProvider, "lastNameEn", String.class));
      ps.setString(idx++, getValue(row, fieldMappingProvider, "password", String.class));
      ps.setString(idx++, getValue(row, fieldMappingProvider, "type", String.class));
      ps.setString(idx++, getValue(row, fieldMappingProvider, "gender", String.class));
      ps.setString(idx++, getValue(row, fieldMappingProvider, "phone", String.class));
      ps.setString(idx++, getValue(row, fieldMappingProvider, "phoneType", String.class));
      ps.setString(idx++, getValue(row, fieldMappingProvider, "cellPhone", String.class));
      ps.setString(idx++, getValue(row, fieldMappingProvider, "isReceiveSms", String.class));
      ps.setString(idx++, getValue(row, fieldMappingProvider, "email", String.class));
      ps.setString(idx++, getValue(row, fieldMappingProvider, "isReceiveEmail", String.class));
      ps.setString(idx++, getValue(row, fieldMappingProvider, "zipcode", String.class));
      ps.setString(idx++, getValue(row, fieldMappingProvider, "address", String.class));
      ps.setString(idx++, getValue(row, fieldMappingProvider, "detailedAddress", String.class));
      ps.setString(idx++, getValue(row, fieldMappingProvider, "addressType", String.class));
      ps.setString(idx++, getValue(row, fieldMappingProvider, "isOfficeWorker", String.class));
      ps.setString(idx++, getValue(row, fieldMappingProvider, "company", String.class));
      ps.setString(idx++, getValue(row, fieldMappingProvider, "position", String.class));
      ps.setString(idx++, getValue(row, fieldMappingProvider, "joinPath", String.class));
      ps.setString(idx++, getValue(row, fieldMappingProvider, "language", String.class));
      ps.setString(idx++, getValue(row, fieldMappingProvider, "etcLanguage", String.class));
      ps.setString(idx++, getValue(row, fieldMappingProvider, "languageSkill", String.class));
      ps.setInt(idx++, getValue(row, fieldMappingProvider, "active", Integer.class));
      ps.setString(idx++, getValue(row, fieldMappingProvider, "foreignCountry", String.class));
      ps.setString(idx++, getValue(row, fieldMappingProvider, "foreignPeriod", String.class));
      ps.setString(idx++, getValue(row, fieldMappingProvider, "foreignPurpose", String.class));
      ps.setString(idx++, getValue(row, fieldMappingProvider, "coursePurpose", String.class));
      ps.setString(idx++, getValue(row, fieldMappingProvider, "withdrawalReason", String.class));
      ps.setString(idx++, getValue(row, fieldMappingProvider, "note", String.class));
      ps.setString(idx++, getValue(row, fieldMappingProvider, "nickname", String.class));
      ps.setString(idx++, getValue(row, fieldMappingProvider, "textbook", String.class));
      ps.setObject(idx++, getValue(row, fieldMappingProvider, "createdOn", LocalDateTime.class));
      ps.setObject(idx++, getValue(row, fieldMappingProvider, "modifiedOn", LocalDateTime.class));

      return ps;
    });
  }

  @Override
  protected FieldMappingProvider getFieldMappingProvider() {
    return new UserFieldMapping();
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
        getValue(row, mappingProvider, "id", String.class),
        getValue(row, mappingProvider, "loginId", String.class),
        getValue(row, mappingProvider, "name", String.class),
        getValue(row, mappingProvider, "nameEn", String.class),
        getValue(row, mappingProvider, "lastNameEn", String.class),
        getValue(row, mappingProvider, "password", String.class),
        getValue(row, mappingProvider, "type", String.class),
        getValue(row, mappingProvider, "gender", String.class),
        getValue(row, mappingProvider, "phone", String.class),
        getValue(row, mappingProvider, "phoneType", String.class),
        getValue(row, mappingProvider, "cellPhone", String.class),
        getValue(row, mappingProvider, "isReceiveSms", String.class),
        getValue(row, mappingProvider, "email", String.class),
        getValue(row, mappingProvider, "isReceiveEmail", String.class),
        getValue(row, mappingProvider, "zipcode", String.class),
        getValue(row, mappingProvider, "address", String.class),
        getValue(row, mappingProvider, "detailedAddress", String.class),
        getValue(row, mappingProvider, "addressType", String.class),
        getValue(row, mappingProvider, "isOfficeWorker", String.class),
        getValue(row, mappingProvider, "company", String.class),
        getValue(row, mappingProvider, "position", String.class),
        getValue(row, mappingProvider, "joinPath", String.class),
        getValue(row, mappingProvider, "language", String.class),
        getValue(row, mappingProvider, "etcLanguage", String.class),
        getValue(row, mappingProvider, "languageSkill", String.class),
        getValue(row, mappingProvider, "active", Integer.class),
        getValue(row, mappingProvider, "foreignCountry", String.class),
        getValue(row, mappingProvider, "foreignPeriod", String.class),
        getValue(row, mappingProvider, "foreignPurpose", String.class),
        getValue(row, mappingProvider, "coursePurpose", String.class),
        getValue(row, mappingProvider, "withdrawalReason", String.class),
        getValue(row, mappingProvider, "note", String.class),
        getValue(row, mappingProvider, "nickname", String.class),
        getValue(row, mappingProvider, "textbook", String.class),
        getValue(row, mappingProvider, "createdOn", LocalDateTime.class),
        getValue(row, mappingProvider, "modifiedOn", LocalDateTime.class),
        getValue(row, mappingProvider, "lessonInfo", String.class),
    };
  }

  @Override
  protected List<String> getReplaceMultipleSpacesWithNewlineFieldNames() {
    return List.of("note");
  }

}

