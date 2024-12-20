package com.lms.api.admin.service.dto.teacher;

import java.time.LocalTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListCgtTime {
  int timeId;
  List<Schedule> schedules;

  @Getter
  @Setter
  @FieldDefaults(level = AccessLevel.PRIVATE)
  public static class Schedule {
    Long id;
    LocalTime startTime;
  }
}
