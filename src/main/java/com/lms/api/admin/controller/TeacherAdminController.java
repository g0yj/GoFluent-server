package com.lms.api.admin.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lms.api.admin.controller.dto.CreateTeacherSchedulesRequest;
import com.lms.api.admin.controller.dto.ListTeacherAttendancesDateRequest;
import com.lms.api.admin.controller.dto.ListTeacherAttendancesRequest;
import com.lms.api.admin.controller.dto.ListTeacherAttendancesResponse;
import com.lms.api.admin.controller.dto.ListTeacherSchedulesByWeekRequest;
import com.lms.api.admin.controller.dto.ListTeacherSchedulesByWeekResponse;
import com.lms.api.admin.controller.dto.ListTeacherSchedulesRequest;
import com.lms.api.admin.controller.dto.ListTeacherSchedulesResponse;
import com.lms.api.admin.controller.dto.ListUserSchedulesByWeekResponse;
import com.lms.api.admin.controller.dto.teacher.CreateCgtRequest;
import com.lms.api.admin.controller.dto.teacher.CreateReservationCgtRequest;
import com.lms.api.admin.controller.dto.teacher.CreateTeacherRequest;
import com.lms.api.admin.controller.dto.teacher.CreateTeacherResponse;
import com.lms.api.admin.controller.dto.teacher.DeleteCgtRequest;
import com.lms.api.admin.controller.dto.teacher.DeleteReservationCgtRequest;
import com.lms.api.admin.controller.dto.teacher.GetTeacherResponse;
import com.lms.api.admin.controller.dto.teacher.ListCgtRequest;
import com.lms.api.admin.controller.dto.teacher.ListCgtResponse;
import com.lms.api.admin.controller.dto.teacher.ListReservationCgtRequest;
import com.lms.api.admin.controller.dto.teacher.ListReservationCgtResponse;
import com.lms.api.admin.controller.dto.teacher.ListTeacherRequest;
import com.lms.api.admin.controller.dto.teacher.ListTeacherResponse;
import com.lms.api.admin.controller.dto.teacher.ListWorkedTeachersRequest;
import com.lms.api.admin.controller.dto.teacher.UpdateSortRequest;
import com.lms.api.admin.controller.dto.teacher.UpdateTeacherRequest;
import com.lms.api.admin.service.ReservationAdminService;
import com.lms.api.admin.service.TeacherAdminService;
import com.lms.api.admin.service.UserAdminService;
import com.lms.api.admin.service.dto.CreateSchedules;
import com.lms.api.admin.service.dto.Login;
import com.lms.api.admin.service.dto.ReservationAttendance;
import com.lms.api.admin.service.dto.Schedule;
import com.lms.api.admin.service.dto.Teacher;
import com.lms.api.admin.service.dto.teacher.CreateCgt;
import com.lms.api.admin.service.dto.teacher.CreateReservationCgt;
import com.lms.api.admin.service.dto.teacher.CreateTeacher;
import com.lms.api.admin.service.dto.teacher.DeleteCgt;
import com.lms.api.admin.service.dto.teacher.GetReservationCgt;
import com.lms.api.admin.service.dto.teacher.GetTeacher;
import com.lms.api.admin.service.dto.teacher.ListCgt;
import com.lms.api.admin.service.dto.teacher.ListWorkedTeachers;
import com.lms.api.admin.service.dto.teacher.SearchListCgt;
import com.lms.api.admin.service.dto.teacher.SearchListTeachers;
import com.lms.api.admin.service.dto.teacher.SearchTeacherResponse;
import com.lms.api.admin.service.dto.teacher.UpdateTeacher;
import com.lms.api.common.code.AttendanceStatus;
import com.lms.api.common.code.TeacherType;
import com.lms.api.common.controller.dto.PageResponse;
import com.lms.api.common.dto.LoginInfo;
import com.lms.api.common.util.DateUtils;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/admin/v1/teachers")
@RequiredArgsConstructor
public class TeacherAdminController {

  private final TeacherAdminService teacherAdminService;
  private final ReservationAdminService reservationAdminService;
  private final TeacherAdminControllerMapper teacherAdminControllerMapper;
  private final UserAdminControllerMapper userAdminControllerMapper;
  private final UserAdminService userAdminService;

