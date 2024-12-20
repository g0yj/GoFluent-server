package com.lms.api.mobile.service;

import com.lms.api.common.entity.ReservationEntity;
import com.lms.api.common.entity.ScheduleEntity;
import com.lms.api.common.entity.TeacherEntity;
import com.lms.api.common.mapper.ServiceMapper;
import com.lms.api.common.mapper.ServiceMapperConfig;
import com.lms.api.mobile.service.dto.Reservation;
import com.lms.api.mobile.service.dto.Schedule;
import com.lms.api.mobile.service.dto.Teacher;
import java.util.Optional;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", config = ServiceMapperConfig.class, uses = {
    ServiceMapper.class})
public interface ReservationServiceMapper {
    @Mapping(target = "teacher", source = "teacherEntity")
    Reservation toReservation(ReservationEntity reservationEntity);

    @Mapping(target = "user", source = "userEntity")
    Teacher toTeacher(TeacherEntity teacherEntity);

    @Mapping(target = "teacher", source = "teacherEntity")
    Schedule toSchedule(ScheduleEntity scheduleEntity);

    Schedule toSchedule( Optional<ScheduleEntity> scheduleEntity);
}
