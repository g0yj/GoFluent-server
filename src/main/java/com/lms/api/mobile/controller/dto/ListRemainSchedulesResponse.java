package com.lms.api.mobile.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListRemainSchedulesResponse {
    List<Schedule> schedules;

    @Getter
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Schedule {
        long id;
        String teacherId;
        String teacherName;

        @JsonFormat(pattern = "HH:mm")
        LocalTime startTime;

        @JsonFormat(pattern = "HH:mm")
        public LocalTime getEndTime() {
            return startTime.plusMinutes(30);
        }
    }
}
