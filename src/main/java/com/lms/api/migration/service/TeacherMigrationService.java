package com.lms.api.migration.service;

import static com.lms.api.migration.MigrationRunner.DIR_PATH;

import com.lms.api.migration.mapping.FieldMappingProvider;
import com.lms.api.migration.mapping.TeacherFieldMapping;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TeacherMigrationService extends BaseMigrationService {


  private final String SQL = """
        INSERT INTO teacher (
            user_id, 
            `type`, 
            partner_teacher_id, 
            work_start_date, 
            work_time, 
            work_type, 
            sms_type, 
            home_address, 
            home_phone, 
            cell_phone, 
            cell_phone_device_number, 
            cell_phone_lease_start_date, 
            cell_phone_lease_end_date, 
            cell_phone_device_price, 
            cell_phone_device_statue, 
            university, 
            major, 
            previous_work, 
            nationality, 
            visa_type, 
            visa_entry_type, 
            visa_end_date, 
            arrival_date, 
            arrival_note, 
            stay_start_date, 
            stay_end_date, 
            re_entry_note, 
            sponsor_start_date, 
            sponsor_end_date, 
            training_day, 
            visa_note, 
            contract_start_date, 
            contract_end_date, 
            contract_basic_salary, 
            contract_note, 
            pnp_code, 
            airfare_payment, 
            deposit, 
            monthly, 
            hire_type, 
            landlord, 
            zipcode, 
            address, 
            detailed_address, 
            phone, 
            hire_note, 
            hire_start_date, 
            hire_end_date, 
            education_office, 
            recruitment_date, 
            education_office_note, 
            education_office_manager, 
            contract_expiration_date, 
            dismissal_immi_date, 
            departure_date, 
            dismissal_manager, 
            dismissal_me_date, 
            release_note, 
            dismissal_note, 
            basic_salary, 
            housing_cost, 
            management_cost, 
            national_pension, 
            health_insurance, 
            care_insurance, 
            employment_insurance, 
            sort, 
            is_active, 
            `language`, 
            created_on, 
            modified_on
        ) VALUES (
          ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,
          ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,
          ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,
          ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,
          ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,
          ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,
          ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,
          ?
        )
        """.trim();

  public TeacherMigrationService(JdbcTemplate jdbcTemplate) {
    super(jdbcTemplate);
  }

  @Override
  protected String getDirPath() {
    return DIR_PATH;
  }

  @Override
  protected String getTableName() {
    return "teacher";
  }

//  @Override
//  protected List<String> getTruncateTableName() {
//    return List.of("schedule", getTableName());
//  }

  @Override
  protected String getCsvFileName() {
    return "강사정보관리.csv";
  }

  @Override
  protected FieldMappingProvider getFieldMappingProvider() {
    return new TeacherFieldMapping();
  }

  @Override
  protected boolean useAutoIncrementId() {
    return false;
  }

  @Override
  protected void processRow(String[] row, FieldMappingProvider mappingProvider) {
    //for
    jdbcTemplate.update(connection -> {
      PreparedStatement ps = connection.prepareStatement(SQL);
      int idx = 1;

      ps.setString(idx++, getValue(row, mappingProvider, "userId", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "type", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "partnerTeacherId", String.class));
      ps.setObject(idx++, getValue(row, mappingProvider, "workStartDate", LocalDate.class));
      ps.setString(idx++, getValue(row, mappingProvider, "workTime", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "workType", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "smsType", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "homeAddress", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "homePhone", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "cellPhone", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "cellPhoneDeviceNumber", String.class));
      ps.setObject(idx++, getValue(row, mappingProvider, "cellPhoneLeaseStartDate", LocalDate.class));
      ps.setObject(idx++, getValue(row, mappingProvider, "cellPhoneLeaseEndDate", LocalDate.class));
      ps.setString(idx++, getValue(row, mappingProvider, "cellPhoneDevicePrice", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "cellPhoneDeviceStatue", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "university", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "major", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "previousWork", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "nationality", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "visaType", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "visaEntryType", String.class));
      ps.setObject(idx++, getValue(row, mappingProvider, "visaEndDate", LocalDate.class));
      ps.setObject(idx++, getValue(row, mappingProvider, "arrivalDate", LocalDate.class));
      ps.setString(idx++, getValue(row, mappingProvider, "arrivalNote", String.class));
      ps.setObject(idx++, getValue(row, mappingProvider, "stayStartDate", LocalDate.class));
      ps.setObject(idx++, getValue(row, mappingProvider, "stayEndDate", LocalDate.class));
      ps.setString(idx++, getValue(row, mappingProvider, "reEntryNote", String.class));
      ps.setObject(idx++, getValue(row, mappingProvider, "sponsorStartDate", LocalDate.class));
      ps.setObject(idx++, getValue(row, mappingProvider, "sponsorEndDate", LocalDate.class));
      ps.setString(idx++, getValue(row, mappingProvider, "trainingDay", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "visaNote", String.class));
      ps.setObject(idx++, getValue(row, mappingProvider, "contractStartDate", LocalDate.class));
      ps.setObject(idx++, getValue(row, mappingProvider, "contractEndDate", LocalDate.class));
      ps.setString(idx++, getValue(row, mappingProvider, "contractBasicSalary", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "contractNote", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "pnpCode", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "airfarePayment", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "deposit", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "monthly", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "hireType", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "landlord", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "zipcode", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "address", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "detailedAddress", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "phone", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "hireNote", String.class));
      ps.setObject(idx++, getValue(row, mappingProvider, "hireStartDate", LocalDate.class));
      ps.setObject(idx++, getValue(row, mappingProvider, "hireEndDate", LocalDate.class));
      ps.setString(idx++, getValue(row, mappingProvider, "educationOffice", String.class));
      ps.setObject(idx++, getValue(row, mappingProvider, "recruitmentDate", LocalDate.class));
      ps.setString(idx++, getValue(row, mappingProvider, "educationOfficeNote", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "educationOfficeManager", String.class));
      ps.setObject(idx++, getValue(row, mappingProvider, "contractExpirationDate", LocalDate.class));
      ps.setObject(idx++, getValue(row, mappingProvider, "dismissalImmiDate", LocalDate.class));
      ps.setObject(idx++, getValue(row, mappingProvider, "departureDate", LocalDate.class));
      ps.setString(idx++, getValue(row, mappingProvider, "dismissalManager", String.class));
      ps.setObject(idx++, getValue(row, mappingProvider, "dismissalMeDate", LocalDate.class));
      ps.setString(idx++, getValue(row, mappingProvider, "releaseNote", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "dismissalNote", String.class));
      ps.setInt(idx++, getValue(row, mappingProvider, "basicSalary", Integer.class));
      ps.setInt(idx++, getValue(row, mappingProvider, "housingCost", Integer.class));
      ps.setInt(idx++, getValue(row, mappingProvider, "managementCost", Integer.class));
      ps.setInt(idx++, getValue(row, mappingProvider, "nationalPension", Integer.class));
      ps.setInt(idx++, getValue(row, mappingProvider, "healthInsurance", Integer.class));
      ps.setInt(idx++, getValue(row, mappingProvider, "careInsurance", Integer.class));
      ps.setInt(idx++, getValue(row, mappingProvider, "employmentInsurance", Integer.class));
      ps.setInt(idx++, getValue(row, mappingProvider, "sort", Integer.class));
      ps.setInt(idx++, getValue(row, mappingProvider, "isActive", Integer.class));
      ps.setString(idx++, getValue(row, mappingProvider, "language", String.class));
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
        getValue(row, mappingProvider, "userId", String.class),
        getValue(row, mappingProvider, "type", String.class),
        getValue(row, mappingProvider, "partnerTeacherId", String.class),
        getValue(row, mappingProvider, "workStartDate", LocalDate.class),
        getValue(row, mappingProvider, "workTime", String.class),
        getValue(row, mappingProvider, "workType", String.class),
        getValue(row, mappingProvider, "smsType", String.class),
        getValue(row, mappingProvider, "homeAddress", String.class),
        getValue(row, mappingProvider, "homePhone", String.class),
        getValue(row, mappingProvider, "cellPhone", String.class),
        getValue(row, mappingProvider, "cellPhoneDeviceNumber", String.class),
        getValue(row, mappingProvider, "cellPhoneLeaseStartDate", LocalDate.class),
        getValue(row, mappingProvider, "cellPhoneLeaseEndDate", LocalDate.class),
        getValue(row, mappingProvider, "cellPhoneDevicePrice", String.class),
        getValue(row, mappingProvider, "cellPhoneDeviceStatue", String.class),
        getValue(row, mappingProvider, "university", String.class),
        getValue(row, mappingProvider, "major", String.class),
        getValue(row, mappingProvider, "previousWork", String.class),
        getValue(row, mappingProvider, "nationality", String.class),
        getValue(row, mappingProvider, "visaType", String.class),
        getValue(row, mappingProvider, "visaEntryType", String.class),
        getValue(row, mappingProvider, "visaEndDate", LocalDate.class),
        getValue(row, mappingProvider, "arrivalDate", LocalDate.class),
        getValue(row, mappingProvider, "arrivalNote", String.class),
        getValue(row, mappingProvider, "stayStartDate", LocalDate.class),
        getValue(row, mappingProvider, "stayEndDate", LocalDate.class),
        getValue(row, mappingProvider, "reEntryNote", String.class),
        getValue(row, mappingProvider, "sponsorStartDate", LocalDate.class),
        getValue(row, mappingProvider, "sponsorEndDate", LocalDate.class),
        getValue(row, mappingProvider, "trainingDay", String.class),
        getValue(row, mappingProvider, "visaNote", String.class),
        getValue(row, mappingProvider, "contractStartDate", LocalDate.class),
        getValue(row, mappingProvider, "contractEndDate", LocalDate.class),
        getValue(row, mappingProvider, "contractBasicSalary", String.class),
        getValue(row, mappingProvider, "contractNote", String.class),
        getValue(row, mappingProvider, "pnpCode", String.class),
        getValue(row, mappingProvider, "airfarePayment", String.class),
        getValue(row, mappingProvider, "deposit", String.class),
        getValue(row, mappingProvider, "monthly", String.class),
        getValue(row, mappingProvider, "hireType", String.class),
        getValue(row, mappingProvider, "landlord", String.class),
        getValue(row, mappingProvider, "zipcode", String.class),
        getValue(row, mappingProvider, "address", String.class),
        getValue(row, mappingProvider, "detailedAddress", String.class),
        getValue(row, mappingProvider, "phone", String.class),
        getValue(row, mappingProvider, "hireNote", String.class),
        getValue(row, mappingProvider, "hireStartDate", LocalDate.class),
        getValue(row, mappingProvider, "hireEndDate", LocalDate.class),
        getValue(row, mappingProvider, "educationOffice", String.class),
        getValue(row, mappingProvider, "recruitmentDate", LocalDate.class),
        getValue(row, mappingProvider, "educationOfficeNote", String.class),
        getValue(row, mappingProvider, "educationOfficeManager", String.class),
        getValue(row, mappingProvider, "contractExpirationDate", LocalDate.class),
        getValue(row, mappingProvider, "dismissalImmiDate", LocalDate.class),
        getValue(row, mappingProvider, "departureDate", LocalDate.class),
        getValue(row, mappingProvider, "dismissalManager", String.class),
        getValue(row, mappingProvider, "dismissalMeDate", LocalDate.class),
        getValue(row, mappingProvider, "releaseNote", String.class),
        getValue(row, mappingProvider, "dismissalNote", String.class),
        getValue(row, mappingProvider, "basicSalary", Integer.class),
        getValue(row, mappingProvider, "housingCost", Integer.class),
        getValue(row, mappingProvider, "managementCost", Integer.class),
        getValue(row, mappingProvider, "nationalPension", Integer.class),
        getValue(row, mappingProvider, "healthInsurance", Integer.class),
        getValue(row, mappingProvider, "careInsurance", Integer.class),
        getValue(row, mappingProvider, "employmentInsurance", Integer.class),
        getValue(row, mappingProvider, "sort", Integer.class),
        getValue(row, mappingProvider, "isActive", Integer.class),
        getValue(row, mappingProvider, "language", String.class),
        getValue(row, mappingProvider, "createdOn", LocalDateTime.class),
        getValue(row, mappingProvider, "modifiedOn", LocalDateTime.class)
    };
  }
}
