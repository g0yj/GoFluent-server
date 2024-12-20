package com.lms.api.admin.controller;

import com.lms.api.admin.code.UserSchedulesCode.ReservationStatus;
import com.lms.api.admin.controller.dto.CreateUserConsultationRequest;
import com.lms.api.admin.controller.dto.CreateUserLdfRequest;
import com.lms.api.admin.controller.dto.CreateUserLevelTestRequest;
import com.lms.api.admin.controller.dto.CreateUserNoteRequest;
import com.lms.api.admin.controller.dto.CreateUserOrderPaymentsRequest;
import com.lms.api.admin.controller.dto.CreateUserOrderProductRequest;
import com.lms.api.admin.controller.dto.CreateUserOrderProductResponse;
import com.lms.api.admin.controller.dto.CreateUserOrderRefundRequest;
import com.lms.api.admin.controller.dto.CreateUserRequest;
import com.lms.api.admin.controller.dto.CreateUserReservationsRequest;
import com.lms.api.admin.controller.dto.GetUserConsultationResponse;
import com.lms.api.admin.controller.dto.GetUserCourseResponse;
import com.lms.api.admin.controller.dto.GetUserLdfResponse;
import com.lms.api.admin.controller.dto.GetUserLevelTestResponse;
import com.lms.api.admin.controller.dto.GetUserNoteResponse;
import com.lms.api.admin.controller.dto.GetUserOrderResponse;
import com.lms.api.admin.controller.dto.GetUserReservationResponse;
import com.lms.api.admin.controller.dto.GetUserResponse;
import com.lms.api.admin.controller.dto.ListUserConsultationsResponse;
import com.lms.api.admin.controller.dto.ListUserCourseHistoriesResponse;
import com.lms.api.admin.controller.dto.ListUserCoursesRequest;
import com.lms.api.admin.controller.dto.ListUserCoursesResponse;
import com.lms.api.admin.controller.dto.ListUserLdfsRequest;
import com.lms.api.admin.controller.dto.ListUserLdfsResponse;
import com.lms.api.admin.controller.dto.ListUserLevelTestsResponse;
import com.lms.api.admin.controller.dto.ListUserNotesRequest;
import com.lms.api.admin.controller.dto.ListUserNotesResponse;
import com.lms.api.admin.controller.dto.ListUserNotesResponse1;
import com.lms.api.admin.controller.dto.ListUserOrderPaymentsResponse;
import com.lms.api.admin.controller.dto.ListUserOrdersResponse;
import com.lms.api.admin.controller.dto.ListUserReservationsRequest;
import com.lms.api.admin.controller.dto.ListUserReservationsResponse;
import com.lms.api.admin.controller.dto.ListUserSchedulesByDateRequest;
import com.lms.api.admin.controller.dto.ListUserSchedulesByDateResponse;
import com.lms.api.admin.controller.dto.ListUserSchedulesByTimeRequest;
import com.lms.api.admin.controller.dto.ListUserSchedulesByTimeResponse;
import com.lms.api.admin.controller.dto.ListUserSchedulesByWeekRequest;
import com.lms.api.admin.controller.dto.ListUserSchedulesByWeekResponse;
import com.lms.api.admin.controller.dto.ListUsersRequest;
import com.lms.api.admin.controller.dto.ListUsersResponse;
import com.lms.api.admin.controller.dto.UpdateLdfRequest;
import com.lms.api.admin.controller.dto.UpdateUserConsultationRequest;
import com.lms.api.admin.controller.dto.UpdateUserCourseRequest;
import com.lms.api.admin.controller.dto.UpdateUserLevelTestRequest;
import com.lms.api.admin.controller.dto.UpdateUserNoteRequest;
import com.lms.api.admin.controller.dto.UpdateUserOrderPaymentsRequest;
import com.lms.api.admin.controller.dto.UpdateUserRequest;
import com.lms.api.admin.controller.dto.UpdateUserReservationRequest;
import com.lms.api.admin.controller.dto.UpdateUserReservationsRequest;
import com.lms.api.admin.controller.dto.order.GetUserOrderPaymentResponse;
import com.lms.api.admin.service.ScheduleAdminService;
import com.lms.api.admin.service.TeacherAdminService;
import com.lms.api.admin.service.UserAdminService;
import com.lms.api.admin.service.dto.Course;
import com.lms.api.admin.service.dto.CourseHistory;
import com.lms.api.admin.service.dto.CreateMemberConsultation;
import com.lms.api.admin.service.dto.CreateNote;
import com.lms.api.admin.service.dto.CreateOrderPayments;
import com.lms.api.admin.service.dto.CreateOrderProduct;
import com.lms.api.admin.service.dto.CreateOrderRefund;
import com.lms.api.admin.service.dto.CreateReservations;
import com.lms.api.admin.service.dto.CreateUser;
import com.lms.api.admin.service.dto.CreateUserLdf;
import com.lms.api.admin.service.dto.CreateUserLevelTest;
import com.lms.api.admin.service.dto.Ldf;
import com.lms.api.admin.service.dto.LdfDto;
import com.lms.api.admin.service.dto.LevelTest;
import com.lms.api.admin.service.dto.MemberConsultation;
import com.lms.api.admin.service.dto.Note;
import com.lms.api.admin.service.dto.Order;
import com.lms.api.admin.service.dto.Payment;
import com.lms.api.admin.service.dto.Reservation;
import com.lms.api.admin.service.dto.Schedule;
import com.lms.api.admin.service.dto.SearchCourseHistories;
import com.lms.api.admin.service.dto.SearchUserCourses;
import com.lms.api.admin.service.dto.SearchUserLdfs;
import com.lms.api.admin.service.dto.SearchUserReservations;
import com.lms.api.admin.service.dto.SearchUsers;
import com.lms.api.admin.service.dto.Teacher;
import com.lms.api.admin.service.dto.UpdateCourse;
import com.lms.api.admin.service.dto.UpdateLdf;
import com.lms.api.admin.service.dto.UpdateMemberConsultation;
import com.lms.api.admin.service.dto.UpdateNote;
import com.lms.api.admin.service.dto.UpdateOrderPayment;
import com.lms.api.admin.service.dto.UpdateReservation;
import com.lms.api.admin.service.dto.UpdateReservations;
import com.lms.api.admin.service.dto.UpdateUser;
import com.lms.api.admin.service.dto.UpdateUserLevelTest;
import com.lms.api.admin.service.dto.User;
import com.lms.api.admin.service.dto.UserDto;
import com.lms.api.common.code.CourseHistoryModule;
import com.lms.api.common.controller.dto.PageRequest;
import com.lms.api.common.controller.dto.PageResponse;
import com.lms.api.common.dto.LoginInfo;
import com.lms.api.common.util.DateUtils;
import com.lms.api.common.util.ObjectUtils;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
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
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/admin/v1/users")
@RequiredArgsConstructor
public class UserAdminController {

