package com.lms.api.migration.mapping;

import java.util.HashMap;
import java.util.Map;

public class LdfFieldMapping implements FieldMappingProvider {

  private final Map<String, FieldMapping> fieldMappings;

  public LdfFieldMapping() {
    fieldMappings = new HashMap<>();
    fieldMappings.put("id", new FieldMapping(0, "고유값", null));
    fieldMappings.put("reservationId", new FieldMapping(1, "강의예약아이디", null));
    fieldMappings.put("userId", new FieldMapping(2, "사용자아이디", null));
    fieldMappings.put("lesson", new FieldMapping(3, "레슨", null));
    fieldMappings.put("contentSp", new FieldMapping(4, "내용_SP", null));
    fieldMappings.put("contentV", new FieldMapping(5, "내용_V", null));
    fieldMappings.put("contentSg", new FieldMapping(6, "내용_SG", null));
    fieldMappings.put("contentC", new FieldMapping(7, "내용_C", null));
    fieldMappings.put("createdOn", new FieldMapping(8, "등록일시", null));
    fieldMappings.put("modifiedOn", new FieldMapping(8, "등록일시", null));
    fieldMappings.put("createdBy", new FieldMapping(9, "등록자아이디", null));
    fieldMappings.put("modifiedBy", new FieldMapping(9, "등록자아이디", null));
    fieldMappings.put("grade", new FieldMapping(10, "평점", null));
    fieldMappings.put("evaluation", new FieldMapping(11, "평가", null));
    fieldMappings.put("emailId", new FieldMapping(12, "전송 이메일 ID", null));
  }

  @Override
  public FieldMapping getFieldMapping(String fieldName) {
    return fieldMappings.get(fieldName);
  }
}
