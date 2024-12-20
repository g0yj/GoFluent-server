package com.lms.api.migration.mapping;

import java.util.HashMap;
import java.util.Map;

public class ConsultationHistoryFieldMapping implements FieldMappingProvider {

  private final Map<String, FieldMapping> fieldMappings;

  public ConsultationHistoryFieldMapping() {
    fieldMappings = new HashMap<>();
    fieldMappings.put("id", new FieldMapping(0, "고유값", null));
    fieldMappings.put("consultationId", new FieldMapping(1, "상담관리고유값", null));
    fieldMappings.put("date", new FieldMapping(5, "상담일", null));
    fieldMappings.put("details", new FieldMapping(4, "상담내용", null));
    fieldMappings.put("createdBy", new FieldMapping(2, "작성자아이디", null));
    fieldMappings.put("createdOn", new FieldMapping(5, "등록일", null));
    fieldMappings.put("modifiedOn", new FieldMapping(5, "등록일", null));
  }

  @Override
  public FieldMapping getFieldMapping(String fieldName) {
    return fieldMappings.get(fieldName);
  }
}