  /**
   * 05. 강사 스케줄 조회
   */
  @GetMapping("/{id}/schedules")
  public ListTeacherSchedulesResponse listSchedules(@PathVariable String id,
      @Valid ListTeacherSchedulesRequest request) {
    // 주어진 강사 ID와 날짜 범위로 스케줄 목록을 조회
    List<Schedule> schedules = teacherAdminService.listSchedules(id, request.getDateFrom(),
        request.getDateTo());

    // 30분 단위로 스케줄 생성
    List<ListTeacherSchedulesResponse.Schedule> responseSchedules = Stream.iterate(
            request.getDateFrom(), date -> date.isBefore(request.getDateTo().plusDays(1)),
            date -> date.plusDays(1))
        .flatMap(date -> Stream.iterate(LocalTime.of(6, 0),
                time -> time.isBefore(LocalTime.MAX) && !time.equals(LocalTime.MIN),
                time -> time.plusMinutes(30))
            .map(time -> ListTeacherSchedulesResponse.Schedule.builder()
                .date(date)
                .time(time)
                // 스케줄 목록에 해당 날짜와 시간이 있으면 scheduled를 true
                .scheduled(schedules.stream()
                    .anyMatch(s -> s.getDate().equals(date) && s.getStartTime().equals(time)))
                .build()))
        .toList();

    return ListTeacherSchedulesResponse.builder().schedules(responseSchedules).build();
  }

  /**
   * 05. 강사 스케줄 조회
   */
  @GetMapping("/{id}/schedules/by-week")
  public ListTeacherSchedulesResponse listSchedulesByWeek(@PathVariable String id,
      @Valid ListTeacherSchedulesByWeekRequest request) {
    LocalDate[] localDates = DateUtils.getWeekStartEnd(request.getDateMonth().getYear(),
        request.getDateMonth().getMonthValue(), request.getWeek());

    ListTeacherSchedulesRequest byDateRequest = ListTeacherSchedulesRequest.builder()
        .dateFrom(localDates[0])
        .dateTo(localDates[1])
        .build();

    return new ListTeacherSchedulesByWeekResponse(
        listSchedules(id, byDateRequest),
        DateUtils.getWeekCount(request.getDateMonth().toString()));
  }

  @PostMapping("/{id}/schedules")
  public void createSchedules(LoginInfo loginInfo, @PathVariable String id,
      @RequestBody @Valid CreateTeacherSchedulesRequest request) {
    CreateSchedules createSchedules = teacherAdminControllerMapper.toCreateSchedules(request, id,
        loginInfo.getId());

    teacherAdminService.createSchedules(createSchedules);
  }

  /**
   * 출석율/결석율(월)
   */
  @GetMapping("/attendances")
  public ListTeacherAttendancesResponse listAttendancesMonth(
      @Valid ListTeacherAttendancesRequest request) {
    LocalDate startDate = request.getYearMonth().atDay(1);
    AttendanceStatus status = request.getStatus();
    log.debug("start date: {}, status: {}", startDate, status);

    return reservationAdminService.getTeacherAttendances(DateUtils.getString(startDate),
        status.name());
  }

  /**
   * 출석율/결석율(일)
   */
  @GetMapping("/attendances/by-date")
  public ListTeacherAttendancesResponse listAttendancesDate(
      @Valid ListTeacherAttendancesDateRequest request) {
    LocalDate firstDayOfMonth = request.getYearMonthDay().withDayOfMonth(1);
    String startDate = DateUtils.getString(firstDayOfMonth);
    String endDate = DateUtils.getString(request.getYearMonthDay());
    AttendanceStatus status = request.getStatus();
    log.debug("start date: {}, status: {}", startDate, status);

    return reservationAdminService.getTeacherAttendances(startDate, endDate, status.name());
  }

