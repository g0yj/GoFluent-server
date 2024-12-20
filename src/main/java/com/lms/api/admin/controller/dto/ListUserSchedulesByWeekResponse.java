package com.lms.api.admin.controller.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListUserSchedulesByWeekResponse extends ListUserSchedulesByDateResponse {

  int week;

  public ListUserSchedulesByWeekResponse(ListUserSchedulesByDateResponse byDateResponse, int week) {
    super(byDateResponse.getSchedules());
    this.week = week;
  }
}
