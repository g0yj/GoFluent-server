package com.lms.api.migration.mapping;

import java.util.HashMap;
import java.util.Map;

public class OrderFieldMapping implements FieldMappingProvider {

  private final Map<String, FieldMapping> fieldMappings;

  public OrderFieldMapping() {
    fieldMappings = new HashMap<>();
    fieldMappings.put("id", new FieldMapping(0, "고유값", null));
    fieldMappings.put("type", new FieldMapping(1, "주문타입", null));
    fieldMappings.put("userId", new FieldMapping(2, "주문자아이디", null));
    fieldMappings.put("supplyAmount", new FieldMapping(4, "공급가액", null));
    fieldMappings.put("discountAmount", new FieldMapping(5, "할인액", null));
    fieldMappings.put("billingAmount", new FieldMapping(7, "실청구액", null));
    fieldMappings.put("cashAmount", new FieldMapping(8, "현금", null));
    fieldMappings.put("depositAmount", new FieldMapping(9, "예금", null));
    fieldMappings.put("cardCount", new FieldMapping(10, "사용카드수", null));
    fieldMappings.put("cardAmount", new FieldMapping(11, "카드금액합계", null));
    fieldMappings.put("paymentAmount", new FieldMapping(12, "결제액", null));
    fieldMappings.put("receivableAmount", new FieldMapping(13, "미수금", null));
    fieldMappings.put("refundAmount", new FieldMapping(14, "환불액", null));
    fieldMappings.put("createdBy", new FieldMapping(15, "등록자아이디", null));
    fieldMappings.put("modifiedBy", new FieldMapping(15, "등록자아이디", null));
    fieldMappings.put("createdOn", new FieldMapping(16, "등록일", null));
    fieldMappings.put("modifiedOn", new FieldMapping(16, "등록일", null));
    fieldMappings.put("recallDate", new FieldMapping(17, "회수예정일", null));
    fieldMappings.put("receivableReason", new FieldMapping(18, "미수금사유", null));
  }

  @Override
  public FieldMapping getFieldMapping(String fieldName) {
    return fieldMappings.get(fieldName);
  }
}
