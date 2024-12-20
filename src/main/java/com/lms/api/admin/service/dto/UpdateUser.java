package com.lms.api.admin.service.dto;

import com.lms.api.common.code.AddressType;
import com.lms.api.common.code.Gender;
import com.lms.api.common.code.JoinPath;
import com.lms.api.common.code.PhoneType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateUser {

  String id;
  String name;
  String nameEn;
  String lastNameEn;
  String nickname;
  String textbook;
  String loginId;
  String email;

  @Default
  Boolean isReceiveEmail = false;

  String password;
  Gender gender;
  String phone;
  PhoneType phoneType;
  String cellPhone;

  @Default
  Boolean isReceiveSms = false;

  Boolean isOfficeWorker;
  String company;
  String position;
  String note;
  String lessonInfo;

  boolean active;
  String zipcode;
  String address;
  String detailedAddress;
  AddressType addressType;
  JoinPath joinPath;
  String language;
  String etcLanguage;
  String languageSkill;
  String foreignCountry;
  String foreignPeriod;
  String foreignPurpose;
  String coursePurpose;
  String modifiedBy;
}
