package com.lms.api.admin.controller.dto.statistics;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListLdfResponse {

  long id;
  String date;
  String startTime;
  String endTime;
  String userName;
  double grade;
  String evaluation;
}
