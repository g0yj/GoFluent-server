package com.lms.api.migration.mapping;

import java.util.HashMap;
import java.util.Map;

public class ScheduleFieldMapping implements FieldMappingProvider {

  private final Map<String, FieldMapping> fieldMappings;

  public ScheduleFieldMapping() {
    this.fieldMappings = new HashMap<>();
    fieldMappings.put("id", new FieldMapping(0, "고유값", null));
    fieldMappings.put("teacherId", new FieldMapping(1, "강사아이디", null));
    fieldMappings.put("date", new FieldMapping(2, "날짜", null));
    fieldMappings.put("startTime", new FieldMapping(3, "시작시간", null));
    fieldMappings.put("createdBy", new FieldMapping(4, "등록자", null));
    fieldMappings.put("modifiedBy", new FieldMapping(4, "등록자", null));
    fieldMappings.put("createdOn", new FieldMapping(5, "등록일", null));
    fieldMappings.put("modifiedOn", new FieldMapping(5, "등록일", null));
    fieldMappings.put("workTime", new FieldMapping(6, "근무시간", null));
  }

  @Override
  public FieldMapping getFieldMapping(String fieldName) {
    return fieldMappings.get(fieldName);
  }
}
