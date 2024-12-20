package com.lms.api.mobile.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lms.api.common.code.AttendanceStatus;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListSchedulesResponse {
    List<Schedule> schedules;

    @Getter
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Schedule {
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate date;

        List<Reservation> reservations;
    }

    @Getter
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Reservation {
        long id;

        @JsonFormat(pattern = "HH:mm")
        LocalTime startTime;

        @JsonFormat(pattern = "HH:mm")
        LocalTime endTime;

        AttendanceStatus attendanceStatus;
        String attendanceStatusLabel;

        String teacherName;

        @JsonProperty("isCancelable")
        boolean cancelable;
    }
}
