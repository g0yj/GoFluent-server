package com.lms.api.admin.service.dto.teacher;

import com.lms.api.admin.service.dto.TeacherFile;
import com.lms.api.admin.service.dto.User;
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
public class GetTeacher {

  String userId; // teacher 식별키

  User teacher;

  String partnerTeacherId;
  String createdOn;
  TeacherType type; //HT(한국인), LT(외국인)
  LocalDate workStartDate;//근무시작일
  Language language; //언어 : ENGLISH, CHINESE, JAPANESE
  WorkTime workTime; // AM_16, PM_16, SP_16, AM_8, PM_8, SP_10, SP_4
  WorkType workType; // 근무타입: A, C
  boolean active;
  String memo;
  int sort;
  int basicSalary;
  int housingCost;
  int managementCost;
  int nationalPension;
  int healthInsurance;
  int careInsurance;
  int employmentInsurance;

  List<TeacherFile> teacherFiles;

}
