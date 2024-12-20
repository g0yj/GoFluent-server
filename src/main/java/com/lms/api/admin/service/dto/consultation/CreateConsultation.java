package com.lms.api.admin.service.dto.consultation;

import com.lms.api.common.code.CallTime;
import com.lms.api.common.code.Gender;
import com.lms.api.common.code.JoinPath;
import com.lms.api.common.code.StudyPurpose;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateConsultation {

  Long id;
  String type; //상담구분(카카오)
  String status; // 처리상태 대기,완료

  String name;
  @Builder.Default
  Gender gender = Gender.M;

  CallTime callTime; // 통화 가능 시간
  String phone;
  String cellPhone;
  String email;

  List<StudyPurpose> studyPurposes;

  JoinPath foundPath;

  String foundPathNote;

  String job;
  String company;
  LocalDateTime consultationDate;
  LocalDateTime visitDate;
  String details;

  MultipartFile multipartFile;
  String file;
  Boolean isDeleteFile;

  String createdBy;

  public String getOriginalFile() {
    if (multipartFile == null) {
      return null;
    }
    return multipartFile.getOriginalFilename();
  }

  public String getStudyPurposes() {
    if (studyPurposes == null) {
      return null;
    }
    return studyPurposes.stream().map(StudyPurpose::getCode).collect(Collectors.joining(","));
  }


}
