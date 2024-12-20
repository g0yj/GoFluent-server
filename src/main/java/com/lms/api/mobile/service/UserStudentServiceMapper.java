package com.lms.api.mobile.service;

import com.lms.api.common.entity.CourseEntity;
import com.lms.api.common.entity.OrderProductEntity;
import com.lms.api.common.entity.ReservationEntity;
import com.lms.api.common.entity.TeacherEntity;
import com.lms.api.common.mapper.ServiceMapper;
import com.lms.api.common.mapper.ServiceMapperConfig;
import com.lms.api.mobile.service.dto.Course;
import com.lms.api.mobile.service.dto.OrderProduct;
import com.lms.api.mobile.service.dto.Reservation;
import com.lms.api.mobile.service.dto.Teacher;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", config = ServiceMapperConfig.class, uses = {
    ServiceMapper.class})
public interface UserStudentServiceMapper {
  @Mapping(target = "orderProduct", source = "courseEntity.orderProductEntity")
  @Mapping(target = "reservations", source = "reservationEntities")
  Course toCourse(CourseEntity courseEntity, List<ReservationEntity> reservationEntities);

  @Mapping(target = "product", source = "productEntity")
  OrderProduct toOrderProduct(OrderProductEntity orderProductEntity);

  @Mapping(target = "teacher", source = "teacherEntity")
  Reservation toReservation(ReservationEntity reservationEntity);

  @Mapping(target = "user", source = "userEntity")
  Teacher toTeacher(TeacherEntity teacherEntity);
}