  private final UserAdminService userAdminService;
  private final TeacherAdminService teacherAdminService;
  private final ScheduleAdminService scheduleAdminService;
  private final UserAdminControllerMapper userAdminControllerMapper;

  /*
    @GetMapping
    public PageResponse<ListUsersResponse> listUsers1(ListUsersRequest request) {
      SearchUsers searchUsers = userAdminControllerMapper.toSearchUsers(request);
      Page<User> userPage = userAdminService.listUsers(searchUsers);
      return userAdminControllerMapper.toListUsersResponse(userPage, searchUsers);
    }
  */

  /**
   * 회원 목록
   */
  @GetMapping
  public PageResponse<ListUsersResponse> listUsers(ListUsersRequest request) {
    SearchUsers searchUsers = userAdminControllerMapper.toSearchUsers(request);

    Page<UserDto> userPage = userAdminService.listUsers(searchUsers);

    return userAdminControllerMapper.toListUsersResponse(userPage, searchUsers);
  }

  /**
   * 회원 조회
   */
  @GetMapping("/{id}")
  public GetUserResponse getUser(@PathVariable String id) {
    User user = userAdminService.getUser(id);
    return userAdminControllerMapper.toGetUserResponse(user);
  }

  /**
   * 회원 등록
   */
  @PostMapping
  public void createUser(LoginInfo loginInfo, @RequestBody @Valid CreateUserRequest request) {
    log.info("## 회원 등록 ##");
    CreateUser createUser = userAdminControllerMapper.toCreateUser(request, loginInfo.getId());

    userAdminService.createUser(createUser);
  }

  /**
   * 회원 수정
   */
  @PutMapping("/{id}")
  public void updateUser(
      LoginInfo loginInfo, @PathVariable String id, @RequestBody @Valid UpdateUserRequest request) {
    UpdateUser updateUser = userAdminControllerMapper.toUpdateUser(id, request, loginInfo.getId());

    userAdminService.updateUser(updateUser);
  }

  /**
   * 회원 삭제
   */
  @DeleteMapping("/{id}")
  public void deleteUser(@PathVariable String id) {
    userAdminService.deleteUser(id);
  }

