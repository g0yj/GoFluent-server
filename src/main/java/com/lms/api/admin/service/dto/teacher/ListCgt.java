package com.lms.api.admin.service.dto.teacher;

import com.lms.api.common.code.ScheduleType;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListCgt {
  int id;
  String teacherId;
  String teacherName;
  LocalDate date;
  LocalTime startTime;
  LocalTime endTime;
  LocalTime cgtTime;
  int reservationLimit;
  int reservationCount;
  List<Schedule> schedules;

  @Getter
  @Setter
  @FieldDefaults(level = AccessLevel.PRIVATE)
  public static class Schedule {
    Long id;
    LocalDate date;
    LocalTime startTime;
    LocalTime cgtTime;
    ScheduleType type;
  }
}
