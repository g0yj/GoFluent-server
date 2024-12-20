package com.lms.api.mobile.controller;

import com.lms.api.admin.service.dto.Login;
import com.lms.api.common.code.UserType;
import com.lms.api.common.controller.dto.LoginRequest;
import com.lms.api.common.controller.dto.LoginResponse;
import com.lms.api.common.mapper.ControllerMapper;
import com.lms.api.common.mapper.ControllerMapperConfig;
import com.lms.api.mobile.controller.dto.ListMainReservationResponse;
import com.lms.api.mobile.controller.dto.MobileMainResponse;
import com.lms.api.mobile.controller.dto.UpdatePasswordRequest;
import com.lms.api.mobile.service.dto.Course;
import com.lms.api.mobile.service.dto.Reservation;
import com.lms.api.mobile.service.dto.UpdatePassword;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", config = ControllerMapperConfig.class, uses = {
    ControllerMapper.class})
public interface MobileControllerMapper {

  @Mapping(target = "loginId", source = "id")
  Login toLogin(LoginRequest loginRequest);

  @Mapping(target = "loginId", source = "loginRequest.id")
  @Mapping(target = "type", source = "type")
  Login toLogin(LoginRequest loginRequest, UserType type);

  LoginResponse toLoginResponse(Login login);

  List<MobileMainResponse.Course> toCourses(List<Course> courses);

  List<MobileMainResponse> toMobileMainResponse(List<Course> courses);

  @Mapping(target = "teacherId", source = "teacher.userId")
  @Mapping(target = "teacherName", source = "teacher.name")
  MobileMainResponse.Reservation toReservation(Reservation reservation);

  @Mapping(target = "modifiedBy" , source = "loginInfo")
  @Mapping(target = "id" , source = "loginInfo")
  UpdatePassword toUpdatePassword(String loginInfo , UpdatePasswordRequest request);

  List<ListMainReservationResponse> toListMainReservationResponse(List<Reservation> reservation);

  @Mapping(target = "teacherName", source = "teacher.user.name")
  ListMainReservationResponse toListMainReservationResponse(Reservation reservation);
}
