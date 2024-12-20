package com.lms.api.migration.mapping;

import java.util.HashMap;
import java.util.Map;

public class ConsultationLogFieldMapping implements FieldMappingProvider {

  private final Map<String, FieldMapping> fieldMappings;

  public ConsultationLogFieldMapping() {
    fieldMappings = new HashMap<>();
    fieldMappings.put("date", new FieldMapping(1, "상담일", null));
    fieldMappings.put("name", new FieldMapping(3, "이름", null));
    fieldMappings.put("gender", new FieldMapping(4, "성별", null));
    fieldMappings.put("cellPhone", new FieldMapping(5, "연락처", null));
  }

  @Override
  public FieldMapping getFieldMapping(String fieldName) {
    return fieldMappings.get(fieldName);
  }
}
