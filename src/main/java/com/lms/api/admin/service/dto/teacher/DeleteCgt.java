package com.lms.api.admin.service.dto.teacher;

import com.lms.api.common.code.ScheduleType;
import java.time.LocalTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeleteCgt {

  List<Long> schedules;

  ScheduleType type;

  int reservationLimit;

  LocalTime cgtTime;

  String modifiedBy;

}
