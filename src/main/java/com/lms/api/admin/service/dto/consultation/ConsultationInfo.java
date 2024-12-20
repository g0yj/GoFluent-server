package com.lms.api.admin.service.dto.consultation;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lms.api.common.code.CallTime;
import com.lms.api.common.code.Gender;
import com.lms.api.common.code.JoinPath;
import com.lms.api.common.code.StudyPurpose;
import com.lms.api.common.code.YN;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConsultationInfo {

  Long id;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  LocalDateTime consultationDate;
  String name;
  Gender gender;
  String job;
  String company;
  String phone;
  String cellPhone;


  String foundPathNote;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  LocalDateTime visitDate;
  String details;
  YN isMember;
  String type;

  //공부목적
  List<StudyPurpose> studyPurposes;

  String etcStudyPurpose;

  String email;

  String status; // 처리상태

  String file;
  String originalFile;
  String fileUrl;


  String createdBy;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  LocalDateTime createdOn;
  String modifiedBy;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  LocalDateTime modifiedOn;


  JoinPath foundPath;
  CallTime callTime;

  String modifiedName;
  String createdName;



}