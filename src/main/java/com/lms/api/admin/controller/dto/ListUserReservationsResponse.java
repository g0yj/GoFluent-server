package com.lms.api.admin.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lms.api.common.code.LessonType;
import com.lms.api.common.controller.dto.PageResponseData;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListUserReservationsResponse extends PageResponseData {

  long id;
  String teacherName;
  LessonType lessonType;

  @JsonFormat(pattern = "yyyy-MM-dd (EEE)", locale = "en-us")
  LocalDate date;

  @JsonFormat(pattern = "HH:mm")
  LocalTime startTime;

  @JsonFormat(pattern = "HH:mm")
  LocalTime endTime;

  String attendanceStatus;
  String modifierName;

  @JsonFormat(pattern = "yyyy-MM-dd")
  LocalDate modifiedOn;

  String courseName;
  String report;

  @JsonProperty("isCancel")
  boolean cancel;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
  LocalDateTime cancelDate;

  String cancelerName;
}
