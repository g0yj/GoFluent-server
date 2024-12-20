package com.lms.api.admin.controller;

import com.lms.api.admin.controller.dto.CreateTeacherSchedulesRequest;
import com.lms.api.admin.controller.dto.teacher.CreateCgtRequest;
import com.lms.api.admin.controller.dto.teacher.CreateReservationCgtRequest;
import com.lms.api.admin.controller.dto.teacher.CreateTeacherRequest;
import com.lms.api.admin.controller.dto.teacher.DeleteCgtRequest;
import com.lms.api.admin.controller.dto.teacher.GetTeacherResponse;
import com.lms.api.admin.controller.dto.teacher.ListCgtRequest;
import com.lms.api.admin.controller.dto.teacher.ListCgtResponse;
import com.lms.api.admin.controller.dto.teacher.ListCgtTimeResponse;
import com.lms.api.admin.controller.dto.teacher.ListReservationCgtResponse;
import com.lms.api.admin.controller.dto.teacher.ListTeacherRequest;
import com.lms.api.admin.controller.dto.teacher.ListTeacherResponse;
import com.lms.api.admin.controller.dto.teacher.MCreateTeacherRequest;
import com.lms.api.admin.controller.dto.teacher.MUpdateTeacherRequest;
import com.lms.api.admin.controller.dto.teacher.UpdateTeacherRequest;
import com.lms.api.admin.service.dto.CreateSchedule;
import com.lms.api.admin.service.dto.CreateSchedules;
import com.lms.api.admin.service.dto.teacher.CreateCgt;
import com.lms.api.admin.service.dto.teacher.CreateReservationCgt;
import com.lms.api.admin.service.dto.teacher.CreateTeacher;
import com.lms.api.admin.service.dto.teacher.DeleteCgt;
import com.lms.api.admin.service.dto.teacher.GetReservationCgt;
import com.lms.api.admin.service.dto.teacher.GetTeacher;
import com.lms.api.admin.service.dto.teacher.ListCgt;
import com.lms.api.admin.service.dto.teacher.MCreateTeacher;
import com.lms.api.admin.service.dto.teacher.MUpdateTeacher;
import com.lms.api.admin.service.dto.teacher.SearchListCgt;
import com.lms.api.admin.service.dto.teacher.SearchListTeachers;
import com.lms.api.admin.service.dto.teacher.SearchTeacherResponse;
import com.lms.api.admin.service.dto.teacher.UpdateTeacher;
import com.lms.api.common.controller.dto.PageResponse;
import com.lms.api.common.mapper.ControllerMapper;
import com.lms.api.common.mapper.ControllerMapperConfig;
import com.lms.api.common.service.dto.Search;
import java.time.LocalTime;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring", config = ControllerMapperConfig.class, uses = {
    ControllerMapper.class})
public interface TeacherAdminControllerMapper {

  @Mapping(target = "startDate", source = "createTeacherSchedulesRequest.dateFrom")
  @Mapping(target = "endDate", source = "createTeacherSchedulesRequest.dateTo")
  CreateSchedules toCreateSchedules(CreateTeacherSchedulesRequest createTeacherSchedulesRequest,
      String teacherId, String modifiedBy);

  @Mapping(target = "startTime", source = "time")
  CreateSchedule toCreateSchedule(CreateTeacherSchedulesRequest.Schedule schedule);


  SearchListTeachers toSearchTeachers(ListTeacherRequest request);

  @Mapping(target = "list", source = "teacherPage.content")
  @Mapping(target = "totalCount", source = "teacherPage.totalElements")
  PageResponse<ListTeacherResponse> toListTeacherRepsonse(Page<SearchTeacherResponse> teacherPage,
      Search search);


  ListTeacherResponse toListTeacherResponse(SearchTeacherResponse teacher);

  /**
   * 개별출력
   */
  @Mapping(target = "id", source = "teacher.id")
  @Mapping(target = "loginId", source = "teacher.loginId")
  @Mapping(target = "name", source = "teacher.name")
  @Mapping(target = "nameEn", source = "teacher.nameEn")
  @Mapping(target = "lastNameEn", source = "teacher.lastNameEn")
  @Mapping(target = "password", source = "teacher.password")
  @Mapping(target = "email", source = "teacher.email")
  @Mapping(target = "gender", source = "teacher.gender")
  @Mapping(target = "cellPhone", source = "teacher.cellPhone")
  @Mapping(target = "files", source = "teacherFiles")
  @Mapping(target = "teacherType", source = "type")
  GetTeacherResponse toGetTeacherResponse(GetTeacher teacher);

  /**
   * 강사 등록
   */
  @Mapping(target = "multipartFiles", source = "request.files")
  @Mapping(target = "file", ignore = true)
  @Mapping(target = "createdBy", source = "createdBy")
  @Mapping(target = "isReceiveEmail", source = "request.isReceiveEmail")
  @Mapping(target = "isReceiveSms", source = "request.isReceiveSms")
  CreateTeacher toCreateTeacher(CreateTeacherRequest request, String createdBy);

  /**
   * M 강사등록
   */
  @Mapping(target = "createdBy", source = "createdBy")
  MCreateTeacher toMcreateTeacher(MCreateTeacherRequest request, String createdBy);


  /**
   * 수정
   */
  @Mapping(target = "multipartFiles", source = "updateTeacher.files")
  @Mapping(target = "file", ignore = true)
  @Mapping(target = "userId", source = "id")
  UpdateTeacher toUpdateTeacher(String id, UpdateTeacherRequest updateTeacher, String modifiedBy);

  /**
   * M 강사 수정
   */
  @Mapping(target = "userId", source = "id")
  MUpdateTeacher toMupdateTeacher(String id, MUpdateTeacherRequest mUpdateTeacher,
      String modifiedBy);


  @Mapping(target = "modifiedBy", source = "modifiedBy")
  @Mapping(target = "startTime" , source = "request.startTime")
  CreateCgt toCreateCgt(CreateCgtRequest request, String modifiedBy);

  SearchListCgt toSearchCgt(ListCgtRequest request);

  @Mapping(target = "list", source = "cgtPage.content")
  @Mapping(target = "totalCount", source = "cgtPage.totalElements")
  PageResponse<ListCgtResponse> toListCgtResponse(Page<ListCgt> cgtPage, Search searchCgts);

  @Mapping(target = "reservationLimit", source = "reservationLimit")
  @Mapping(target = "reservationCount", source = "reservationCount")
  @Mapping(target = "endTime", source = "endTime")
  @Mapping(target = "cgtTime", source = "cgtTime")
  ListCgtResponse toListCgtResponse(ListCgt listCgt);

  List<ListCgtTimeResponse> toListCgtTimeResponse(List<LocalTime> cgtTimeList);

  ListCgtTimeResponse toListCgtTimeResponse(LocalTime cgtTime);

  @Mapping(target = "modifiedBy", source = "modifiedBy")
  DeleteCgt toDeleteCgt(DeleteCgtRequest request, String modifiedBy);

  @Mapping(target="createdBy", source="loginId")
  CreateReservationCgt toCreateReservationCgt(String loginId, CreateReservationCgtRequest createReservationCgtRequest);

  List<ListReservationCgtResponse> toListReservationCgtResponse(List<GetReservationCgt> getReservationCgts);
  @Mapping(target = "userId" , source = "user.id")
  @Mapping(target = "name" , source = "user.name")
  ListReservationCgtResponse toListReservationCgtResponse(GetReservationCgt getReservationCgt);
}
