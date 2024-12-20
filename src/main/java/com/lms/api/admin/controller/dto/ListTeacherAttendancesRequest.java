package com.lms.api.admin.controller.dto;

import com.lms.api.common.code.AttendanceStatus;
import jakarta.validation.constraints.NotNull;
import java.time.YearMonth;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListTeacherAttendancesRequest {

  @NotNull
  AttendanceStatus status;

  @NotNull
  YearMonth yearMonth;
}
