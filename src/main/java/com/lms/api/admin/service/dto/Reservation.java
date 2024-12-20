package com.lms.api.admin.service.dto;

import com.lms.api.common.code.AttendanceStatus;
import com.lms.api.common.util.StringUtils;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Reservation {

  long id;
  LocalDate date;
  LocalTime startTime;
  LocalTime endTime;
  AttendanceStatus attendanceStatus;
  String report;
  String todayLesson;
  String nextLesson;
  boolean cancel;
  LocalDateTime cancelDate;
  String cancelerName;
  String cancelReason;
  String modifierName;
  LocalDateTime modifiedOn;

  Course course;
  User user;
  Teacher teacher;
  Product product;
  boolean hasConsecutiveReport;

  public boolean isReported() {
    return StringUtils.hasText(report);
  }

  public void setHasConsecutiveReport(boolean hasConsecutiveReport) {
    this.hasConsecutiveReport = hasConsecutiveReport;
  }
}
