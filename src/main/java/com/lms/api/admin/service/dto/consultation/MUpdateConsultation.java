package com.lms.api.admin.service.dto.consultation;

import com.lms.api.common.code.ConsultationType;
import com.lms.api.common.code.Gender;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MUpdateConsultation {

  Long id;
  ConsultationType type; //상담구분(카카오)
  String status; // 처리상태 대기,완료

  String name;
  Gender gender;
  String callTime; // 통화 가능 시간
  String phone;
  String cellPhone;
  String email;
  String studyPurposes;
  String foundPath;
  String foundPathNote;
  String job;
  String company;
  LocalDateTime consultationDate;
  LocalDateTime visitDate;
  String details;

  String modifiedBy;


}
