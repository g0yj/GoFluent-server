package com.lms.api.admin.controller.dto.teacher;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListReservationCgtRequest {
   // 목록에서 가져올 것들
    @NotNull
    LocalDate date;
    @NotNull
    String teacherId;
    @NotNull
    List<Long> schedules;
    @NotNull
    LocalTime cgtTime;

}
