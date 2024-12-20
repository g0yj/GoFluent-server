package com.lms.api.admin.controller.dto.reservation;

import com.lms.api.admin.code.SearchReservationCode.ReportCondition;
import com.lms.api.admin.code.SearchReservationCode.ReportSort;
import com.lms.api.common.code.UserType;
import com.lms.api.common.controller.dto.PageRequest;
import java.time.LocalDate;
import java.time.YearMonth;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListReportRequest extends PageRequest {

  // 운영자페이지,강사페이지 구분을 위함
  UserType userType;

  LocalDate dateFrom;
  LocalDate dateTo;
  String teacherId;

  ReportCondition reportCondition;
  ReportSort reportSort;

  YearMonth yearMonth;
  LocalDate date;

  public ListReportRequest(Integer page, Integer limit, Integer pageSize, String order, String direction, String search, String keyword, UserType userType, LocalDate dateFrom, LocalDate dateTo, String teacherId, ReportCondition reportCondition, ReportSort reportSort, YearMonth yearMonth, LocalDate date) {
    super(page, limit, pageSize, order, direction, search, keyword);
    this.userType = userType;
    this.dateFrom = dateFrom;
    this.dateTo = dateTo;
    this.teacherId = teacherId;
    this.reportCondition = reportCondition;
    this.reportSort = reportSort;
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

