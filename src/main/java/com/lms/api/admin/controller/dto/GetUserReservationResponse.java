package com.lms.api.admin.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lms.api.common.code.AttendanceStatus;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetUserReservationResponse {

  long id;

  @JsonFormat(pattern = "yyyy-MM-dd (EEE)", locale = "en-us")
  LocalDate date;

  @JsonFormat(pattern = "HH:mm")
  LocalTime startTime;

  @JsonFormat(pattern = "HH:mm")
  LocalTime endTime;

  String courseName;
  AttendanceStatus attendanceStatus;
  String report;
}
