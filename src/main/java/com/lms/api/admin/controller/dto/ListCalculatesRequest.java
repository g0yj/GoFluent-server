package com.lms.api.admin.controller.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListCalculatesRequest {

  @NotNull
  LocalDate dateFrom;

  @NotNull
  LocalDate dateTo;

  String creatorName;
}
