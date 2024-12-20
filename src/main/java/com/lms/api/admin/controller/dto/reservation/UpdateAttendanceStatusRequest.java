package com.lms.api.admin.controller.dto.reservation;

import com.lms.api.common.code.AttendanceStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateAttendanceStatusRequest {

  @NotNull
  AttendanceStatus attendanceStatus;
  @NotNull
  Long reservationId;
}
