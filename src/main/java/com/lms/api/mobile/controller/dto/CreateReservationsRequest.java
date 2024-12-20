package com.lms.api.mobile.controller.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateReservationsRequest {
    @Positive
    long courseId;

    @NotNull
    LocalDate date;

    @NotNull
    LocalTime time;

    @NotEmpty
    String teacherId;

    Long remainScheduleId;
}
