package com.lms.api.migration.mapping;

import java.util.HashMap;
import java.util.Map;

public class CourseHistoryFieldMapping implements FieldMappingProvider {

  private final Map<String, FieldMapping> fieldMappings;

  public CourseHistoryFieldMapping() {
    fieldMappings = new HashMap<>();
    fieldMappings.put("id", new FieldMapping(0, "고유값", null));
    fieldMappings.put("module", new FieldMapping(1, "모듈", null));
    fieldMappings.put("moduleId", new FieldMapping(2, "모듈아이디", null));
    fieldMappings.put("createdBy", new FieldMapping(3, "등록자아이디", null));
    fieldMappings.put("modifiedBy", new FieldMapping(4, "등록자아이디", null));
    fieldMappings.put("type", new FieldMapping(5, "구분", null));
    fieldMappings.put("title", new FieldMapping(6, "제목", null));
    fieldMappings.put("content", new FieldMapping(7, "내용", null));
    fieldMappings.put("courseId", new FieldMapping(8, "수강아이디", null));
    fieldMappings.put("createdOn", new FieldMapping(9, "등록일", null));
    fieldMappings.put("modifiedOn", new FieldMapping(9, "등록일", null));
  }

  @Override
  public FieldMapping getFieldMapping(String fieldName) {
    return fieldMappings.get(fieldName);
  }
}
