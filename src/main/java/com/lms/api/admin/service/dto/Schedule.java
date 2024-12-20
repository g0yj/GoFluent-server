package com.lms.api.admin.service.dto;

import com.lms.api.common.code.WorkTime;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Schedule {

  long id;
  LocalDate date;
  LocalTime startTime;
  WorkTime workTime;
  Teacher teacher;
}
