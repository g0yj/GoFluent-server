package com.lms.api.admin.controller.dto;

import com.lms.api.common.code.AttendanceStatus;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListTeacherAttendancesDateRequest {

  @NotNull
  AttendanceStatus status;

  @NotNull
  LocalDate yearMonthDay;
}
