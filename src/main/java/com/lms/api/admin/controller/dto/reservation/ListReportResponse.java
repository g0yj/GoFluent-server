package com.lms.api.admin.controller.dto.reservation;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lms.api.common.code.AttendanceStatus;
import com.lms.api.common.controller.dto.PageResponseData;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListReportResponse extends PageResponseData {

  long id;

  @JsonFormat(pattern = "yyyy-MM-dd (EEE)", locale = "en-us")
  LocalDate date;
  @JsonFormat(pattern = "HH:mm")
  LocalTime startTime;
  @JsonFormat(pattern = "HH:mm")
  LocalTime endTime;
  String teacherName;
  String userName;
  String courseName;
  float lessonCount;
  float remainCount;
  float assignmentCount;
  AttendanceStatus attendanceStatus;
  String report;
  String todayLesson;
  String nextLesson;

}
