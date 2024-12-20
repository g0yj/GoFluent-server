package com.lms.api.admin.service.dto.teacher;

import com.lms.api.common.code.Gender;
import com.lms.api.common.code.TeacherType;
import com.lms.api.common.code.WorkTime;
import com.lms.api.common.code.WorkType;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MUpdateTeacher {

  // 회원 필수 값
  @Builder.Default
  Boolean isReceiveEmail = false;
  @Builder.Default
  Boolean isReceiveSms = false;
  @Builder.Default
  boolean active = false;


  String loginId;
  // 회원 관련
  String name;
  String firstNameEn;
  String lastNameEn;
  String nameEn;
  String password;
  String email;
  Gender gender;

  // 강사 필수 값
  @Builder.Default
  boolean teacherActive = false;

  TeacherType teacherType; // 한국인강사, 외국인강사

  @Builder.Default
  int basicSalary = 0;
  @Builder.Default
  int housingCost = 0;
  @Builder.Default
  int managementCost = 0;
  @Builder.Default
  int nationalPension = 0;
  @Builder.Default
  int healthInsurance = 0;
  @Builder.Default
  int careInsurance = 0;
  @Builder.Default
  int employmentInsurance = 0;
  @Builder.Default
  int sort = 0;

  // 강사 관련
  LocalDate workStartDate;
  WorkTime workTime;
  WorkType workType;
  String partnerTeacherId;

  String userId;

  String modifiedBy;


}
