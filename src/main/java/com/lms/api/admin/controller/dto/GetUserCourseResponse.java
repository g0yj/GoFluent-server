package com.lms.api.admin.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lms.api.common.code.CourseStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetUserCourseResponse {

  long id;
  String language;
  String name;
  float lessonCount;
  float assignmentCount;
  float remainCount;
  String countChangeReason;

  @JsonFormat(pattern = "yyyy-MM-dd")
  LocalDate startDate;

  @JsonFormat(pattern = "yyyy-MM-dd")
  LocalDate endDate;

  String dateChangeReason;
  String teacherId;
  String assistantTeacherId;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  LocalDateTime createdOn;

  CourseStatus status;
}