  /**
   * 04. 회원 과정(수강) 목록 조회
   */
  @GetMapping("/{id}/courses")
  public PageResponse<ListUserCoursesResponse> listCourses(
      @PathVariable String id, ListUserCoursesRequest request) {
    SearchUserCourses searchUserCourses =
        userAdminControllerMapper.toSearchUserCourses(request, id);

    Page<Course> coursePage = userAdminService.listCourses(searchUserCourses);

    return userAdminControllerMapper.toListUserCoursesResponse(coursePage, searchUserCourses);
  }

  /**
   * 회원 예약 목록 조회
   */
  @GetMapping("/{id}/reservations")
  public PageResponse<ListUserReservationsResponse> listReservations(
      @PathVariable String id, ListUserReservationsRequest request) {
    SearchUserReservations searchUserReservations =
        userAdminControllerMapper.toSearchUserReservations(request, id);

    Page<Reservation> reservationPage = userAdminService.listReservations(searchUserReservations);

    return userAdminControllerMapper.toListUserReservationsResponse(
        reservationPage, searchUserReservations);
  }

  /**
   * 회원 예약 조회
   */
  @GetMapping("/{id}/reservations/{reservationId}")
  public GetUserReservationResponse getReservation(
      @PathVariable String id, @PathVariable long reservationId) {
    Reservation reservation = userAdminService.getReservation(id, reservationId);

    return userAdminControllerMapper.toGetUserReservationResponse(reservation);
  }

  /**
   * 회원 예약 수정(취소)
   */
  @PutMapping("/{id}/reservations")
  public void updateReservations(
      LoginInfo loginInfo,
      @PathVariable String id,
      @RequestBody @Valid UpdateUserReservationsRequest request) {
    UpdateReservations updateReservations =
        userAdminControllerMapper.toUpdateReservations(request, id, loginInfo.getId());

    userAdminService.updateReservations(updateReservations);
  }

  /**
   * 회원 예약 수정 (학사보고서)
   */
  @PutMapping("/{id}/reservations/{reservationId}")
  public void updateReservation(
      LoginInfo loginInfo,
      @PathVariable String id,
      @PathVariable long reservationId,
      @RequestBody @Valid UpdateUserReservationRequest request) {
    UpdateReservation updateReservation =
        userAdminControllerMapper.toUpdateReservation(
            reservationId, request, id, loginInfo.getId());

    userAdminService.updateReservation(updateReservation);
  }

  // 페이징 처리한 로직
  @GetMapping("/{id}/notes1")
  public PageResponse<ListUserNotesResponse1> listNotes1(
      @PathVariable String id, PageRequest request) {
    SearchCourseHistories searchCourseHistories =
        userAdminControllerMapper.toSearchCourseHistories(
            request, id, CourseHistoryModule.ASSIGN, null);

    Page<CourseHistory> courseHistoryPage =
        userAdminService.listCourseHistories(searchCourseHistories);

    return userAdminControllerMapper.toListUserNotesResponse(
        courseHistoryPage, searchCourseHistories);
  }

  @GetMapping("/{id}/notes")
  public List<ListUserNotesResponse> listNotes(
      @PathVariable String id, ListUserNotesRequest request) {
    Note note = userAdminControllerMapper.toNote(id, request, CourseHistoryModule.ASSIGN, null);
    List<Note> notes = userAdminService.listNotes(note);
    return userAdminControllerMapper.toListNoteResponse(notes);
  }

  @PostMapping("/{id}/notes")
  public void createNotes(
      LoginInfo loginInfo,
      @PathVariable String id,
      @RequestBody @Valid CreateUserNoteRequest request) {
    CreateNote createCourseHistory =
        userAdminControllerMapper.toCreateNote(
            request, id, CourseHistoryModule.ASSIGN, loginInfo.getId());
    userAdminService.createNote(createCourseHistory);
  }

  @GetMapping("/{id}/notes/{noteId}")
  public GetUserNoteResponse getNote(@PathVariable String id, @PathVariable long noteId) {
    Note note = userAdminService.getNote(id, noteId);
    return userAdminControllerMapper.toGetNoteResponse(note);
  }

  @PutMapping("/notes/{id}")
  public void updateNote(
      LoginInfo loginInfo, @PathVariable long id, @RequestBody UpdateUserNoteRequest request) {
    UpdateNote updateNote = userAdminControllerMapper.toUpdateNote(request, id, loginInfo.getId());
    userAdminService.updateNote(updateNote);
  }

  @DeleteMapping("/notes/{id}")
  public void deleteNote(@PathVariable long id) {
    userAdminService.deleteNote(id);
  }

