package com.lms.api.admin.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lms.api.admin.dto.LanguageSkill;
import com.lms.api.common.code.AddressType;
import com.lms.api.common.code.CoursePurpose;
import com.lms.api.common.code.Gender;
import com.lms.api.common.code.JoinPath;
import com.lms.api.common.code.LanguageCode;
import com.lms.api.common.code.PhoneType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateUserRequest {

  @NotEmpty
  String name;

  String firstNameEn;
  String lastNameEn;
  String nickname;
  String textbook;

  @NotEmpty
  String loginId;

  @NotEmpty
  String email;

  Boolean isReceiveEmail;
  String password;

  @NotNull
  Gender gender;

  String phone;
  PhoneType phoneType;

  @NotEmpty
  String cellPhone;

  Boolean isReceiveSms;

  Boolean isOfficeWorker;
  String company;
  String position;
  String note;
  String lessonInfo;

  @JsonProperty("isActive")
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

  public String getNameEn() {
    if (firstNameEn == null && lastNameEn == null) {
      return null;
    }

    if (firstNameEn == null) {
      return lastNameEn;
    }

    if (lastNameEn == null) {
      return firstNameEn;
    }

    return firstNameEn + " " + lastNameEn;
  }

  public String getLanguage() {
    if (languages == null) {
      return null;
    }

    return languages.stream().map(LanguageCode::name).collect(Collectors.joining("|"));
  }

  public String getLanguageSkill() {
    if (languageSkills == null) {
      return null;
    }

    return languageSkills.stream()
        .map(languageSkill -> languageSkill.getLanguageTest().name() + ":"
            + languageSkill.getScore())
        .collect(Collectors.joining(","));
  }

  public String getCoursePurpose() {
    if (coursePurposes == null) {
      return null;
    }

    return coursePurposes.stream()
            .filter(Objects::nonNull)
            .map(CoursePurpose::name)
            .collect(Collectors.joining(","));
  }
}
