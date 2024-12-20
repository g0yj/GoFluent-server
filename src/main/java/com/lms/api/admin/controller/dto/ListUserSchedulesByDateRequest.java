package com.lms.api.admin.controller.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListUserSchedulesByDateRequest {

  @NotEmpty
  String teacherId;

  @NotEmpty
  String assistantTeacherId;

  @NotNull
  LocalDate dateFrom;

  @NotNull
  LocalDate dateTo;
}
