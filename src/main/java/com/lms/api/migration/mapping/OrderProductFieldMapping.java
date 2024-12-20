package com.lms.api.migration.mapping;

import java.util.HashMap;
import java.util.Map;

public class OrderProductFieldMapping implements FieldMappingProvider {

  private final Map<String, FieldMapping> fieldMappings;

  public OrderProductFieldMapping() {
    fieldMappings = new HashMap<>();
    fieldMappings.put("id", new FieldMapping(0, "고유값", null));
    fieldMappings.put("orderId", new FieldMapping(3, "주문아이디", null));
    fieldMappings.put("productId", new FieldMapping(7, "상품아이디", null));
    fieldMappings.put("months", new FieldMapping(9, "개월수", 0));
    fieldMappings.put("teacherId", new FieldMapping(11, "강사아이디", null));
    fieldMappings.put("quantity", new FieldMapping(12, "횟수", 0));
    fieldMappings.put("productOption", new FieldMapping(14, "옵션", null));
    fieldMappings.put("isRetake", new FieldMapping(15, "재등록여부", null));
    fieldMappings.put("retakeTeacherId", new FieldMapping(16, "재등록 강사", null));
    fieldMappings.put("retakeNote", new FieldMapping(17, "재등록 노트", null));
    fieldMappings.put("amount", new FieldMapping(18, "단위액", 0));
    fieldMappings.put("paymentAmount", new FieldMapping(21, "결제액", 0));
    fieldMappings.put("discountAmount", new FieldMapping(22, "할인액", 0));
    fieldMappings.put("note", new FieldMapping(31, "메모", null));
    fieldMappings.put("createdBy", new FieldMapping(22, "등록자아이디", null));
    fieldMappings.put("modifiedBy", new FieldMapping(22, "등록자아이디", null));
    fieldMappings.put("createdOn", new FieldMapping(33, "등록일", null));
    fieldMappings.put("modifiedOn", new FieldMapping(33, "등록일", null));
  }

  @Override
  public FieldMapping getFieldMapping(String fieldName) {
    return fieldMappings.get(fieldName);
  }
}
