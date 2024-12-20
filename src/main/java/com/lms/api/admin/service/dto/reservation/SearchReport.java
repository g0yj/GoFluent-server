package com.lms.api.admin.service.dto.reservation;

import com.lms.api.admin.code.SearchReservationCode.ReportCondition;
import com.lms.api.admin.code.SearchReservationCode.ReportSort;
import com.lms.api.common.code.UserType;
import com.lms.api.common.service.dto.Search;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchReport extends Search {

  UserType userType;
  String loginId;

  LocalDate dateFrom;
  LocalDate dateTo;
  String teacherId;

  ReportCondition reportCondition;
  ReportSort reportSort;

}
