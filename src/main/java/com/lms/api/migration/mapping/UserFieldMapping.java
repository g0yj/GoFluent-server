package com.lms.api.migration.mapping;

import com.lms.api.common.code.AddressType;
import java.util.HashMap;
import java.util.Map;

public class UserFieldMapping implements FieldMappingProvider {

  private final Map<String, FieldMapping> fieldMappings;

  public UserFieldMapping() {
    fieldMappings = new HashMap<>();
    fieldMappings.put("id", new FieldMapping(0, "고유값", null));
    fieldMappings.put("loginId", new FieldMapping(1, "로그인아이디", null));
    fieldMappings.put("name", new FieldMapping(2, "이름", null));
    fieldMappings.put("nameEn", new FieldMapping(3, "영문이름(전체)", null));
    fieldMappings.put("lastNameEn", new FieldMapping(4, "영문이름(성)", null));
    fieldMappings.put("password", new FieldMapping(5, "비밀번호", null));
    fieldMappings.put("type", new FieldMapping(6, "구분", null));
    fieldMappings.put("gender", new FieldMapping(10, "성별", null));
    fieldMappings.put("phone", new FieldMapping(13, "유선전화", null));
    fieldMappings.put("phoneType", new FieldMapping(14, "유선전화타입", null));
    fieldMappings.put("cellPhone", new FieldMapping(15, "휴대전화번호", null));
    fieldMappings.put("isReceiveSms", new FieldMapping(16, "SMS수신여부", null));
    fieldMappings.put("email", new FieldMapping(17, "이메일", null));
    fieldMappings.put("isReceiveEmail", new FieldMapping(18, "이메일수신여부", null));
    fieldMappings.put("zipcode", new FieldMapping(19, "우편번호", null));
    fieldMappings.put("address", new FieldMapping(20, "주소1", null));
    fieldMappings.put("detailedAddress", new FieldMapping(21, "주소2", null));
    fieldMappings.put("addressType", new FieldMapping(22, "주소구분", AddressType.H.name()));
    fieldMappings.put("isOfficeWorker", new FieldMapping(23, "직장인여부", null));
    fieldMappings.put("company", new FieldMapping(24, "직장/학교", null));
    fieldMappings.put("position", new FieldMapping(25, "직책/학과", null));
    fieldMappings.put("joinPath", new FieldMapping(26, "가입경로", "900"));
    fieldMappings.put("language", new FieldMapping(27, "학습희망언어", null));
    fieldMappings.put("etcLanguage", new FieldMapping(28, "기타희망언어", null));
    fieldMappings.put("languageSkill", new FieldMapping(29, "외국어실력", null));
    fieldMappings.put("createdOn", new FieldMapping(31, "등록일", null));
    fieldMappings.put("modifiedOn", new FieldMapping(31, "등록일", null));
    fieldMappings.put("active", new FieldMapping(32, "상태", 0));
    fieldMappings.put("foreignCountry", new FieldMapping(33, "외국거주경험_국가명", null));
    fieldMappings.put("foreignPeriod", new FieldMapping(34, "기간", null));
    fieldMappings.put("foreignPurpose", new FieldMapping(35, "목적", null));
    fieldMappings.put("coursePurpose", new FieldMapping(36, "수강목적", null));
    fieldMappings.put("withdrawalReason", new FieldMapping(37, "탈퇴사유", null));
    fieldMappings.put("note", new FieldMapping(39, "특이사항", null));
    fieldMappings.put("nickname", new FieldMapping(40, "닉네임", null));
    fieldMappings.put("textbook", new FieldMapping(41, "교재", null));
    fieldMappings.put("lessonInfo", new FieldMapping(41, "학습정보", null));
  }

  @Override
  public FieldMapping getFieldMapping(String fieldName) {
    return fieldMappings.get(fieldName);
  }
}
