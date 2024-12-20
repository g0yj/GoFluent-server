package com.lms.api.admin.controller;

import com.lms.api.admin.controller.dto.reservation.GetReportResponse;
import com.lms.api.admin.controller.dto.reservation.ListReportRequest;
import com.lms.api.admin.controller.dto.reservation.ListReportResponse;
import com.lms.api.admin.controller.dto.reservation.ListSchedulesResponse;
import com.lms.api.admin.controller.dto.reservation.UpdateReportRequest;
import com.lms.api.admin.service.dto.Reservation;
import com.lms.api.admin.service.dto.Teacher;
import com.lms.api.admin.service.dto.reservation.SearchReport;
import com.lms.api.admin.service.dto.reservation.UpdateReport;
import com.lms.api.common.controller.dto.PageResponse;
import com.lms.api.common.mapper.ControllerMapper;
import com.lms.api.common.mapper.ControllerMapperConfig;
import com.lms.api.common.service.dto.Search;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring", config = ControllerMapperConfig.class, uses = {
    ControllerMapper.class})
public interface ReservationAdminControllerMapper {

  /**
   * 학사보고서
   */
  @Mapping(target = "loginId" , source = "loginId")
  SearchReport toSearchReport(String loginId, ListReportRequest listReportRequest);

  @Mapping(target = "list", source = "reservationPage.content")
  @Mapping(target = "totalCount", source = "reservationPage.totalElements")
  PageResponse<ListReportResponse> toListReportResponse(Page<Reservation> reservationPage,
      Search search);

  @Mapping(target = "teacherName", source = "teacher.user.name")
  @Mapping(target = "userName", source = "user.name") //회원명
  @Mapping(target = "courseName", source = "product.name")
  @Mapping(target = "lessonCount", source = "course.lessonCount")
  @Mapping(target = "assignmentCount", source = "course.assignmentCount")
  @Mapping(target = "remainCount", source = "course.remainCount")
  ListReportResponse toListReportResponse(Reservation reservation);

  /**
   * 학사보고서 수정
   */
  UpdateReport toUpdateReport(long reservationId, UpdateReportRequest request, String modifiedBy);

  /**
   * 학사보고서 상세 조회
   */
  @Mapping(target = "userName", source = "user.name")
  @Mapping(target = "courseName", source = "product.name")
  GetReportResponse getReport(Reservation reservation);

  List<ListSchedulesResponse.Teacher> toTeacher(List<Teacher> teachers);

  @Mapping(target = "id", source = "userId")
  ListSchedulesResponse.Teacher toTeacher(Teacher teacher);
}

