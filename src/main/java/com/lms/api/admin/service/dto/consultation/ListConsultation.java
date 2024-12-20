package com.lms.api.admin.service.dto.consultation;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lms.api.common.code.CallTime;
import com.lms.api.common.code.Gender;
import com.lms.api.common.code.LevelTestType;
import com.lms.api.common.code.YN;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListConsultation {

  Long id;
  String institutionId;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  LocalDateTime consultationDate;
  String name;
  Gender gender;
  String job;
  String company;
  String phone;
  String cellPhone;
  String foundPath;
  String foundPathNote;
  LocalDateTime visitDate;
  String details;
  YN isMember;
  String type;
  String studyPurpose;
  String etcStudyPurpose;
  CallTime callTime;
  String email;

  List<SmsList> smsList;

  //처리상태
  String status;
  String statusCode;

  LevelTestType levelTestType;
  String levelTestAnswer;
  Integer levelTestCorrectCount;

  String createdBy;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  LocalDateTime createdOn;
  String modifiedBy;
  @JsonFormat(pattern = "yyyy-MM-dd")
  LocalDateTime modifiedOn;

  String creatorName;
}
