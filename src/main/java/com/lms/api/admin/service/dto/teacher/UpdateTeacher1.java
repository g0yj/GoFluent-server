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

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateTeacher1 {

  String userId;

  String name; // 필수
  String firstNameEn;
  String lastNameEn;
  String loginId;
  String password;
  String email;
  Gender gender;
  String cellPhone;

  LocalDate workStartDate;//근무시작일
  TeacherType teacherType; //HT(한국인), LT(외국인)
  WorkTime workTime; // AM_16, PM_16, SP_16, AM_8, PM_8, SP_10, SP_4
  WorkType workType; // 근무타입: A, C
  String partnerTeacherId;
  boolean active; //활동: true

  String modifiedBy;
}
