package com.lms.api.admin.controller.dto.consultation;

import com.lms.api.common.code.CallTime;
import com.lms.api.common.code.Gender;
import com.lms.api.common.code.JoinPath;
import com.lms.api.common.code.StudyPurpose;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class CreateConsultationRequest {

  @NotNull
  String type;
  @NotNull
  String status;
  @NotNull
  String name;

  Gender gender;
  CallTime callTime;
  String phone;

  @NotNull
  String cellPhone;
  String email;

  List<StudyPurpose> studyPurposes; //

//  @NotNull
  JoinPath foundPath;

  String foundPathNote;
  String job;
  String company;


  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  LocalDateTime consultationDate ;


  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  LocalDateTime visitDate;

  String details;
  MultipartFile file;
  Boolean isDeleteFile;



}
