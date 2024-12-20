package com.lms.api.migration.mapping;

import java.util.HashMap;
import java.util.Map;

public class CourseFieldMapping implements FieldMappingProvider {

  private final Map<String, FieldMapping> fieldMappings;

  public CourseFieldMapping() {
    fieldMappings = new HashMap<>();
    fieldMappings.put("id", new FieldMapping(0, "고유값", null));
    fieldMappings.put("userId", new FieldMapping(1, "회원아이디", null));
    fieldMappings.put("lessonCount", new FieldMapping(8, "레슨 횟수", null));
    fieldMappings.put("assignmentCount", new FieldMapping(9, "배정 횟수 (예약포함)", null));
    fieldMappings.put("attendanceCount", new FieldMapping(10, "수업 횟수 (출결횟수)", null));
    fieldMappings.put("startDate", new FieldMapping(11, "수강시작일", null));
    fieldMappings.put("endDate", new FieldMapping(12, "수강종료일", null));
    fieldMappings.put("teacherId", new FieldMapping(13, "담당강사", null));
    fieldMappings.put("assistantTeacherId", new FieldMapping(14, "부담임강사", null));
    fieldMappings.put("countChangeReason", new FieldMapping(15, "레슨횟수 변경시사유", null));
    fieldMappings.put("isCompletion", new FieldMapping(17, "수료여부", "N"));
    fieldMappings.put("createdBy", new FieldMapping(18, "등록자아이디", null));
    fieldMappings.put("createdOn", new FieldMapping(19, "등록일", null));
    fieldMappings.put("modifiedBy", new FieldMapping(20, "수정자아이디", null));
    fieldMappings.put("modifiedOn", new FieldMapping(19, "수정일", null));
    fieldMappings.put("orderProductId", new FieldMapping(22, "아이템아이디", null));
    fieldMappings.put("isReservation", new FieldMapping(23, "예약여부", "N"));
    fieldMappings.put("dateChangeReason", new FieldMapping(24, "레슨횟수 변경사유", null));
  }

  @Override
  public FieldMapping getFieldMapping(String fieldName) {
    return fieldMappings.get(fieldName);
  }
}