  //  @GetMapping("/attendances")
  public ListTeacherAttendancesResponse listAttendances(
      @Valid ListTeacherAttendancesRequest request) {
    LocalDate startDate = request.getYearMonth().atDay(1);
    LocalDate endDate = request.getYearMonth().atEndOfMonth();

    List<Teacher> teachers = teacherAdminService.listTeachers();
    List<ReservationAttendance> htAttendances = reservationAdminService.listAttendances(startDate,
        endDate, request.getStatus(), TeacherType.HT);
    List<ReservationAttendance> ltAttendances = reservationAdminService.listAttendances(startDate,
        endDate, request.getStatus(), TeacherType.LT);
    List<ListTeacherAttendancesResponse.Schedule> schedules = new ArrayList<>();
    List<ListTeacherAttendancesResponse.Attendance> totalAttendances = new ArrayList<>();

    for (LocalDate date = startDate; date.isBefore(endDate.plusDays(1)); date = date.plusDays(1)) {
      // HT
      List<ListTeacherAttendancesResponse.Attendance> attendances = getAttendances(date, teachers,
          TeacherType.HT, htAttendances);

      // LT
      attendances.addAll(getAttendances(date, teachers, TeacherType.LT, ltAttendances));

      for (ListTeacherAttendancesResponse.Attendance attendance : attendances) {
        totalAttendances.stream().filter(a -> a.getName().equals(attendance.getName()))
            .findFirst()
            .ifPresentOrElse(a -> {
              a.setReservationCount(a.getReservationCount() + attendance.getReservationCount());
              a.setAttendanceCount(a.getAttendanceCount() + attendance.getAttendanceCount());
            }, () -> totalAttendances.add(ListTeacherAttendancesResponse.Attendance.builder()
                .name(attendance.getName())
                .attendanceCount(attendance.getAttendanceCount())
                .reservationCount(attendance.getReservationCount())
                .build()));
      }

      schedules.add(ListTeacherAttendancesResponse.Schedule.builder()
          .date(date)
          .attendances(attendances)
          .build());
    }

    return ListTeacherAttendancesResponse.builder()
        .schedules(schedules)
        .totalAttendances(totalAttendances)
        .build();
  }

  private List<ListTeacherAttendancesResponse.Attendance> getAttendances(LocalDate date,
      List<Teacher> teachers, TeacherType teacherType,
      List<ReservationAttendance> reservationAttendances) {
    List<ListTeacherAttendancesResponse.Attendance> attendances = teachers.stream()
        .filter(teacher -> teacher.getType() == teacherType)
        .map(teacher -> reservationAttendances.stream()
            .filter(a -> a.getDate().equals(date) && a.getTeacherId().equals(teacher.getUserId()))
            .findFirst()
            .map(a -> ListTeacherAttendancesResponse.Attendance.builder()
                .name(teacher.getUser().getName())
                .reservationCount(a.getReservationCount())
                .attendanceCount(a.getAttendanceCount())
                .build())
            .orElse(ListTeacherAttendancesResponse.Attendance.builder()
                .name(teacher.getUser().getName())
                .build()))
        .collect(Collectors.toList());

    attendances.add(ListTeacherAttendancesResponse.Attendance.builder()
        .name(teacherType.name() + " Avg.")
        .reservationCount(attendances.stream()
            .mapToLong(ListTeacherAttendancesResponse.Attendance::getReservationCount).sum())
        .attendanceCount(attendances.stream()
            .mapToLong(ListTeacherAttendancesResponse.Attendance::getAttendanceCount).sum())
        .build());

    return attendances;
  }

  /**
   * 강사 목록
   */
  @GetMapping
  public PageResponse<ListTeacherResponse> listTeachers(ListTeacherRequest request) {
    SearchListTeachers searchListTeachers = teacherAdminControllerMapper.toSearchTeachers(request);
    Page<SearchTeacherResponse> teacherPage = teacherAdminService.toListTeachers(
        searchListTeachers);
    return teacherAdminControllerMapper.toListTeacherRepsonse(teacherPage, searchListTeachers);
  }

  /**
   * 강사 상세 출력
   */
  @GetMapping("/{id}")
  public GetTeacherResponse getTeacher(@PathVariable String id) {
    GetTeacher teacher = teacherAdminService.getTeacher(id);
    return teacherAdminControllerMapper.toGetTeacherResponse(teacher);
  }

  /**
   * 강사 등록
   */
  @PostMapping
  public CreateTeacherResponse createTeacher(LoginInfo loginInfo, CreateTeacherRequest request) {

    CreateTeacher createTeacher = teacherAdminControllerMapper.toCreateTeacher(request,
        loginInfo.getId());
    String id = teacherAdminService.createTeacher(createTeacher);

    return CreateTeacherResponse.builder().id(id).build();
  }


  /**
   * 수정 / 삭제 삭제 : 강사 등록 시 회원으로도 등록되기 때문에 강사 삭제는 active를 fasle로 바꾸기만
   */
  @PutMapping("/{id}")
  public void updateTeacher(LoginInfo loginInfo, @PathVariable String id,
      UpdateTeacherRequest updateTeacherRequest) {
    UpdateTeacher updateTeacher = teacherAdminControllerMapper.toUpdateTeacher(id,
        updateTeacherRequest, loginInfo.getId());
    teacherAdminService.updateTeacher(updateTeacher);
  }

