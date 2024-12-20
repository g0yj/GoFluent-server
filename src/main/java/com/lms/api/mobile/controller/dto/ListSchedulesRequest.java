package com.lms.api.mobile.controller.dto;

import jakarta.validation.constraints.NotNull;
import java.time.YearMonth;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListSchedulesRequest {
    @NotNull
    YearMonth yearMonth;
}
