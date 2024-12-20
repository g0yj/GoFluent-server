package com.lms.api.mobile.service.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateReservations {
    String userId;
    long courseId;
    LocalDate date;
    LocalTime time;
    String teacherId;
    Long remainScheduleId;

    public int getReservationScheduleSize() {
        return remainScheduleId == null ? 1 : 2;
    }
}
