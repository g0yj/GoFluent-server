package com.lms.api.admin.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lms.api.common.code.WorkTime;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateTeacherSchedulesRequest {

  @NotNull
  LocalDate dateFrom;

  @NotNull
  LocalDate dateTo;

  WorkTime workTime;
  List<Schedule> schedules;

  @Getter
  @FieldDefaults(level = AccessLevel.PRIVATE)
  public static class Schedule {

    @NotNull
    LocalDate date;

    @NotNull
    @JsonFormat(pattern = "HH:mm")
    LocalTime time;
  }
}
