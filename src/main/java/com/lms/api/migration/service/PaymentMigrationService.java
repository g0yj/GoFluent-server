package com.lms.api.migration.service;

import static com.lms.api.migration.MigrationRunner.DIR_PATH;

import com.lms.api.migration.mapping.FieldMappingProvider;
import com.lms.api.migration.mapping.PaymentFieldMapping;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PaymentMigrationService extends BaseMigrationService {

  private final String SQL = """
      INSERT INTO payment (
          id, 
          user_id, 
          order_id, 
          payment_date, 
          type, 
          payment_method, 
          payment_amount, 
          account_holder, 
          account_number, 
          card_company, 
          card_number, 
          installment_months, 
          approval_number, 
          cancel_amount, 
          cancel_date, 
          cancel_manager, 
          created_by, 
          modified_by, 
          created_on, 
          modified_on, 
          memo, 
          is_receipt_issued, 
          receipt_number, 
          deposit_amount, 
          transaction_name, 
          company_number, 
          account_transaction_date
      ) VALUES (
          ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 
          ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 
          ?, ?, ?, ?, ?, ?, ?
      )
      """.trim();

  public PaymentMigrationService(JdbcTemplate jdbcTemplate) {
    super(jdbcTemplate);
  }

  @Override
  protected String getDirPath() {
    return DIR_PATH;
  }

  @Override
  protected String getTableName() {
    return "payment";
  }

//  @Override
//  protected List<String> getTruncateTableName() {
//    return List.of(getTableName());
//  }

  @Override
  protected String getCsvFileName() {
    return "주문결제관리.csv";
  }

  @Override
  protected FieldMappingProvider getFieldMappingProvider() {
    return new PaymentFieldMapping();
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
      ps.setString(idx++, getValue(row, mappingProvider, "userId", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "orderId", String.class));
      ps.setObject(idx++, getValue(row, mappingProvider, "paymentDate", LocalDate.class));
      ps.setString(idx++, getValue(row, mappingProvider, "type", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "paymentMethod", String.class));
      ps.setInt(idx++, getValue(row, mappingProvider, "paymentAmount", Integer.class));
      ps.setString(idx++, getValue(row, mappingProvider, "accountHolder", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "accountNumber", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "cardCompany", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "cardNumber", String.class));
      ps.setInt(idx++, getValue(row, mappingProvider, "installmentMonths", Integer.class));
      ps.setString(idx++, getValue(row, mappingProvider, "approvalNumber", String.class));
      ps.setInt(idx++, getValue(row, mappingProvider, "cancelAmount", Integer.class));
      ps.setObject(idx++, getValue(row, mappingProvider, "cancelDate", LocalDate.class));
      ps.setString(idx++, getValue(row, mappingProvider, "cancelManager", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "createdBy", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "modifiedBy", String.class));
      ps.setObject(idx++, getValue(row, mappingProvider, "createdOn", LocalDateTime.class));
      ps.setObject(idx++, getValue(row, mappingProvider, "modifiedOn", LocalDateTime.class));
      ps.setString(idx++, getValue(row, mappingProvider, "memo", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "isReceiptIssued", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "receiptNumber", String.class));
      ps.setInt(idx++, getValue(row, mappingProvider, "depositAmount", Integer.class));
      ps.setString(idx++, getValue(row, mappingProvider, "transactionName", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "companyNumber", String.class));
      ps.setObject(idx++,
          getValue(row, mappingProvider, "accountTransactionDate", LocalDateTime.class));

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
        getValue(row, mappingProvider, "orderId", String.class),
        getValue(row, mappingProvider, "paymentDate", LocalDate.class),
        getValue(row, mappingProvider, "type", String.class),
        getValue(row, mappingProvider, "paymentMethod", String.class),
        getValue(row, mappingProvider, "paymentAmount", Integer.class),
        getValue(row, mappingProvider, "accountHolder", String.class),
        getValue(row, mappingProvider, "accountNumber", String.class),
        getValue(row, mappingProvider, "cardCompany", String.class),
        getValue(row, mappingProvider, "cardNumber", String.class),
        getValue(row, mappingProvider, "installmentMonths", Integer.class),
        getValue(row, mappingProvider, "approvalNumber", String.class),
        getValue(row, mappingProvider, "cancelAmount", Integer.class),
        getValue(row, mappingProvider, "cancelDate", LocalDate.class),
        getValue(row, mappingProvider, "cancelManager", String.class),
        getValue(row, mappingProvider, "createdBy", String.class),
        getValue(row, mappingProvider, "modifiedBy", String.class),
        getValue(row, mappingProvider, "createdOn", LocalDateTime.class),
        getValue(row, mappingProvider, "modifiedOn", LocalDateTime.class),
        getValue(row, mappingProvider, "memo", String.class),
        getValue(row, mappingProvider, "isReceiptIssued", String.class),
        getValue(row, mappingProvider, "receiptNumber", String.class),
        getValue(row, mappingProvider, "depositAmount", Integer.class),
        getValue(row, mappingProvider, "transactionName", String.class),
        getValue(row, mappingProvider, "companyNumber", String.class),
        getValue(row, mappingProvider, "accountTransactionDate", LocalDateTime.class)
    };
  }
}
