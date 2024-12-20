package com.lms.api.migration.mapping;

import java.util.HashMap;
import java.util.Map;

public class ConsultationFieldMapping implements FieldMappingProvider {

  private final Map<String, FieldMapping> fieldMappings;

  public ConsultationFieldMapping() {
    fieldMappings = new HashMap<>();
    fieldMappings.put("id", new FieldMapping(0, "고유값", null));
    fieldMappings.put("institutionId", new FieldMapping(1, "기관아이디", null));
    fieldMappings.put("consultationDate", new FieldMapping(2, "상담일시", null));
    fieldMappings.put("createdBy", new FieldMapping(3, "상담자아이디", null));
    fieldMappings.put("modifiedBy", new FieldMapping(3, "상담자아이디", null));
    fieldMappings.put("name", new FieldMapping(4, "고객명", null));
    fieldMappings.put("gender", new FieldMapping(5, "성별", null));
    fieldMappings.put("job", new FieldMapping(6, "직업", null));
    fieldMappings.put("company", new FieldMapping(7, "회사명", null));
    fieldMappings.put("phone", new FieldMapping(8, "전화번호", null));
    fieldMappings.put("cellPhone", new FieldMapping(9, "핸드폰번호", null));
    fieldMappings.put("foundPath", new FieldMapping(10, "알게된경로", null));
    fieldMappings.put("foundPathNote", new FieldMapping(11, "알게된경로추가", null));
    fieldMappings.put("visitDate", new FieldMapping(12, "방문예약", null));
    fieldMappings.put("details", new FieldMapping(13, "상담내용", null));
    fieldMappings.put("isMember", new FieldMapping(14, "회원여부", null));
    fieldMappings.put("createdOn", new FieldMapping(15, "등록일", null));
    fieldMappings.put("modifiedOn", new FieldMapping(15, "등록일", null));
    fieldMappings.put("type", new FieldMapping(16, "상담타입", null));
    fieldMappings.put("studyPurpose", new FieldMapping(18, "영어공부목적", null));
    fieldMappings.put("etcStudyPurpose", new FieldMapping(19, "영어공부목적(기타)", null));
    fieldMappings.put("callTime", new FieldMapping(20, "통화가능시간", null));
    fieldMappings.put("email", new FieldMapping(21, "이메일", null));
    fieldMappings.put("status", new FieldMapping(22, "상담진행상태", null));
    fieldMappings.put("levelTestType", new FieldMapping(23, "레벨테스트 타입", null));
  }

  @Override
  public FieldMapping getFieldMapping(String fieldName) {
    return fieldMappings.get(fieldName);
  }
}
