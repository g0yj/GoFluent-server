package com.lms.api.admin.controller.dto.teacher;

import com.lms.api.common.code.TeacherType;
import com.lms.api.common.code.WorkTime;
import com.lms.api.common.controller.dto.PageResponseData;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListTeacherResponse extends PageResponseData {

  String userId;
  TeacherType type;
  String teacherName;
  String teacherEnName;
  String email;
  String cellPhone;
  WorkTime workTime;
  int sort;
  boolean active;

}
