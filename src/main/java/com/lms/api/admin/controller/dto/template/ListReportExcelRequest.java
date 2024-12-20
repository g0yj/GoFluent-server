package com.lms.api.admin.controller.dto.template;

import com.lms.api.admin.code.SearchReservationCode.ReportCondition;
import com.lms.api.common.code.UserType;
import java.time.LocalDate;
import java.time.YearMonth;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
public class ListReportExcelRequest  {

  // 운영자페이지,강사페이지 구분을 위함
  UserType userType;

  LocalDate dateFrom;
  LocalDate dateTo;
  String teacherId;

  String keyword;

  ReportCondition reportCondition;

  YearMonth yearMonth;
  LocalDate date;

  public ListReportExcelRequest(UserType userType, LocalDate dateFrom, LocalDate dateTo, String teacherId, ReportCondition reportCondition, YearMonth yearMonth, LocalDate date) {
    this.userType = userType;
    this.dateFrom = dateFrom;
    this.dateTo = dateTo;
    this.teacherId = teacherId;
    this.reportCondition = reportCondition;
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

