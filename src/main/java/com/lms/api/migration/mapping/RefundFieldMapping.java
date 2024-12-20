package com.lms.api.migration.mapping;

import java.util.HashMap;
import java.util.Map;

public class RefundFieldMapping implements FieldMappingProvider {

  private final Map<String, FieldMapping> fieldMappings;

  public RefundFieldMapping() {
    fieldMappings = new HashMap<>();
    fieldMappings.put("id", new FieldMapping(0, "고유값", null));
    fieldMappings.put("userId", new FieldMapping(2, "회원아이디", null));
    fieldMappings.put("orderProductId", new FieldMapping(3, "주문아이디", null));
    fieldMappings.put("refundDate", new FieldMapping(5, "환불일", null));
    fieldMappings.put("refundAmount", new FieldMapping(6, "금액", 0));
    fieldMappings.put("cashAmount", new FieldMapping(7, "현금액", 0));
    fieldMappings.put("depositAmount", new FieldMapping(8, "강사아이디", 0));
    fieldMappings.put("bank", new FieldMapping(9, "예금액", null));
    fieldMappings.put("accountNumber", new FieldMapping(10, "계좌번호", null));
    fieldMappings.put("refundReason", new FieldMapping(11, "환불사유", null));
    fieldMappings.put("createdBy", new FieldMapping(12, "은행명", null));
    fieldMappings.put("modifiedBy", new FieldMapping(12, "등록자아이디", null));
    fieldMappings.put("createdOn", new FieldMapping(13, "등록일", null));
    fieldMappings.put("modifiedOn", new FieldMapping(13, "등록일", null));
  }

  @Override
  public FieldMapping getFieldMapping(String fieldName) {
    return fieldMappings.get(fieldName);
  }
}
