package com.lms.api.migration.mapping;

import java.util.HashMap;
import java.util.Map;

public class ReservationFieldMapping implements FieldMappingProvider {

  private final Map<String, FieldMapping> fieldMappings;

  public ReservationFieldMapping() {
    fieldMappings = new HashMap<>();
    fieldMappings.put("id", new FieldMapping(0, "고유값", null));
    fieldMappings.put("courseId", new FieldMapping(1, "수강자아이디", null));
    fieldMappings.put("userId", new FieldMapping(3, "회원아이디", null));
    fieldMappings.put("date", new FieldMapping(4, "수강일", null));
    fieldMappings.put("startTime", new FieldMapping(5, "시작시간", null));
    fieldMappings.put("endTime", new FieldMapping(6, "끝나는시간", null));
    fieldMappings.put("teacherId", new FieldMapping(7, "강사아이디", null));
    fieldMappings.put("attendanceStatus", new FieldMapping(8, "출결상태", null));
    fieldMappings.put("report", new FieldMapping(11, "출결상태", null));
    fieldMappings.put("createdBy", new FieldMapping(12, "등록자아이디", null));
    fieldMappings.put("modifiedBy", new FieldMapping(12, "등록자아이디", null));
    fieldMappings.put("createdOn", new FieldMapping(13, "등록일", null));
    fieldMappings.put("modifiedOn", new FieldMapping(13, "등록일", null));
  }

  @Override
  public FieldMapping getFieldMapping(String fieldName) {
    return fieldMappings.get(fieldName);
  }
}
