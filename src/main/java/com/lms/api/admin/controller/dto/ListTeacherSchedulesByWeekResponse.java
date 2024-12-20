package com.lms.api.admin.controller.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListTeacherSchedulesByWeekResponse extends  ListTeacherSchedulesResponse {

  int week;

  public ListTeacherSchedulesByWeekResponse(ListTeacherSchedulesResponse response, int week) {
    super(response.getSchedules());
    this.week = week;
  }

}