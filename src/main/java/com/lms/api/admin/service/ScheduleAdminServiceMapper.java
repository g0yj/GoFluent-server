package com.lms.api.admin.service;

import com.lms.api.admin.service.dto.Reservation;
import com.lms.api.admin.service.dto.Schedule;
import com.lms.api.admin.service.dto.Teacher;
import com.lms.api.common.entity.ReservationEntity;
import com.lms.api.common.entity.ScheduleEntity;
import com.lms.api.common.entity.TeacherEntity;
import com.lms.api.common.mapper.ServiceMapper;
import com.lms.api.common.mapper.ServiceMapperConfig;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", config = ServiceMapperConfig.class, uses = {ServiceMapper.class})
public interface ScheduleAdminServiceMapper {

  Schedule toSchedule(ScheduleEntity scheduleEntity, Teacher teacher);

  @Mapping(target = "user", source = "teacherEntity.userEntity")
  @Mapping(target = "reservations", source = "reservationEntities")
  Teacher toTeacher(TeacherEntity teacherEntity, List<ReservationEntity> reservationEntities);

  @Mapping(target = "user", source = "userEntity")
  @Mapping(target = "course", source = "courseEntity")
  Reservation toReservation(ReservationEntity reservationEntity);
}
