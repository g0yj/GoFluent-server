package com.lms.api.admin.controller.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.YearMonth;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListUserSchedulesByWeekRequest {

  /** 조회할 날짜(YYYY-MM) */
  @NotNull
  YearMonth dateMonth;

  /** 스케줄을 조회할 담임강사 ID */
  @NotNull
  @Min(1)
  @Max(6)
  Integer week;

  /** 스케줄을 조회할 담임강사 ID */
  @NotEmpty
  String teacherId;

  /** 스케줄을 조회할 부담임 강사 ID */
  @NotEmpty
  String assistantTeacherId;
}
