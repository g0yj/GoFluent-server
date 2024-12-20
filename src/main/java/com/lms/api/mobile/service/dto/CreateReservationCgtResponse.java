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
public class CreateReservationCgtResponse {
    // 수업예약시간
    LocalDate date;
    LocalTime time;
    // 강사명
    String teacherName;
    // 성공 메세지
    String message;
}
