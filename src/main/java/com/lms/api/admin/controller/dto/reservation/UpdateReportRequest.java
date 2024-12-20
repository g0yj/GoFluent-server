package com.lms.api.admin.controller.dto.reservation;

import com.lms.api.common.code.AttendanceStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateReportRequest {

  AttendanceStatus attendanceStatus;
  String report;
  String todayLesson;
  String nextLesson;
}
