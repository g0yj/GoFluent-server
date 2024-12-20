package com.lms.api.migration.mapping;

import java.util.HashMap;
import java.util.Map;

public class MemberConsultationFieldMapping implements FieldMappingProvider {

  private final Map<String, FieldMapping> fieldMappings;

  public MemberConsultationFieldMapping() {
    fieldMappings = new HashMap<>();
    fieldMappings.put("id", new FieldMapping(0, "고유값", null));
    fieldMappings.put("consultationDate", new FieldMapping(1, "상담일시", null));
    fieldMappings.put("userId", new FieldMapping(2, "회원아이디", null));
    fieldMappings.put("type", new FieldMapping(3, "type", null));
    fieldMappings.put("createdBy", new FieldMapping(4, "상담자아이디", null));
    fieldMappings.put("modifiedBy", new FieldMapping(4, "상담자아이디", null));
    fieldMappings.put("details", new FieldMapping(5, "상담내용", null));
    fieldMappings.put("createdOn", new FieldMapping(6, "등록일", null));
    fieldMappings.put("modifiedOn", new FieldMapping(6, "등록일", null));
  }

  @Override
  public FieldMapping getFieldMapping(String fieldName) {
    return fieldMappings.get(fieldName);
  }
}
