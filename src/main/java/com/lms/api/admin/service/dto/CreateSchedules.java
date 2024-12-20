package com.lms.api.admin.service.dto;

import com.lms.api.common.code.WorkTime;
import java.time.LocalDate;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateSchedules {

  String teacherId;
  LocalDate startDate;
  LocalDate endDate;
  WorkTime workTime;
  String modifiedBy;
  List<CreateSchedule> schedules;
}
