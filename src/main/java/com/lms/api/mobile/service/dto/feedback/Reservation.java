package com.lms.api.mobile.service.dto.feedback;

import com.lms.api.common.code.AttendanceStatus;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Reservation {
  Long id;

  LocalDate date;
  LocalTime startTime;
  LocalTime endTime;

  AttendanceStatus attendanceStatus;

  String todayLesson;
  String report;
  String nextLesson;

  String ldfYN;

}
