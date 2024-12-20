package com.lms.api.migration.service;

import static com.lms.api.migration.MigrationRunner.DIR_PATH;

import com.lms.api.migration.mapping.FieldMappingProvider;
import com.lms.api.migration.mapping.OrderFieldMapping;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderMigrationService extends BaseMigrationService {

  private final String SQL = """
      INSERT INTO order_ (
          id, 
          type, 
          user_id, 
          supply_amount, 
          discount_amount, 
          billing_amount, 
          cash_amount, 
          deposit_amount, 
          card_count, 
          card_amount, 
          payment_amount, 
          receivable_amount, 
          refund_amount, 
          created_by, 
          modified_by, 
          created_on, 
          modified_on, 
          recall_date, 
          receivable_reason
      ) VALUES (
          ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 
          ?, ?, ?, ?, ?, ?, ?, ?, ?
      )
      """.trim();

  public OrderMigrationService(JdbcTemplate jdbcTemplate) {
    super(jdbcTemplate);
  }

  @Override
  protected String getDirPath() {
    return DIR_PATH;
  }

  @Override
  protected String getTableName() {
    return "order_";
  }

//  @Override
//  protected List<String> getTruncateTableName() {
//    return List.of("order_product", getTableName());
//  }

  @Override
  protected String getCsvFileName() {
    return "주문관리.csv";
  }

  @Override
  protected FieldMappingProvider getFieldMappingProvider() {
    return new OrderFieldMapping();
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
      ps.setString(idx++, getValue(row, mappingProvider, "type", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "userId", String.class));
      ps.setInt(idx++, getValue(row, mappingProvider, "supplyAmount", Integer.class));
      ps.setInt(idx++, getValue(row, mappingProvider, "discountAmount", Integer.class));
      ps.setInt(idx++, getValue(row, mappingProvider, "billingAmount", Integer.class));
      ps.setInt(idx++, getValue(row, mappingProvider, "cashAmount", Integer.class));
      ps.setInt(idx++, getValue(row, mappingProvider, "depositAmount", Integer.class));
      ps.setInt(idx++, getValue(row, mappingProvider, "cardCount", Integer.class));
      ps.setInt(idx++, getValue(row, mappingProvider, "cardAmount", Integer.class));
      ps.setInt(idx++, getValue(row, mappingProvider, "paymentAmount", Integer.class));
      ps.setInt(idx++, getValue(row, mappingProvider, "receivableAmount", Integer.class));
      ps.setInt(idx++, getValue(row, mappingProvider, "refundAmount", Integer.class));
      ps.setString(idx++, getValue(row, mappingProvider, "createdBy", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "modifiedBy", String.class));
      ps.setObject(idx++, getValue(row, mappingProvider, "createdOn", LocalDateTime.class));
      ps.setObject(idx++, getValue(row, mappingProvider, "modifiedOn", LocalDateTime.class));
      ps.setObject(idx++, getValue(row, mappingProvider, "recallDate", LocalDate.class));
      ps.setString(idx++, getValue(row, mappingProvider, "receivableReason", String.class));

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
        getValue(row, mappingProvider, "type", String.class),
        getValue(row, mappingProvider, "userId", String.class),
        getValue(row, mappingProvider, "supplyAmount", Integer.class),
        getValue(row, mappingProvider, "discountAmount", Integer.class),
        getValue(row, mappingProvider, "billingAmount", Integer.class),
        getValue(row, mappingProvider, "cashAmount", Integer.class),
        getValue(row, mappingProvider, "depositAmount", Integer.class),
        getValue(row, mappingProvider, "cardCount", Integer.class),
        getValue(row, mappingProvider, "cardAmount", Integer.class),
        getValue(row, mappingProvider, "paymentAmount", Integer.class),
        getValue(row, mappingProvider, "receivableAmount", Integer.class),
        getValue(row, mappingProvider, "refundAmount", Integer.class),
        getValue(row, mappingProvider, "createdBy", String.class),
        getValue(row, mappingProvider, "modifiedBy", String.class),
        getValue(row, mappingProvider, "createdOn", LocalDateTime.class),
        getValue(row, mappingProvider, "modifiedOn", LocalDateTime.class),
        getValue(row, mappingProvider, "recallDate", LocalDate.class),
        getValue(row, mappingProvider, "receivableReason", String.class)
    };
  }
}