  @GetMapping("/{id}/schedules/by-time")
  public ListUserSchedulesByTimeResponse listSchedulesByTime(
      @PathVariable String id, @Valid ListUserSchedulesByTimeRequest request) {
    List<Schedule> schedules =
        scheduleAdminService.listSchedules(request.getDate(), request.getDate());
    List<Teacher> teachers = teacherAdminService.listTeachers();

    List<ListUserSchedulesByTimeResponse.Schedule> schedulesByTime =
        Stream.iterate(
                LocalTime.of(6, 0),
                time -> time.isBefore(LocalTime.MAX) && !time.equals(LocalTime.MIN),
                time -> time.plusMinutes(30))
            .map(
                time ->
                    ListUserSchedulesByTimeResponse.Schedule.builder()
                        .time(time)
                        .reservations(
                            teachers.stream()
                                .map(
                                    teacher -> {
                                      Optional<Schedule> schedule =
                                          schedules.stream()
                                              .filter(
                                                  s ->
                                                      s.getTeacher()
                                                          .getUserId()
                                                          .equals(teacher.getUserId())
                                                      && s.getStartTime().equals(time))
                                              .findFirst();

                                      return ListUserSchedulesByTimeResponse.Reservation.builder()
                                          .scheduleId(schedule.map(Schedule::getId).orElse(null))
                                          .teacherId(teacher.getUserId())
                                          .teacherName(teacher.getUser().getName())
                                          .status(
                                              schedule
                                                  .map(
                                                      s ->
                                                          s.getTeacher().getReservations().stream()
                                                              .filter(
                                                                  reservation ->
                                                                      reservation
                                                                          .getStartTime()
                                                                          .equals(time))
                                                              .findFirst()
                                                              .map(
                                                                  reservation ->
                                                                      reservation
                                                                          .getUser()
                                                                          .getId()
                                                                          .equals(id)
                                                                          ? ReservationStatus.USERS
                                                                          : ReservationStatus.FULL)
                                                              .orElse(ReservationStatus.AVAILABLE))
                                                  .orElse(ReservationStatus.NONE))
                                          .build();
                                    })
                                .toList())
                        .build())
            .toList();

    return ListUserSchedulesByTimeResponse.builder().schedules(schedulesByTime).build();
  }

  /**
   * 예약 스케쥴 조회(주차)
   */
  @GetMapping("/{id}/schedules/by-week")
  public ListUserSchedulesByDateResponse listSchedulesByWeek(
      @PathVariable String id, @Valid ListUserSchedulesByWeekRequest request) {

    LocalDate[] localDates = DateUtils.getWeekStartEnd(request.getDateMonth().getYear(),
        request.getDateMonth().getMonthValue(), request.getWeek());

    ListUserSchedulesByDateRequest byDateRequest = ListUserSchedulesByDateRequest.builder()
        .dateFrom(localDates[0])
        .dateTo(localDates[1])
        .teacherId(request.getTeacherId())
        .assistantTeacherId(request.getAssistantTeacherId())
        .build();

    return new ListUserSchedulesByWeekResponse(
        listSchedulesByDate(id, byDateRequest),
        DateUtils.getWeekCount(request.getDateMonth().toString()));
  }

