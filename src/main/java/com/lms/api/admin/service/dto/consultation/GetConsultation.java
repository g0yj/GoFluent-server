package com.lms.api.admin.service.dto.consultation;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lms.api.common.code.Gender;
import com.lms.api.common.code.LevelTestType;
import com.lms.api.common.code.StudyPurpose;
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
public class GetConsultation {
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

  String joinPath;

  String foundPathNote;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  LocalDateTime visitDate;
  String details;
  YN isMember;
  String type;

  //공부목적
  List<StudyPurpose> studyPurposes;

  String etcStudyPurpose;
  String callTime;
  String email;

  String status; // 처리상태

  String file;
  String originalFile;
  String fileUrl;


  LevelTestType levelTestType;
  String levelTestAnswer;
  Integer levelTestCorrectCount;

  String createdBy;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  LocalDateTime createdOn;
  String modifiedBy;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  LocalDateTime modifiedOn;

  String modifiedName;
  String createdName;


}
