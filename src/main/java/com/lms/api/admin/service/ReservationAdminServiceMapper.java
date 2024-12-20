package com.lms.api.admin.service;

import com.lms.api.admin.service.dto.Course;
import com.lms.api.admin.service.dto.Reservation;
import com.lms.api.admin.service.dto.Teacher;
import com.lms.api.admin.service.dto.User;
import com.lms.api.admin.service.dto.reservation.UpdateReport;
import com.lms.api.common.code.CourseStatus;
import com.lms.api.common.entity.CourseEntity;
import com.lms.api.common.entity.ReservationEntity;
import com.lms.api.common.entity.TeacherEntity;
import com.lms.api.common.entity.UserEntity;
import com.lms.api.common.mapper.ServiceMapper;
import com.lms.api.common.mapper.ServiceMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", config = ServiceMapperConfig.class, uses = {ServiceMapper.class})
public interface ReservationAdminServiceMapper {

  @Mapping(target = "user", source = "reservationEntity.userEntity")
  @Mapping(target = "course", source = "reservationEntity.courseEntity")
  @Mapping(target = "teacher", source = "reservationEntity.teacherEntity")
  @Mapping(target = "product", source = "reservationEntity.courseEntity.orderProductEntity.productEntity")
  Reservation toReservation(ReservationEntity reservationEntity);

  User toUser(UserEntity userEntity);

  @Mapping(target = "user", source = "courseEntity.userEntity")
  @Mapping(target = "orderProduct", source = "courseEntity.orderProductEntity")
  @Mapping(target = "teacher", source = "courseEntity.teacherEntity")
  @Mapping(target = "assistantTeacher", source = "courseEntity.assistantTeacherEntity")
  Course toCourse(CourseEntity courseEntity);

  @Mapping(target = "user", source = "courseEntity.userEntity")
  @Mapping(target = "orderProduct", source = "courseEntity.orderProductEntity")
  @Mapping(target = "teacher", source = "courseEntity.teacherEntity")
  @Mapping(target = "assistantTeacher", source = "courseEntity.assistantTeacherEntity")
  Course toCourse(CourseEntity courseEntity, CourseStatus status);

  @Mapping(target = "user", source = "userEntity")
  Teacher toTeacher(TeacherEntity teacherEntity);

  @Mapping(target = "id", source = "reservationId", ignore = true)
  void mapReservationEntity(UpdateReport updateReport,
      @MappingTarget ReservationEntity reservationEntity);

  @Mapping(target = "user", source = "reservationEntity.userEntity")
  @Mapping(target = "teacher", source = "reservationEntity.teacherEntity")
  @Mapping(target = "product", source = "reservationEntity.courseEntity.orderProductEntity.productEntity")
  Reservation toReport(ReservationEntity reservationEntity);


}
