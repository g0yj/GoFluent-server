package com.lms.api.admin.controller.dto.teacher;

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
public class ListReservationCgtResponse {
  String userId;
  String name;
  LocalDate date;
  LocalTime startTime;
  LocalTime endTime;
  String teacherId;
  List<Long> schedules;
}
