package com.lms.api.admin.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lms.api.admin.code.UserSchedulesCode.ReservationStatus;
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
public class ListUserSchedulesByDateResponse {
  List<Schedule> schedules;

  @Getter
  @Builder
  @FieldDefaults(level = AccessLevel.PRIVATE)
  public static class Schedule {
    @JsonFormat(pattern = "HH:mm")
    LocalTime time;
    List<Reservation> reservations;
  }

  @Getter
  @Builder
  @FieldDefaults(level = AccessLevel.PRIVATE)
  public static class Reservation {
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate date;

    Long teacherScheduleId;
    ReservationStatus teacherStatus;
    Long assistantTeacherScheduleId;
    ReservationStatus assistantTeacherStatus;
    List<Teacher> teachers;
  }

  @Getter
  @Builder
  @FieldDefaults(level = AccessLevel.PRIVATE)
  public static class Teacher {
    String id;
    String name;
  }
}
