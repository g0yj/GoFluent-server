package com.lms.api.migration.service;

import static com.lms.api.migration.MigrationRunner.DIR_PATH;

import com.lms.api.migration.mapping.FieldMappingProvider;
import com.lms.api.migration.mapping.RefundFieldMapping;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RefundMigrationService extends BaseMigrationService {

  private final String SQL = """
      INSERT INTO refund (
          id, 
          user_id, 
          order_product_id, 
          refund_date, 
          refund_amount, 
          cash_amount, 
          deposit_amount, 
          bank, 
          account_number, 
          refund_reason, 
          created_by, 
          modified_by, 
          created_on, 
          modified_on
      ) VALUES (
          ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?
      )
      """.trim();

  public RefundMigrationService(JdbcTemplate jdbcTemplate) {
    super(jdbcTemplate);
  }

  @Override
  protected String getDirPath() {
    return DIR_PATH;
  }

  @Override
  protected String getTableName() {
    return "refund";
  }

//  @Override
//  protected List<String> getTruncateTableName() {
//    return List.of(getTableName());
//  }

  @Override
  protected String getCsvFileName() {
    return "주문환불관리.csv";
  }

  @Override
  protected FieldMappingProvider getFieldMappingProvider() {
    return new RefundFieldMapping();
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

      ps.setString(idx++, getValue(row, mappingProvider, "id", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "userId", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "orderProductId", String.class));
      ps.setObject(idx++, getValue(row, mappingProvider, "refundDate", LocalDate.class));
      ps.setInt(idx++, getValue(row, mappingProvider, "refundAmount", Integer.class));
      ps.setInt(idx++, getValue(row, mappingProvider, "cashAmount", Integer.class));
      ps.setInt(idx++, getValue(row, mappingProvider, "depositAmount", Integer.class));
      ps.setString(idx++, getValue(row, mappingProvider, "bank", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "accountNumber", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "refundReason", String.class));
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
        getValue(row, mappingProvider, "id", String.class),
        getValue(row, mappingProvider, "userId", String.class),
        getValue(row, mappingProvider, "orderProductId", String.class),
        getValue(row, mappingProvider, "refundDate", LocalDate.class),
        getValue(row, mappingProvider, "refundAmount", Integer.class),
        getValue(row, mappingProvider, "cashAmount", Integer.class),
        getValue(row, mappingProvider, "depositAmount", Integer.class),
        getValue(row, mappingProvider, "bank", String.class),
        getValue(row, mappingProvider, "accountNumber", String.class),
        getValue(row, mappingProvider, "refundReason", String.class),
        getValue(row, mappingProvider, "createdBy", String.class),
        getValue(row, mappingProvider, "modifiedBy", String.class),
        getValue(row, mappingProvider, "createdOn", LocalDateTime.class),
        getValue(row, mappingProvider, "modifiedOn", LocalDateTime.class)
    };
  }
}