  @GetMapping("/{id}/schedules/by-date")
  public ListUserSchedulesByDateResponse listSchedulesByDate(
      @PathVariable String id, @Valid ListUserSchedulesByDateRequest request) {
    List<Schedule> schedules =
        scheduleAdminService.listSchedules(request.getDateFrom(), request.getDateTo());

    List<ListUserSchedulesByDateResponse.Schedule> schedulesByDate =
        Stream.iterate(
                LocalTime.of(6, 0),
                time -> time.isBefore(LocalTime.MAX) && !time.equals(LocalTime.MIN),
                time -> time.plusMinutes(30))
            .map(
                time ->
                    ListUserSchedulesByDateResponse.Schedule.builder()
                        .time(time)
                        .reservations(
                            Stream.iterate(
                                    request.getDateFrom(),
                                    date -> date.isBefore(request.getDateTo().plusDays(1)),
                                    date -> date.plusDays(1))
                                .map(
                                    date -> {
                                      List<Schedule> targetSchedules =
                                          schedules.stream()
                                              .filter(
                                                  schedule ->
                                                      schedule.getDate().equals(date)
                                                      && schedule.getStartTime().equals(time))
                                              .toList();

                                      List<Teacher> teachers =
                                          targetSchedules.stream()
                                              .map(Schedule::getTeacher)
                                              .toList();

                                      return ListUserSchedulesByDateResponse.Reservation.builder()
                                          .date(date)
                                          .teacherScheduleId(
                                              targetSchedules.stream()
                                                  .filter(
                                                      schedule ->
                                                          schedule
                                                              .getTeacher()
                                                              .getUserId()
                                                              .equals(request.getTeacherId()))
                                                  .findFirst()
                                                  .map(Schedule::getId)
                                                  .orElse(null))
                                          .teacherStatus(
                                              teachers.stream()
                                                  .filter(
                                                      teacher ->
                                                          teacher
                                                              .getUserId()
                                                              .equals(request.getTeacherId()))
                                                  .findFirst()
                                                  .map(
                                                      teacher ->
                                                          teacher.getReservations().stream()
                                                              .filter(
                                                                  reservation ->
                                                                      reservation
                                                                          .getDate()
                                                                          .equals(date)
                                                                      && reservation
                                                                          .getStartTime()
                                                                          .equals(time))
                                                              .findFirst()
                                                              .map(
                                                                  reservation ->
                                                                      reservation
                                                                          .getUser()
                                                                          .getId()
                                                                          .equals(id)
                                                                          ? ReservationStatus.USERS
                                                                          : ReservationStatus.FULL)
                                                              .orElse(ReservationStatus.AVAILABLE))
                                                  .orElse(ReservationStatus.NONE))
                                          .assistantTeacherScheduleId(
                                              targetSchedules.stream()
                                                  .filter(
                                                      schedule ->
                                                          schedule
                                                              .getTeacher()
                                                              .getUserId()
                                                              .equals(
                                                                  request.getAssistantTeacherId()))
                                                  .findFirst()
                                                  .map(Schedule::getId)
                                                  .orElse(null))
                                          .assistantTeacherStatus(
                                              teachers.stream()
                                                  .filter(
                                                      teacher ->
                                                          teacher
                                                              .getUserId()
                                                              .equals(
                                                                  request.getAssistantTeacherId()))
                                                  .findFirst()
                                                  .map(
                                                      teacher ->
                                                          teacher.getReservations().stream()
                                                              .filter(
                                                                  reservation ->
                                                                      reservation
                                                                          .getDate()
                                                                          .equals(date)
                                                                      && reservation
                                                                          .getStartTime()
                                                                          .equals(time))
                                                              .findFirst()
                                                              .map(
                                                                  reservation ->
                                                                      reservation
                                                                          .getUser()
                                                                          .getId()
                                                                          .equals(id)
                                                                          ? ReservationStatus.USERS
                                                                          : ReservationStatus.FULL)
                                                              .orElse(ReservationStatus.AVAILABLE))
                                                  .orElse(ReservationStatus.NONE))
                                          .teachers(
                                              teachers.stream()
                                                  .filter(
                                                      teacher ->
                                                          teacher.getReservations().stream()
                                                              .noneMatch(
                                                                  reservation ->
                                                                      reservation
                                                                          .getDate()
                                                                          .equals(date)
                                                                      && reservation
                                                                          .getStartTime()
                                                                          .equals(time)))
                                                  .map(
                                                      teacher ->
                                                          ListUserSchedulesByDateResponse.Teacher
                                                              .builder()
                                                              .id(teacher.getUserId())
                                                              .name(teacher.getUser().getName())
                                                              .build())
                                                  .toList())
                                          .build();
                                    })
                                .toList())
                        .build())
            .toList();

    return ListUserSchedulesByDateResponse.builder().schedules(schedulesByDate).build();
  }

  /**
   * 09. 예약등록
   */
  @PostMapping("/{id}/reservations")
  public void createReservations(
      LoginInfo loginInfo,
      @PathVariable String id,
      @RequestBody @Valid CreateUserReservationsRequest request) {
    CreateReservations createReservations =
        userAdminControllerMapper.toCreateReservations(request, id, loginInfo.getId());

    userAdminService.createReservations(createReservations);
  }

  /**
   * 11.회원 수강 조회
   */
  @GetMapping("/{id}/courses/{courseId}")
  public GetUserCourseResponse getCourse(@PathVariable String id, @PathVariable long courseId) {
    Course course = userAdminService.getCourse(id, courseId);

    return userAdminControllerMapper.toGetUserCourseResponse(course);
  }

  /**
   * 12. 회원 수강 수정
   */
  @PutMapping("/{id}/courses/{courseId}")
  public void updateCourse(
      LoginInfo loginInfo,
      @PathVariable String id,
      @PathVariable long courseId,
      @RequestBody @Valid UpdateUserCourseRequest request) {
    UpdateCourse updateCourse =
        userAdminControllerMapper.toUpdateCourse(courseId, request, id, loginInfo.getId());

    userAdminService.updateCourse(updateCourse);
  }

