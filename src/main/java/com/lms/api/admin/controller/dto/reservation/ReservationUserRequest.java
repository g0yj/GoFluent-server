package com.lms.api.admin.controller.dto.reservation;

import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReservationUserRequest {
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  LocalDate dateFrom;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  LocalDate dateTo;

  public ReservationUserRequest(LocalDate dateFrom, LocalDate dateTo) {
    this.dateFrom = dateFrom;
    this.dateTo = dateTo;
  }
}
