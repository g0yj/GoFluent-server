package com.lms.api.migration.service;

import static com.lms.api.migration.MigrationRunner.DIR_PATH;

import com.lms.api.migration.mapping.FieldMappingProvider;
import com.lms.api.migration.mapping.OrderProductFieldMapping;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderProductMigrationService extends BaseMigrationService {

  private final String SQL = """
      INSERT INTO order_product (
          id, 
          order_id, 
          product_id, 
          months, 
          teacher_id, 
          quantity, 
          product_option, 
          is_retake, 
          retake_teacher_id, 
          retake_note, 
          amount, 
          payment_amount, 
          discount_amount, 
          note, 
          created_by, 
          modified_by, 
          created_on, 
          modified_on
      ) VALUES (
          ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 
          ?, ?, ?, ?, ?, ?, ?, ?
      )
      """.trim();

  public OrderProductMigrationService(JdbcTemplate jdbcTemplate) {
    super(jdbcTemplate);
  }

  @Override
  protected String getDirPath() {
    return DIR_PATH;
  }

  @Override
  protected String getTableName() {
    return "order_product";
  }

//  @Override
//  protected List<String> getTruncateTableName() {
//    return List.of(getTableName());
//  }

  @Override
  protected String getCsvFileName() {
    return "주문아이템관리.csv";
  }

  @Override
  protected FieldMappingProvider getFieldMappingProvider() {
    return new OrderProductFieldMapping();
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
      ps.setString(idx++, getValue(row, mappingProvider, "orderId", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "productId", String.class));
      ps.setInt(idx++, getValue(row, mappingProvider, "months", Integer.class));
      ps.setString(idx++, getValue(row, mappingProvider, "teacherId", String.class));
      ps.setInt(idx++, getValue(row, mappingProvider, "quantity", Integer.class));
      ps.setString(idx++, getValue(row, mappingProvider, "productOption", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "isRetake", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "retakeTeacherId", String.class));
      ps.setString(idx++, getValue(row, mappingProvider, "retakeNote", String.class));
      ps.setInt(idx++, getValue(row, mappingProvider, "amount", Integer.class));
      ps.setInt(idx++, getValue(row, mappingProvider, "paymentAmount", Integer.class));
      ps.setInt(idx++, getValue(row, mappingProvider, "discountAmount", Integer.class));
      ps.setString(idx++, getValue(row, mappingProvider, "note", String.class));
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
        getValue(row, mappingProvider, "orderId", String.class),
        getValue(row, mappingProvider, "productId", String.class),
        getValue(row, mappingProvider, "months", Integer.class),
        getValue(row, mappingProvider, "teacherId", String.class),
        getValue(row, mappingProvider, "quantity", Integer.class),
        getValue(row, mappingProvider, "productOption", String.class),
        getValue(row, mappingProvider, "isRetake", String.class),
        getValue(row, mappingProvider, "retakeTeacherId", String.class),
        getValue(row, mappingProvider, "retakeNote", String.class),
        getValue(row, mappingProvider, "amount", Integer.class),
        getValue(row, mappingProvider, "paymentAmount", Integer.class),
        getValue(row, mappingProvider, "discountAmount", Integer.class),
        getValue(row, mappingProvider, "note", String.class),
        getValue(row, mappingProvider, "createdBy", String.class),
        getValue(row, mappingProvider, "modifiedBy", String.class),
        getValue(row, mappingProvider, "createdOn", LocalDateTime.class),
        getValue(row, mappingProvider, "modifiedOn", LocalDateTime.class)
    };
  }
}