  /**
   * 삭제
   */
  @DeleteMapping("/{id}")
  public void deleteTeacher(
      LoginInfo loginInfo, @PathVariable String id) {
    teacherAdminService.deleteTeacher(id, loginInfo.getId());
  }

  /**
   * 강사 목록에서 순서 변경 api
   */
  @PutMapping("/sort")
  public void updateSortTeachers(@Valid @RequestBody UpdateSortRequest request) {
    teacherAdminService.updateSortTeachers(request);
  }

  /**
   * CGT 등록 가능한 시간 조회
   */
  @GetMapping("/cgttimes")
//  public List<ListCgtTimeResponse> listCgtTimes(
  public List<String> listCgtTimes(
      @RequestParam("date") @NotNull @JsonFormat(pattern = "yyyy-MM-dd") LocalDate date,
      @RequestParam("teacherId") @NotNull String teacherId) {
    List<LocalTime> cgtTimes = teacherAdminService.listCgtTimes(date, teacherId);
    return cgtTimes.stream()
        .map(time -> time.format(DateTimeFormatter.ofPattern("HH:mm")))
        .collect(Collectors.toList());
//    return teacherAdminControllerMapper.toListCgtTimeResponse(cgtTimes);
  }

  /**
   * CGT 스케쥴 등록
   */
  @PutMapping("/cgt")
  public void createCgt(LoginInfo loginInfo, @RequestBody CreateCgtRequest request) {
    CreateCgt createCgt = teacherAdminControllerMapper.toCreateCgt(request, loginInfo.getId());
    teacherAdminService.createCgt(createCgt);
  }

  /**
   * CGT 스케쥴 목록 조회
   */
  @GetMapping("/cgt")
  public PageResponse<ListCgtResponse> listCgts(@Valid ListCgtRequest request) {
    SearchListCgt searchCgts = teacherAdminControllerMapper.toSearchCgt(request);
    Page<ListCgt> cgtPage = teacherAdminService.listCgts(searchCgts);
    return teacherAdminControllerMapper.toListCgtResponse(cgtPage, searchCgts);
  }

  /**
   * CGT 스케줄 삭제 > 스케줄 식별키 배열을  돌려 놓기 (type:COURSE, limit: 1, cgtTime: null)
   */
  @PutMapping("/deletecgt")
  public void deleteCgt(Login login, @RequestBody DeleteCgtRequest request) {
    DeleteCgt deleteCgt = teacherAdminControllerMapper.toDeleteCgt(request, login.getId());
    teacherAdminService.deleteCgt(deleteCgt);
  }

  /**
   * CGT 추가 수강생 예약
   *
   * @Request: 목록에서 전달 받은 teacherId, schedules(date,startTime) /  userId
   */
  @PostMapping("/reservationCgt")
  public void createReservationCgt(LoginInfo loginInfo,
      @RequestBody CreateReservationCgtRequest request) {
    CreateReservationCgt createReservationCgt = teacherAdminControllerMapper.toCreateReservationCgt(
        loginInfo.getId(), request);
    teacherAdminService.createReservationCgt(createReservationCgt);
  }

  /**
   * 기예약자 선택 예약 취소
   *
   * @Request: 목록에서 전달 받은 userId, scheduleId
   */
  @DeleteMapping("/reservationCgt")
  public void deleteReservationCgt(@RequestBody DeleteReservationCgtRequest request) {
    teacherAdminService.deleteReservationCgt(request);
  }

  /**
   * 운영자 CGT 예약자 리스트
   *
   * @Request: scheduleId
   * @Response: userId, name, startTime, endTime, date
   */
  @PostMapping("/reservationCgtList")
  public List<ListReservationCgtResponse> listReservationCgt(
      @RequestBody ListReservationCgtRequest request) {
    List<GetReservationCgt> reservationCgts = teacherAdminService.listReservationCgt(request);
    return teacherAdminControllerMapper.toListReservationCgtResponse(reservationCgts);
  }

  /**
   * 해당 기간 일했던 강사 목록 조회
   *
   * @Param: dateFrom, dateTo
   */
  @GetMapping("/worked")
  public List<ListWorkedTeachers> listWorkedTeachers(@Valid ListWorkedTeachersRequest request) {
    return teacherAdminService.listWorkedTeachers(request);
  }
}
