package com.lms.api.mobile.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class CreateReservationsResponse {
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate date;

    List<Schedule> schedules;

    @Getter
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Schedule {
        @JsonFormat(pattern = "HH:mm")
        LocalTime time;

        String teacherName;
    }
}
