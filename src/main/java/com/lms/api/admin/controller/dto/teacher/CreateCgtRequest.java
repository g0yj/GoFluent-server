package com.lms.api.admin.controller.dto.teacher;

import com.lms.api.common.code.ScheduleType;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateCgtRequest {

  @NotNull
  LocalDate date;

  @NotNull
  @DateTimeFormat(pattern = "HH:mm:ss")
  LocalTime startTime;

  @NotNull
  String teacherId;

  @NotNull
  ScheduleType type;

  int reservationLimit;

}

