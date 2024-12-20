package com.lms.api.admin.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetUserLdfResponse {

  long id;

  String lesson;

  @JsonFormat(pattern = "yyyy-MM-dd (EEE)", locale = "en-us")
  LocalDate date;
  @JsonFormat(pattern = "HH:mm")
  LocalTime startTime;
  @JsonFormat(pattern = "HH:mm")
  LocalTime endTime;

  String teacherName;
  String contentSp;
  String contentV;
  String contentSg;
  String contentC;
}
