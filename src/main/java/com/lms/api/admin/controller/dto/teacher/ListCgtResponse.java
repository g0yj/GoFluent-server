package com.lms.api.admin.controller.dto.teacher;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.lms.api.admin.service.dto.teacher.ListCgt.Schedule;
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
public class ListCgtResponse {

  int id; // cgt 식별키
  String teacherId; // 강사식별키
  String teacherName;
  LocalDate date; //날짜
  @JsonFormat(pattern = "HH:mm")
  LocalTime startTime;
  @JsonFormat(pattern = "HH:mm")
  LocalTime endTime;

  LocalTime cgtTime;

  int reservationLimit;
  int reservationCount;
  List<Schedule> schedules;

}
