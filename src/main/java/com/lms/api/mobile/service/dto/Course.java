package com.lms.api.mobile.service.dto;

import java.time.LocalDate;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Course {
  long id;
  LocalDate startDate;
  LocalDate endDate;
  float lessonCount;
  float assignmentCount;
  float remainCount;
  float attendanceCount;
  float reservationCount;
  OrderProduct orderProduct;
  List<Reservation> reservations;

  //추가
  float totalAttendanceRate;
  float totalAttendanceCount;
  float totalReservationCount;
  float totalNonAttendanceCount;

  public String getProductName() {
    return orderProduct == null ? null : orderProduct.getProductName();
  }
}
