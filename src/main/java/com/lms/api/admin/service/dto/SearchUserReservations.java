package com.lms.api.admin.service.dto;

import com.lms.api.common.service.dto.Search;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchUserReservations extends Search {

  String userId;
  Long courseId;
  LocalDate dateFrom;
  LocalDate dateTo;
  Boolean excludeCancel; // 취소 제외
  Boolean excludeAttendance; // 출석 제외
}
