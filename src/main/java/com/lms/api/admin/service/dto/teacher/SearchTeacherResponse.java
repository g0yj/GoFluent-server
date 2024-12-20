package com.lms.api.admin.service.dto.teacher;

import com.lms.api.admin.service.dto.User;
import com.lms.api.common.code.TeacherType;
import com.lms.api.common.code.WorkTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchTeacherResponse {

  String userId;
  String email;
  String cellPhone;
  String teacherName;
  String teacherEnName;
  TeacherType type;
  boolean active;
  int sort;
  WorkTime workTime;
  User user;
}
