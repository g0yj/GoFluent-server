package com.lms.api.admin.controller;

import com.lms.api.admin.controller.dto.reservation.GetReportResponse;
import com.lms.api.admin.controller.dto.reservation.ListReportRequest;
import com.lms.api.admin.controller.dto.reservation.ListReportResponse;
import com.lms.api.admin.controller.dto.reservation.ListSchedulesRequest;
import com.lms.api.admin.controller.dto.reservation.ListSchedulesResponse;
import com.lms.api.admin.controller.dto.reservation.UpdateAttendanceStatusRequest;
import com.lms.api.admin.controller.dto.reservation.UpdateReportRequest;
import com.lms.api.admin.service.ReservationAdminService;
import com.lms.api.admin.service.ScheduleAdminService;
import com.lms.api.admin.service.TeacherAdminService;
import com.lms.api.admin.service.dto.Reservation;
import com.lms.api.admin.service.dto.Schedule;
import com.lms.api.admin.service.dto.Teacher;
import com.lms.api.admin.service.dto.reservation.SearchReport;
import com.lms.api.admin.service.dto.reservation.UpdateReport;
import com.lms.api.common.controller.dto.PageResponse;
import com.lms.api.common.dto.LoginInfo;
import com.lms.api.common.util.ObjectUtils;
import jakarta.validation.Valid;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/v1/reservations")
@RequiredArgsConstructor
public class ReservationAdminController {

  private final ReservationAdminService reservationAdminService;
  private final ReservationAdminControllerMapper reservationAdminControllerMapper;
  private final TeacherAdminService teacherAdminService;
  private final ScheduleAdminService scheduleAdminService;

  /**
   * 학사 보고서 메뉴 출력
   */
  @GetMapping("/report")
  public PageResponse<ListReportResponse> listReport(LoginInfo loginInfo,
      ListReportRequest request) {
    SearchReport searchReport = reservationAdminControllerMapper.toSearchReport(loginInfo.getId(),
        request);
//    Page<Reservation> reportPage = reservationAdminService.listReport(searchReport);
    Page<Reservation> reportPage = reservationAdminService.listReservationReport(searchReport);
    return reservationAdminControllerMapper.toListReportResponse(reportPage, searchReport);
  }

  /**
   * 학사 보고서 수정
   */
  @PutMapping("/report/{reservationId}")
  public void updateReport(LoginInfo loginInfo, @PathVariable long reservationId,
      @RequestBody UpdateReportRequest request) {
    UpdateReport updateReport = reservationAdminControllerMapper.toUpdateReport(reservationId,
        request, loginInfo.getId());
    reservationAdminService.updateReport(updateReport);
  }

  /**
   * 학사 보고서 상세출력 - 등록/수정 시 사용
   */
  @GetMapping("/report/{reservationId}")
  public GetReportResponse getReport(@PathVariable long reservationId) {
    Reservation reservation = reservationAdminService.getReport(reservationId);
    return reservationAdminControllerMapper.getReport(reservation);
  }

  /**
   * 01. 스케줄 조회
   */
  @GetMapping("/schedules")
  public ListSchedulesResponse listSchedules(@Valid ListSchedulesRequest request) {
    // Fetch schedules for the given date range
    List<Schedule> schedules = scheduleAdminService.listSchedules(request.getDate(),
        request.getDate());

//    List<Teacher> teachers = ObjectUtils.isNotEmpty(schedules) ?
//        schedules.stream().map(Schedule::getTeacher).distinct()
//            .sorted(Comparator.comparing(Teacher::getSort)).toList() :
//        teacherAdminService.listTeachers();
    List<Teacher> teachers = teacherAdminService.listTeachers();

    List<ListSchedulesResponse.Schedule> responseSchedules = Stream.iterate(
            LocalTime.of(6, 0), time -> time.isBefore(LocalTime.MAX) && !time.equals(LocalTime.MIN),
            time -> time.plusMinutes(30))
        .map(time -> ListSchedulesResponse.Schedule.builder()
            .time(time)
            // 해당 시간에 스케줄이 있는 강사 목록
            .teachers(teachers.stream()
                .filter(teacher -> schedules.stream()
                    .anyMatch(s -> s.getTeacher().getUserId().equals(teacher.getUserId())
                                   && s.getStartTime().equals(time)))
                .map(teacher -> ListSchedulesResponse.Teacher.builder()
                    .id(teacher.getUserId())
                    .name(teacher.getUser().getName())
                    .build())
                .toList())
            // 해당 시간 예약 목록
            .reservations(teachers.stream()
                .map(teacher -> schedules.stream()
                    .filter(s -> s.getTeacher().getUserId().equals(teacher.getUserId())
                                 && s.getStartTime().equals(time))
                    .flatMap(s -> s.getTeacher().getReservations().stream()
                        .filter(r -> r.getStartTime().equals(time))
                        .map(r -> ListSchedulesResponse.Reservation.builder()
                            .reservationId(r.getId())
                            .teacherId(teacher.getUserId())
                            .teacherName(teacher.getUser().getName())
                            .teacherNameEn(teacher.getUser().getNameEn())
                            .userId(r.getUser().getId())
                            .userName(r.getUser().getName())
                            .userNameEn(r.getUser().getNameEn())
                            .email(r.getUser().getEmail())
                            .textbook(r.getUser().getTextbook())
                            .status(r.getAttendanceStatus())
                            .statusLabel(r.getAttendanceStatus().getLabel())
                            .courseId(r.getCourse() != null ? r.getCourse().getId() : null)
                            .retakeRequired(
                                r.getCourse() != null && r.getCourse().isRetakeRequired())
                            .reported(r.isReported())
                            .build()))
                    .findFirst()
                    .orElse(ListSchedulesResponse.Reservation.builder()
                        .teacherId(teacher.getUserId())
                        .teacherName(teacher.getUser().getName())
                        .build()))
                .toList())
            .build())
        .toList();

    return ListSchedulesResponse.builder()
        .teachers(teachers.stream()
            .map(teacher -> ListSchedulesResponse.Teacher.builder()
                .id(teacher.getUserId())
                .name(teacher.getUser().getName())
                .build())
            .toList())
        .schedules(responseSchedules)
        .build();
  }

  /**
   * 출결 처리 관련해서 수강 이력을 남기기 위한 api
   */
  @PutMapping("/attendanceStatus")
  public void updateAttendanceStatus(LoginInfo loginInfo,
      @Valid @RequestBody UpdateAttendanceStatusRequest request) {
    reservationAdminService.updateAttendanceStatus(loginInfo.getId(), request);
  }

}

