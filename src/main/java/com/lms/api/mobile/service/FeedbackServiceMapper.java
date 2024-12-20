package com.lms.api.mobile.service;

import com.lms.api.common.entity.LdfEntity;
import com.lms.api.common.entity.ReservationEntity;
import com.lms.api.common.entity.ScheduleEntity;
import com.lms.api.common.entity.TeacherEntity;
import com.lms.api.common.mapper.ServiceMapper;
import com.lms.api.common.mapper.ServiceMapperConfig;
import com.lms.api.mobile.controller.dto.feedback.UpdateLdfRequest;
import com.lms.api.mobile.service.dto.Schedule;
import com.lms.api.mobile.service.dto.Teacher;
import com.lms.api.mobile.service.dto.feedback.Ldf;
import com.lms.api.mobile.service.dto.feedback.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", config = ServiceMapperConfig.class, uses = {
    ServiceMapper.class})
public interface FeedbackServiceMapper {

    Reservation toReservation(ReservationEntity reservationEntity);

    @Mapping(target = "user", source = "userEntity")
    Teacher toTeacher(TeacherEntity teacherEntity);

    @Mapping(target = "teacher", source = "teacherEntity")
    Schedule toSchedule(ScheduleEntity scheduleEntity);

    Ldf toLdf(LdfEntity ldfEntity);

    @Mapping(target = "id" , ignore = true)
    void mapLdfEntity(UpdateLdfRequest request, @MappingTarget LdfEntity ldfEntity);
}
