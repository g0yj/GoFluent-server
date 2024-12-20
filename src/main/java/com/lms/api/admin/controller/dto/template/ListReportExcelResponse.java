package com.lms.api.admin.controller.dto.template;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListReportExcelResponse {

  long id;

  //reservation
  LocalDate date;
  LocalTime startTime;
  LocalTime endTime;
  String attendanceStatus;
  String report;
  String todayLesson;
  String nextLesson;

  //user_
  String userName;
  String cellPhone;
  // course
  float lessoncount;
  float assignedLessonCount;
  float remainingLessonCount;


}
