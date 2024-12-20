package com.lms.api.admin.service.dto.teacher;

import com.lms.api.common.code.ScheduleType;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateCgt {

  LocalDate date;

  LocalTime startTime;

  String teacherId;

  ScheduleType type;

  int reservationLimit;

  String modifiedBy;

}
