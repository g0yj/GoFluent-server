package com.lms.api.common.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public interface ReservationNotification {

  String getUserId();

  String getTeacherId();

  LocalDate getDate();

  LocalTime getStartTime();

  LocalTime getEndTime();

  String getName();

  String getCellPhone();
}
