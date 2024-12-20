package com.lms.api.mobile.service.dto;

import com.lms.api.common.code.AttendanceStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Reservation {
  long id;
  LocalDate date;
  LocalTime startTime;
  LocalTime endTime;
  AttendanceStatus attendanceStatus;
  boolean cancel;
  LocalDateTime cancelDate;
  String cancelReason;
  Teacher teacher;

  boolean cancelable;

  Long schedule;
}
