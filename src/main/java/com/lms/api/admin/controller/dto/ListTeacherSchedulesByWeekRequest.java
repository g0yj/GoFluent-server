package com.lms.api.admin.controller.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.YearMonth;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListTeacherSchedulesByWeekRequest {

  /** 조회할 날짜(YYYY-MM) */
  @NotNull
  YearMonth dateMonth;

  /** 조회할 주차 */
  @NotNull
  @Min(1)
  @Max(6)
  Integer week;
}
