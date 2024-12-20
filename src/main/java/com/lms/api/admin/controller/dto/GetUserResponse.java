package com.lms.api.admin.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lms.api.admin.dto.LanguageSkill;
import com.lms.api.common.code.AddressType;
import com.lms.api.common.code.CoursePurpose;
import com.lms.api.common.code.Gender;
import com.lms.api.common.code.JoinPath;
import com.lms.api.common.code.LanguageCode;
import com.lms.api.common.code.PhoneType;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetUserResponse {

  String id;
  String name;
  String firstNameEn;
  String lastNameEn;
  String nickname;
  String textbook;
  String loginId;
  String email;
  Boolean isReceiveEmail;
  Gender gender;
  String phone;
  PhoneType phoneType;
  String cellPhone;
  Boolean isReceiveSms;
  Boolean isOfficeWorker;
  String company;
  String position;
  String note;
  String lessonInfo;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  LocalDateTime createDateTime;

  boolean active;
  String zipcode;
  String address;
  String detailedAddress;
  AddressType addressType;
  JoinPath joinPath;
  List<LanguageCode> languages;
  String etcLanguage;
  List<LanguageSkill> languageSkills;
  String foreignCountry;
  String foreignPeriod;
  String foreignPurpose;
  List<CoursePurpose> coursePurposes;
}
