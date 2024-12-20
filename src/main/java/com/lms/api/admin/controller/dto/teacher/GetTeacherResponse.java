package com.lms.api.admin.controller.dto.teacher;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lms.api.common.code.Gender;
import com.lms.api.common.code.Language;
import com.lms.api.common.code.TeacherType;
import com.lms.api.common.code.WorkTime;
import com.lms.api.common.code.WorkType;
import java.time.LocalDate;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetTeacherResponse {

  //user_ 식별키
  String id;
  // 로그인 아이디
  String loginId;
  String name;
  String nameEn;
  String lastNameEn;
  @JsonIgnore
  String password; // 비밀번호는 출력하지 않음 (10월 1일 조원빈 요청)
  String email;
  Gender gender;
  String cellPhone;


  TeacherType teacherType;

  @JsonFormat(pattern = "yyyy-MM-dd")
  LocalDate workStartDate;
  Language language;
  WorkTime workTime;
  WorkType workType;
  String partnerTeacherId;
  String memo;

  // 필수값
  int sort;
  int basicSalary;
  int housingCost;
  int managementCost;
  int nationalPension;
  int healthInsurance;
  int careInsurance;
  int employmentInsurance;
  boolean active; // 활동여부

  List<File> files;

  @Getter
  @Builder
  @FieldDefaults(level = AccessLevel.PRIVATE)
  public static class File {
    long id;
    String file;
    String originalFile;
    String url;
  }

}
