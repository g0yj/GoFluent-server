package com.lms.api.mobile.controller.dto.feedback;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lms.api.common.code.AttendanceStatus;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListFeedbackResponse {
    int id;
    LocalDate date;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "hh:mm a", locale = "en")
    LocalTime startTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "hh:mm a", locale = "en")
    LocalTime endTime;
    AttendanceStatus attendanceStatus;
    String teacherName;

    String ldfYN;


}
