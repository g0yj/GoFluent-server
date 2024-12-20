package com.lms.api.admin.service.dto.consultation;

import com.lms.api.common.code.CallTime;
import com.lms.api.common.code.Gender;
import com.lms.api.common.code.JoinPath;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateConsultation {

  Long id;
  String type; //상담구분(카카오)
  String status; // 처리상태 대기,완료

  String name;
  Gender gender;

  CallTime callTime; // 통화 가능 시간
  String phone;
  String cellPhone;
  String email;
  String studyPurposes;
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

  String modifiedBy;

  public String getOriginalFile() {
    if (multipartFile == null) {
      return null;
    }
    return multipartFile.getOriginalFilename();
  }

}
