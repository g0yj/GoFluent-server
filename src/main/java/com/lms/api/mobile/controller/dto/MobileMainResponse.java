package com.lms.api.mobile.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MobileMainResponse {
  List<Course> courses;

  @Getter
  @Builder
  @FieldDefaults(level = AccessLevel.PRIVATE)
  public static class Course {
    long id;
    String productName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate endDate;

    float lessonCount;
    float remainCount;
    float attendanceCount;
    float reservationCount;

    //추가
    @JsonProperty("totalAttendanceRate")
    public String getTotalAttendanceRate() {
      return String.format("%.2f", totalAttendanceRate);
    }
    float totalAttendanceRate;
    float totalAttendanceCount;
    float totalReservationCount;
    float totalNonAttendanceCount;

    List<Reservation> reservations;
  }

  @Getter
  @Builder
  @FieldDefaults(level = AccessLevel.PRIVATE)
  public static class Reservation {
    long id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate date;

    @JsonFormat(pattern = "HH:mm")
    LocalTime startTime;

    @JsonFormat(pattern = "HH:mm")
    LocalTime endTime;

    String teacherId;
    String teacherName;

    @JsonProperty("isCancelable")
    boolean cancelable;
  }
}
