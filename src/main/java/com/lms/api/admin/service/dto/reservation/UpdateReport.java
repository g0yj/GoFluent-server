package com.lms.api.admin.service.dto.reservation;

import com.lms.api.common.code.AttendanceStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateReport {

  long reservationId;
  AttendanceStatus attendanceStatus;
  String report;
  String todayLesson;
  String nextLesson;
  String modifiedBy;
}
