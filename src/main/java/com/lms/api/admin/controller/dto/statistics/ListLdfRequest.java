package com.lms.api.admin.controller.dto.statistics;

import jakarta.validation.constraints.NotNull;
import java.time.YearMonth;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class ListLdfRequest {

  @NotNull
  YearMonth date;
  @NotNull
  String teacherId;

}
