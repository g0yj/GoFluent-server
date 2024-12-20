package com.lms.api.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "calendar")
public class CalendarEntity {

  @Id
  @Column(name = "calendar_date")
  LocalDate calendarDate;

  Integer year;

  Integer month;

  Integer day;

  String dayOfWeek;

  @Column(name = "is_weekend")
  boolean isWeekend;
  @Column(name = "is_holiday")
  boolean isHoliday;
  @Column(name = "holiday_name")
  String holidayName;

}
