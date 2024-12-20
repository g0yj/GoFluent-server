package com.lms.api.admin.service.dto;

import com.lms.api.admin.dto.LanguageSkill;
import com.lms.api.common.code.AddressType;
import com.lms.api.common.code.CoursePurpose;
import com.lms.api.common.code.Gender;
import com.lms.api.common.code.JoinPath;
import com.lms.api.common.code.LanguageCode;
import com.lms.api.common.code.LanguageTest;
import com.lms.api.common.code.PhoneType;
import com.lms.api.common.code.UserType;
import com.lms.api.common.util.StringUtils;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {

  String id;
  String loginId;
  String name;
  String nameEn;
  String lastNameEn;
  String password;
  UserType type;
  Gender gender;
  String phone;
  PhoneType phoneType;
  String cellPhone;
  Boolean isReceiveSms;
  String email;
  Boolean isReceiveEmail;
  String zipcode;
  String address;
  String detailedAddress;
  AddressType addressType;
  Boolean isOfficeWorker;
  String company;
  String position;
  JoinPath joinPath;
  String language;
  String etcLanguage;
  String languageSkill;
  boolean active;
  String foreignCountry;
  String foreignPeriod;
  String foreignPurpose;
  String coursePurpose;
  String withdrawalReason;
  String note;
  String lessonInfo;
  String nickname;
  String textbook;
  String createdBy;
  LocalDateTime createdOn;
  String modifiedBy;
  LocalDateTime modifiedOn;


  //강사 페이지에서 사용하기 위해 추가한 필드
  List<Reservation> reservations;

  public String getFirstNameEn() {
    if (nameEn == null) {
      return null;
    }

    if (lastNameEn == null) {
      return nameEn;
    }

    int i = nameEn.lastIndexOf(" " + lastNameEn);

    if (i == -1) {
      i = nameEn.lastIndexOf(lastNameEn);
    }

    if (i == -1) {
      return nameEn;
    }

    return nameEn.substring(0, i);
  }

  public List<LanguageCode> getLanguages() {
    if (language == null) {
      return List.of();
    }

    return Arrays.stream(language.split("\\|"))
        .filter(StringUtils::hasText)
        .map(String::trim)
        .map(LanguageCode::valueOf)
        .collect(Collectors.toList());
  }

  public List<LanguageSkill> getLanguageSkills() {
    if (languageSkill == null) {
      return List.of();
    }

    return Arrays.stream(languageSkill.split(","))
            .filter(StringUtils::hasText)
            .map(String::trim)
            .map(languageSkill -> {
              String[] split = languageSkill.split(":");
              String testCode = split[0];
              if ("TOEIC_S".equals(testCode)) {
                testCode = "TOEIC-S";
              } else if ("OPIC".equals(testCode)) {
                testCode = "OPIc";
              } else if ("ETC".equals(testCode)) {
                testCode = "기타";
              }
              return LanguageSkill.builder()
                      .languageTest(LanguageTest.of(testCode))
                      .score(split.length > 1 ? split[1] : null)
                      .build();
            })
            .collect(Collectors.toList());
  }


  public List<CoursePurpose> getCoursePurposes() {
    if (coursePurpose == null) {
      return List.of();
    }

    return Arrays.stream(coursePurpose.split(","))
        .filter(StringUtils::hasText)
        .map(String::trim)
        .map(CoursePurpose::of)
        .collect(Collectors.toList());
  }
}

/*

  public List<LanguageSkill> getLanguageSkills() {
    if (languageSkill == null) {
      return List.of();
    }

    return Arrays.stream(languageSkill.split(","))
        .filter(StringUtils::hasText)
        .map(String::trim)
        .map(languageSkill -> {
          String[] split = languageSkill.split(":");

          return LanguageSkill.builder()
              .languageTest(LanguageTest.of(split[0]))
              .score(split.length > 1 ? split[1] : null)
              .build();
        })
        .collect(Collectors.toList());
  }
*/
