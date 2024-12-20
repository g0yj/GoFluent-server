package com.lms.api.admin.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lms.api.common.code.CourseStatus;
import com.lms.api.common.controller.dto.PageResponseData;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListUserCoursesResponse extends PageResponseData {

  long id;
  String name;
  float lessonCount;
  float assignmentCount;
  float remainCount;

  @JsonFormat(pattern = "yyyy-MM-dd")
  LocalDate startDate;

  @JsonFormat(pattern = "yyyy-MM-dd")
  LocalDate endDate;

  String teacherId;
  String teacherName;
  String assistantTeacherId;
  String assistantTeacherName;

  @JsonFormat(pattern = "yyyy-MM-dd")
  LocalDate createDate;

  CourseStatus status;
}
