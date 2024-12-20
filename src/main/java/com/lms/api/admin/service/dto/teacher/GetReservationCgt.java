package com.lms.api.admin.service.dto.teacher;

import com.lms.api.admin.service.dto.User;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetReservationCgt {

    User user;
    LocalDate date;
    LocalTime startTime;
    LocalTime endTime;
    String teacherId;
    List<Long> schedules;

}
