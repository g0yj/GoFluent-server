package com.lms.api.admin.controller.dto.template;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lms.api.common.code.Gender;
import com.lms.api.common.code.JoinPath;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListUsersExcelResponse {
  String id;
  String loginId;
  String email;
  String name;
  String nameEn;
  Gender gender;
  String phone;
  String cellPhone;
  Boolean isReceiveSms;
  Boolean isReceiveEmail;
  String zipcode;
  String address;
  String detailedAddress;
  Boolean isOfficeWorker;
  String company;
  String position;
  JoinPath joinPath;
  String language;
  String etcLanguage;
  String languageSkill;
  boolean active;
  @JsonFormat(pattern = "yyyy-MM-dd")
  LocalDateTime createdOn;
}
