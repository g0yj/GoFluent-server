package com.lms.api.mobile.controller.dto;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateReservationCgtRequest {
    @NotNull
    List<Long> schedules;

}
