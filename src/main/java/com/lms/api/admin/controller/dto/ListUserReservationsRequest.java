package com.lms.api.admin.controller.dto;

import com.lms.api.common.controller.dto.PageRequest;
import java.time.LocalDate;
import java.time.YearMonth;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListUserReservationsRequest extends PageRequest {

  Long courseId;
  LocalDate dateFrom;
  LocalDate dateTo;
  Boolean excludeCancel;
  Boolean excludeAttendance;

  YearMonth yearMonth;
  LocalDate date;

  public ListUserReservationsRequest(Integer page, Integer limit, Integer pageSize, String order,
      String direction, String search, String keyword,
      Long courseId, LocalDate dateFrom, LocalDate dateTo, Boolean excludeCancel,
      Boolean excludeAttendance, YearMonth yearMonth, LocalDate date) {

    super(page, limit, pageSize, order, direction, search, keyword);
    this.courseId = courseId;
    this.dateFrom = dateFrom;
    this.dateTo = dateTo;
    this.excludeCancel = excludeCancel;
    this.excludeAttendance = excludeAttendance;
    this.yearMonth = yearMonth;
    this.date = date;
  }

  public LocalDate getDateFrom() {
    if (yearMonth != null) {
      return yearMonth.atDay(1);
    }

    if (date != null) {
      return date;
    }

    return dateFrom;
  }

  public LocalDate getDateTo() {
    if (yearMonth != null) {
      return yearMonth.atEndOfMonth();
    }

    if (date != null) {
      return date;
    }

    return dateTo;
  }
}
