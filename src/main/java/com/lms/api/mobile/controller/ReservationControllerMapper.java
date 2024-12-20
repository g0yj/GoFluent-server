package com.lms.api.mobile.controller;

import com.lms.api.common.mapper.ControllerMapper;
import com.lms.api.common.mapper.ControllerMapperConfig;
import com.lms.api.mobile.controller.dto.CreateReservationCgtRequest;
import com.lms.api.mobile.controller.dto.CreateReservationsRequest;
import com.lms.api.mobile.controller.dto.CreateReservationsResponse;
import com.lms.api.mobile.controller.dto.ListRemainSchedulesResponse;
import com.lms.api.mobile.controller.dto.ListSchedulesResponse;
import com.lms.api.mobile.controller.dto.ListTeachersResponse;
import com.lms.api.mobile.controller.dto.UpdateCancelReservationsRequest;
import com.lms.api.mobile.controller.dto.UpdateCancelReservationsResponse;
import com.lms.api.mobile.service.dto.CreateReservationCgt;
import com.lms.api.mobile.service.dto.CreateReservations;
import com.lms.api.mobile.service.dto.Reservation;
import com.lms.api.mobile.service.dto.Schedule;
import com.lms.api.mobile.service.dto.Teacher;
import com.lms.api.mobile.service.dto.UpdateCancelReservations;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", config = ControllerMapperConfig.class, uses = {
    ControllerMapper.class})
public interface ReservationControllerMapper {
    UpdateCancelReservations toUpdateCancelReservations(UpdateCancelReservationsRequest request);

    List<UpdateCancelReservationsResponse.Reservation> toUpdateCancelReservationsResponseReservations(List<Reservation> reservations);

    @Mapping(target = "teacherName", source = "teacher.name")
    UpdateCancelReservationsResponse.Reservation toUpdateCancelReservationsResponseReservations(Reservation reservation);

    List<ListTeachersResponse.Teacher> toListTeachersResponseTeachers(List<Teacher> teachers);

    @Mapping(target = "id", source = "userId")
    ListTeachersResponse.Teacher toListTeachersResponseTeacher(Teacher teacher);

    List<ListRemainSchedulesResponse.Schedule> toListSchedulesResponseSchedules(List<Schedule> schedules);

    @Mapping(target = "teacherId", source = "teacher.userId")
    @Mapping(target = "teacherName", source = "teacher.name")
    ListRemainSchedulesResponse.Schedule toListSchedulesResponseSchedule(Schedule schedule);

    CreateReservations toCreateReservations(CreateReservationsRequest request, String userId);

    List<CreateReservationsResponse.Schedule> toCreateReservationsResponseSchedules(List<Schedule> schedules);

    @Mapping(target = "time", source = "startTime")
    @Mapping(target = "teacherName", source = "teacher.name")
    CreateReservationsResponse.Schedule toCreateReservationsResponseSchedule(Schedule schedule);

    @Mapping(target = "teacherName", source = "teacher.name")
    @Mapping(target = "attendanceStatusLabel", source = "attendanceStatus")
    ListSchedulesResponse.Reservation toListSchedulesResponseReservation(Reservation reservation);

    @Mapping(target = "createdBy" , source = "loginId")
    @Mapping(target = "userId" , source = "loginId")
    CreateReservationCgt toCreateReservationCgt(String loginId, CreateReservationCgtRequest request);

}
