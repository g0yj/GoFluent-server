package com.lms.api.admin.controller.dto;

import com.lms.api.common.code.AttendanceStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateUserReservationRequest {

  AttendanceStatus attendanceStatus;
  String report;
}
