package com.lms.api.admin.service.dto;

import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReservationAttendance {

  String teacherId;
  LocalDate date;
  long reservationCount;
  long attendanceCount;
}