  @GetMapping("/{id}/courses/{courseId}/histories")
  public PageResponse<ListUserCourseHistoriesResponse> listCourseHistories(
      @PathVariable String id, @PathVariable long courseId, PageRequest request) {
    SearchCourseHistories searchCourseHistories =
        userAdminControllerMapper.toSearchCourseHistories(
            request, courseId, CourseHistoryModule.COURSE_USER);

    Page<CourseHistory> courseHistoryPage =
        userAdminService.listCourseHistories(searchCourseHistories);

    return userAdminControllerMapper.toListUserCourseHistoriesResponse(
        courseHistoryPage, searchCourseHistories);
  }

  @GetMapping("/{id}/consultations")
  public ListUserConsultationsResponse listConsultations(@PathVariable String id) {
    List<MemberConsultation> memberConsultations = userAdminService.listMemberConsultations(id);

    return ListUserConsultationsResponse.builder()
        .consultations(
            userAdminControllerMapper.toListUserConsultationsResponse(memberConsultations))
        .build();
  }

  @GetMapping("/{id}/consultations/{consultationId}")
  public GetUserConsultationResponse getConsultation(
      @PathVariable String id, @PathVariable long consultationId) {
    MemberConsultation memberConsultation =
        userAdminService.getMemberConsultation(id, consultationId);

    return userAdminControllerMapper.toGetUserConsultationResponse(memberConsultation);
  }

  @PostMapping("/{id}/consultations")
  public void createConsultation(
      LoginInfo loginInfo,
      @PathVariable String id,
      @RequestBody @Valid CreateUserConsultationRequest request) {
    CreateMemberConsultation createMemberConsultation =
        userAdminControllerMapper.toCreateMemberConsultation(request, id, loginInfo.getId());

    userAdminService.createMemberConsultation(createMemberConsultation);
  }

  @PutMapping("/{id}/consultations/{consultationId}")
  public void updateConsultation(
      LoginInfo loginInfo,
      @PathVariable String id,
      @PathVariable long consultationId,
      @RequestBody @Valid UpdateUserConsultationRequest request) {
    UpdateMemberConsultation updateMemberConsultation =
        userAdminControllerMapper.toUpdateMemberConsultation(
            request, consultationId, id, loginInfo.getId());

    userAdminService.updateMemberConsultation(updateMemberConsultation);
  }

  @DeleteMapping("/{id}/consultations/{consultationId}")
  public void deleteConsultation(@PathVariable String id, @PathVariable long consultationId) {
    userAdminService.deleteMemberConsultation(consultationId, id);
  }

  /**
   * 18. 회원 주문 목록 조회
   */
  @GetMapping("/{id}/orders")
  public ListUserOrdersResponse listOrders(@PathVariable String id) {
    List<Order> orders = userAdminService.listOrders(id);

    return ListUserOrdersResponse.builder()
        .order(userAdminControllerMapper.toListUserOrdersResponse(orders))
        .build();
  }

  /**
   * 19. 회원 주문 조회 (상세조회)
   */
  @GetMapping("/{id}/orders/{orderId}")
  public GetUserOrderResponse getOrder(@PathVariable String id, @PathVariable String orderId) {
    Order order = userAdminService.getOrder(id, orderId);

    GetUserOrderResponse response = userAdminControllerMapper.toGetUserOrderResponse(order);

    // 주문 상품 별 결제 목록이 존재하는지 안하는지
    response
        .getOrderProducts()
        .forEach(
            orderProduct -> {
              orderProduct.setHasPayments(ObjectUtils.isNotEmpty(order.getPayments()));
              // 환불이 없고, 결제가 없을 시 취소 가능
              orderProduct.setRefundType(
                  response.isCancelable()
                      ? GetUserOrderResponse.RefundType.CANCELABLE
                      // 환불금액이 0보다 크면 환불완료, 아니면 환불가능
                      : orderProduct.getRefundAmount() == orderProduct.getBillingAmount()
                          ? GetUserOrderResponse.RefundType.REFUNDED
                          : GetUserOrderResponse.RefundType.REFUNDABLE);
            });

    // 모든 orderProduct의 refundAmount를 더해서 response의 refundAmount에 설정
    int totalRefundAmount =
        response.getOrderProducts().stream()
            .mapToInt(GetUserOrderResponse.OrderProduct::getRefundAmount)
            .sum();
    response.setRefundAmount(totalRefundAmount);

    return response;
  }

