package com.lms.api.admin.controller.dto.teacher;

import com.lms.api.common.code.Gender;
import com.lms.api.common.code.TeacherType;
import com.lms.api.common.code.WorkTime;
import com.lms.api.common.code.WorkType;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MCreateTeacherRequest {

  //@NotEmpty
  String name;
  String firstNameEn;
  String lastNameEn;

  //@NotEmpty
  String password;
  //@NotEmpty
  String loginId;
  //@NotEmpty
  String email;
  //@NotNull
  Gender gender;
  String cellPhone;

  LocalDate workStartDate;
  TeacherType teacherType;
  WorkTime workTime;
  WorkType workType;
  String partnerTeacherId;


  boolean teacherActive;

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

}
