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
public class UpdateCancelReservationsResponse {
    List<Reservation> cancelReservations;

    @Getter
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Reservation {
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate cancelDate;

        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate date;

        @JsonFormat(pattern = "HH:mm")
        LocalTime startTime;

        @JsonFormat(pattern = "HH:mm")
        LocalTime endTime;

        String teacherName;
    }
}