  /*
  @GetMapping("/{id}/orders/{orderId}")
  public GetUserOrderResponse getOrder(@PathVariable String id, @PathVariable String orderId) {
    Order order = userAdminService.getOrder(id, orderId);

    GetUserOrderResponse response = userAdminControllerMapper.toGetUserOrderResponse(order);

    response.getOrderProducts().forEach(orderProduct -> {
      // 주문 상품 별 결제 목록이 존재하는지 안하는지
      orderProduct.setHasPayments(ObjectUtils.isNotEmpty(order.getPayments()));
      // 환불이 없고, 결제가 없을 시 취소 가능
      orderProduct.setRefundType(response.isCancelable() ? GetUserOrderResponse.RefundType.CANCELABLE
              // 환불금액이 0보다 크면 환불완료, 아니면 환불가능
          : orderProduct.getRefundAmount() == orderProduct.getBillingAmount() ? GetUserOrderResponse.RefundType.REFUNDED : GetUserOrderResponse.RefundType.REFUNDABLE);
    });

    return response;
  }*/

  /**
   * 22. 회원 주문 상품 등록(주문 등록)
   */
  @PostMapping("/{id}/orders/products")
  public CreateUserOrderProductResponse createOrderProduct(
      LoginInfo loginInfo,
      @PathVariable String id,
      @RequestBody @Valid CreateUserOrderProductRequest request) {
    CreateOrderProduct createOrderProduct =
        userAdminControllerMapper.toCreateOrderProduct(request, id, loginInfo.getId());

    String orderId = userAdminService.createOrderProduct(createOrderProduct);

    return CreateUserOrderProductResponse.builder().orderId(orderId).build();
  }

  @GetMapping("/{id}/ldfs")
  public PageResponse<ListUserLdfsResponse> listLdfs(
      @PathVariable String id, ListUserLdfsRequest request) {
    SearchUserLdfs searchUserLdfs = userAdminControllerMapper.toSearchUserLdfs(request, id);
    Page<LdfDto> ldfPage = userAdminService.listLdfs(searchUserLdfs);
    return userAdminControllerMapper.toListUserLdfsResponse(ldfPage, searchUserLdfs);
  }

  @GetMapping("/{id}/ldfs/{ldfId}")
  public GetUserLdfResponse getLdf(@PathVariable String id, @PathVariable long ldfId) {
    Ldf ldf = userAdminService.getLdf(id, ldfId);
    return userAdminControllerMapper.toGetUserLdfResponse(ldf);
  }

  @PostMapping("/{id}/ldfs")
  public void createLdf(
      LoginInfo loginInfo, @PathVariable String id, @RequestBody CreateUserLdfRequest request) {
    CreateUserLdf createUserLdf =
        userAdminControllerMapper.toCreateUserLdf(loginInfo.getId(), id, request);
    userAdminService.createLdf(createUserLdf);
  }

  @PutMapping("/ldfs/{id}")
  public void updateLdf(
      LoginInfo loginInfo, @PathVariable long id, @RequestBody UpdateLdfRequest request) {
    UpdateLdf updateLdf = userAdminControllerMapper.toUpdateLdf(loginInfo.getId(), request);
    userAdminService.updateLdf(id, updateLdf);
  }

  @GetMapping("/{id}/levelTests")
  public ListUserLevelTestsResponse listLevelTests(@PathVariable String id) {
    List<LevelTest> levelTests = userAdminService.listLevelTests(id);

    return ListUserLevelTestsResponse.builder()
        .levelTest(userAdminControllerMapper.toListUserLevelTestsResponse(levelTests))
        .build();
  }

  /**
   * 테스트 상세 조회
   */
  @GetMapping("/{id}/levelTests/{testId}")
  public GetUserLevelTestResponse getLevelTest(@PathVariable String id, @PathVariable Long testId) {
    return userAdminService.getLevelTest(id, testId);
  }

  /**
   * 테스트 삭제
   */
  @DeleteMapping("/{id}/levelTests/{testId}")
  public void deleteLeverTest(@PathVariable String id, @PathVariable Long testId) {
    userAdminService.deleteLeverTest(id, testId);
  }

  /**
   * 테스트 등록
   */
  @PostMapping("/{id}/levelTests")
  public void createTest(
      LoginInfo loginInfo, @PathVariable String id, @Valid CreateUserLevelTestRequest request) {
    CreateUserLevelTest createTest =
        userAdminControllerMapper.toCreateUserTest(id, request, loginInfo.getId());
    userAdminService.createUserTest(createTest);
  }

  /**
   * 테스트 수정
   */
  @PutMapping("/{id}/levelTests/{testId}")
  public void updateLevelTest(
      LoginInfo loginInfo,
      @PathVariable String id,
      @PathVariable Long testId,
      UpdateUserLevelTestRequest request) {
    UpdateUserLevelTest updateUserLevelTest =
        userAdminControllerMapper.toUpdateUserLevelTest(id, request, testId, loginInfo.getId());
    userAdminService.updateLevelTest(updateUserLevelTest);
  }

