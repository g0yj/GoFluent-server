package com.lms.api.migration.mapping;

import java.util.HashMap;
import java.util.Map;

public class TeacherFieldMapping implements FieldMappingProvider {

  private final Map<String, FieldMapping> fieldMappings;


  public TeacherFieldMapping() {
    this.fieldMappings = new HashMap<>();

    fieldMappings.put("userId", new FieldMapping(0, "회원아이디", null));
    fieldMappings.put("type", new FieldMapping(1, "구분", null));
    fieldMappings.put("partnerTeacherId", new FieldMapping(2, "파트너아이디", null));
    fieldMappings.put("workStartDate", new FieldMapping(3, "근무시작일", null));
    fieldMappings.put("workTime", new FieldMapping(4, "근무시간", null));
    fieldMappings.put("workType", new FieldMapping(5, "근무타입", null));
    fieldMappings.put("smsType", new FieldMapping(6, "SMS타입", null));
    fieldMappings.put("homeAddress", new FieldMapping(7, "본국주소", null));
    fieldMappings.put("homePhone", new FieldMapping(8, "본국연락처", null));
    fieldMappings.put("cellPhone", new FieldMapping(9, "모바일전화번호", null));
    fieldMappings.put("cellPhoneDeviceNumber", new FieldMapping(10, "모바일기기번호", null));
    fieldMappings.put("cellPhoneLeaseStartDate", new FieldMapping(11, "임대시작일", null));
    fieldMappings.put("cellPhoneLeaseEndDate", new FieldMapping(12, "임대만료일", null));
    fieldMappings.put("cellPhoneDevicePrice", new FieldMapping(13, "모바일기기가격", null));
    fieldMappings.put("cellPhoneDeviceStatue", new FieldMapping(14, "모바일기기상태", null));
    fieldMappings.put("university", new FieldMapping(15, "출신대학", null));
    fieldMappings.put("major", new FieldMapping(16, "전공", null));
    fieldMappings.put("previousWork", new FieldMapping(17, "이전근무지", null));
    fieldMappings.put("nationality", new FieldMapping(18, "국적", null));
    fieldMappings.put("visaType", new FieldMapping(19, "비자타입1", null));
    fieldMappings.put("visaEntryType", new FieldMapping(20, "비자타입2", null));
    fieldMappings.put("visaEndDate", new FieldMapping(21, "비자만료일", null));
    fieldMappings.put("arrivalDate", new FieldMapping(22, "도착일", null));
    fieldMappings.put("arrivalNote", new FieldMapping(24, "도착정보", null));
    fieldMappings.put("stayStartDate", new FieldMapping(25, "체류시작일", null));
    fieldMappings.put("stayEndDate", new FieldMapping(26, "체류만료일", null));
    fieldMappings.put("reEntryNote", new FieldMapping(27, "재입국비고", null));
    fieldMappings.put("sponsorStartDate", new FieldMapping(28, "스폰서시작일", null));
    fieldMappings.put("sponsorEndDate", new FieldMapping(29, "스폰서만료일", null));
    fieldMappings.put("trainingDay", new FieldMapping(30, "훈련일수", null));
    fieldMappings.put("visaNote", new FieldMapping(31, "비자비고", null));
    fieldMappings.put("contractStartDate", new FieldMapping(32, "계약시작일", null));
    fieldMappings.put("contractEndDate", new FieldMapping(33, "계약종료일", null));
    fieldMappings.put("contractBasicSalary", new FieldMapping(34, "계약기본급", null));
    fieldMappings.put("contractNote", new FieldMapping(35, "계약비고", null));
    fieldMappings.put("pnpCode", new FieldMapping(36, "PNP코드", null));
    fieldMappings.put("airfarePayment", new FieldMapping(37, "항공료지급", null));
    fieldMappings.put("deposit", new FieldMapping(38, "보증금", null));
    fieldMappings.put("monthly", new FieldMapping(39, "월세", null));
    fieldMappings.put("hireType", new FieldMapping(40, "임차구분", null));
    fieldMappings.put("landlord", new FieldMapping(41, "임대인", null));
    fieldMappings.put("zipcode", new FieldMapping(42, "우편번호", null));
    fieldMappings.put("address", new FieldMapping(43, "주소1", null));
    fieldMappings.put("detailedAddress", new FieldMapping(44, "주소2", null));
    fieldMappings.put("phone", new FieldMapping(45, "연락처", null));
    fieldMappings.put("hireNote", new FieldMapping(46, "임차비고", null));
    fieldMappings.put("hireStartDate", new FieldMapping(47, "임차시작일", null));
    fieldMappings.put("hireEndDate", new FieldMapping(48, "임차만료일", null));
    fieldMappings.put("educationOffice", new FieldMapping(49, "교육청명", null));
    fieldMappings.put("recruitmentDate", new FieldMapping(50, "채용신고일", null));
    fieldMappings.put("educationOfficeNote", new FieldMapping(51, "교육청비고", null));
    fieldMappings.put("educationOfficeManager", new FieldMapping(52, "교육청담당자", null));
    fieldMappings.put("contractExpirationDate", new FieldMapping(53, "계약만료일", null));
    fieldMappings.put("dismissalImmiDate", new FieldMapping(54, "해임일(IMMI)", null));
    fieldMappings.put("departureDate", new FieldMapping(55, "출발일", null));
    fieldMappings.put("dismissalManager", new FieldMapping(56, "해임담당자", null));
    fieldMappings.put("dismissalMeDate", new FieldMapping(57, "해임일(ME)", null));
    fieldMappings.put("releaseNote", new FieldMapping(58, "릴리스비고", null));
    fieldMappings.put("dismissalNote", new FieldMapping(59, "해임비고", null));
    fieldMappings.put("basicSalary", new FieldMapping(60, "기본수당", 0));
    fieldMappings.put("housingCost", new FieldMapping(61, "하우징", 0));
    fieldMappings.put("managementCost", new FieldMapping(62, "관리비", 0));
    fieldMappings.put("nationalPension", new FieldMapping(63, "국민연금", 0));
    fieldMappings.put("healthInsurance", new FieldMapping(64, "건강보험", 0));
    fieldMappings.put("careInsurance", new FieldMapping(65, "장기요양", 0));
    fieldMappings.put("employmentInsurance", new FieldMapping(66, "고용보험", 0));
    fieldMappings.put("sort", new FieldMapping(67, "강사정렬순서", 0));
    fieldMappings.put("createdOn", new FieldMapping(68, "등록일", null));
    fieldMappings.put("modifiedOn", new FieldMapping(68, "등록일", null));
    fieldMappings.put("isActive", new FieldMapping(69, "상태", null));
    fieldMappings.put("language", new FieldMapping(70, "언어", null));
  }

  @Override
  public FieldMapping getFieldMapping(String fieldName) {
    return fieldMappings.get(fieldName);
  }
}
