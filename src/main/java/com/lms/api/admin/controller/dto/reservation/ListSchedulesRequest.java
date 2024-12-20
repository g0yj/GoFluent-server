package com.lms.api.admin.controller.dto.reservation;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListSchedulesRequest {

  @NotNull
  LocalDate date;

  public ListSchedulesRequest(LocalDate date) {
    this.date = date;
  }
}