  /**
   * 37. 회원 주문 결제/환불 목록 조회
   */
  @GetMapping("/{id}/orders/{orderId}/payments")
  public ListUserOrderPaymentsResponse listOrderPayments(
      @PathVariable String id, @PathVariable String orderId) {
    Order order = userAdminService.getOrderWithPayments(orderId);

    List<ListUserOrderPaymentsResponse.Payment> payments =
        order.getPayments().stream()
            .map(userAdminControllerMapper::toListUserOrderPaymentsResponsePayment)
            .toList();

    List<ListUserOrderPaymentsResponse.Refund> refudns =
        order.getOrderProducts().stream()
            .flatMap(
                orderProduct ->
                    orderProduct.getRefunds().stream()
                        .map(
                            refund ->
                                userAdminControllerMapper.toListUserOrderPaymentsResponseRefund(
                                    refund, orderProduct.getName())))
            .toList();

    return userAdminControllerMapper.toListUserOrderPaymentsResponse(order, payments, refudns);
  }

  /**
   * 39. 회원 주문 결제 등록
   */
  @PostMapping("/{id}/orders/{orderId}/payments")
  public void createOrderPayments(
      LoginInfo loginInfo,
      @PathVariable String id,
      @PathVariable String orderId,
      @RequestBody @Valid CreateUserOrderPaymentsRequest request) {

    request.validate();

    CreateOrderPayments createOrderPayments =
        userAdminControllerMapper.toCreateOrderPayments(request, id, orderId, loginInfo.getId());

    userAdminService.createOrderPayments(createOrderPayments);
  }

  /**
   * 40.회원 주문 결제 취소
   */
  @DeleteMapping("/{id}/orders/{orderId}/payments/{paymentId}")
  public void deleteOrderPayment(
      LoginInfo loginInfo,
      @PathVariable String id,
      @PathVariable String orderId,
      @PathVariable String paymentId) {
    userAdminService.deleteOrderPayment(id, orderId, paymentId, loginInfo.getId());
  }

  /**
   * 41. 회원 주문 환불 등록
   */
  @PostMapping("/{id}/orders/{orderId}/orderProducts/{orderProductId}/refund")
  public void createOrderRefund(
      LoginInfo loginInfo,
      @PathVariable String id,
      @PathVariable String orderId,
      @PathVariable String orderProductId,
      @RequestBody @Valid CreateUserOrderRefundRequest request) {

    CreateOrderRefund createOrderRefund =
        userAdminControllerMapper.toCreateOrderRefund(
            request, id, orderId, orderProductId, loginInfo.getId());

    userAdminService.createOrderRefund(createOrderRefund);
  }

  /**
   * 42. 회원 주문상세 취소 : 주문등록 팝업에서 주문정보에 개별 취소
   */
  @DeleteMapping("/{id}/orders/{orderId}/orderProducts/{orderProductId}")
  public void deleteOrderProduct(
      LoginInfo loginInfo,
      @PathVariable String id,
      @PathVariable String orderId,
      @PathVariable String orderProductId) {
    userAdminService.deleteOrderProduct(id, orderId, orderProductId, loginInfo.getId());
  }

  /**
   * 회원 주문 전체 취소
   */
  @DeleteMapping("/{id}/orders/{orderId}")
  public void deleteOrderProduct(
      LoginInfo loginInfo, @PathVariable String id, @PathVariable String orderId) {
    userAdminService.deleteOrder(id, orderId, loginInfo.getId());
  }

  @GetMapping("/{id}/orders/{orderId}/payments/{paymentId}")
  public GetUserOrderPaymentResponse getOrderPayment(
      @PathVariable String id, @PathVariable String orderId, @PathVariable String paymentId) {
    Payment payment = userAdminService.getPayment(paymentId);
    Integer installmentMonths = payment.getInstallmentMonths();
    if (installmentMonths == null) {
      installmentMonths = 0;
    }
    return userAdminControllerMapper.toGetUserOrderPaymentResponse(payment, installmentMonths);
  }

  @PutMapping("/{id}/orders/{orderId}/payments/{paymentId}")
  public void updateOrderPayment(
      LoginInfo loginInfo,
      @PathVariable String id,
      @PathVariable String orderId,
      @PathVariable String paymentId,
      @RequestBody @Valid UpdateUserOrderPaymentsRequest request) {

    UpdateOrderPayment updateOrderPayment =
        userAdminControllerMapper.toUpdateOrderPayment(request, id, orderId, paymentId);

    userAdminService.updateOrderPayment(updateOrderPayment);
  }
}
