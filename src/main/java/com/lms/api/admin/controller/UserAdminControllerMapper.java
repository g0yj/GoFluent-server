package com.lms.api.admin.controller;

import com.lms.api.admin.controller.dto.CreateUserConsultationRequest;
import com.lms.api.admin.controller.dto.CreateUserLdfRequest;
import com.lms.api.admin.controller.dto.CreateUserLevelTestRequest;
import com.lms.api.admin.controller.dto.CreateUserNoteRequest;
import com.lms.api.admin.controller.dto.CreateUserOrderPaymentsRequest;
import com.lms.api.admin.controller.dto.CreateUserOrderProductRequest;
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
import com.lms.api.admin.service.dto.OrderProduct;
import com.lms.api.admin.service.dto.Payment;
import com.lms.api.admin.service.dto.Refund;
import com.lms.api.admin.service.dto.Reservation;
import com.lms.api.admin.service.dto.SearchCourseHistories;
import com.lms.api.admin.service.dto.SearchUserCourses;
import com.lms.api.admin.service.dto.SearchUserLdfs;
import com.lms.api.admin.service.dto.SearchUserReservations;
import com.lms.api.admin.service.dto.SearchUsers;
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
import com.lms.api.common.code.CourseHistoryType;
import com.lms.api.common.controller.dto.PageRequest;
import com.lms.api.common.controller.dto.PageResponse;
import com.lms.api.common.mapper.ControllerMapper;
import com.lms.api.common.mapper.ControllerMapperConfig;
import com.lms.api.common.service.dto.Search;
import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring", config = ControllerMapperConfig.class, uses = {
    ControllerMapper.class})
public interface UserAdminControllerMapper {

  @Mapping(target = "type" , source = "type")
  SearchUsers toSearchUsers(ListUsersRequest listUsersRequest);

/*
  @Mapping(target = "list", source = "userPage.content")
  @Mapping(target = "totalCount", source = "userPage.totalElements")
  PageResponse<ListUsersResponse> toListUsersResponse1(Page<User> userPage, Search search);

  @Mapping(target = "createDate", source = "createdOn")
  ListUsersResponse toListUsersResponse1(User user);

*/

  @Mapping(target = "list", source = "userPage.content")
  @Mapping(target = "totalCount", source = "userPage.totalElements")
  PageResponse<ListUsersResponse> toListUsersResponse(Page<UserDto> userPage, Search search);

  @Mapping(target = "createDate", source = "user.createdOn")
  @Mapping(target = "id", source = "user.id")
  @Mapping(target = "name", source = "user.name")
  @Mapping(target = "active", source = "user.active")
  @Mapping(target = "cellPhone", source = "user.cellPhone")
  @Mapping(target = "email", source = "user.email")
  @Mapping(target = "company", source = "user.company")
  @Mapping(target = "remainingCount", source = "remainingCount")
  @Mapping(target = "notBook", source = "notBook")
  @Mapping(target = "expirationDate", source = "endDate")
  ListUsersResponse toListUsersResponse(UserDto user);


  @Mapping(target = "createDateTime", source = "createdOn")
  @Mapping(target = "lessonInfo", source = "lessonInfo")
  GetUserResponse toGetUserResponse(User user);


  CreateUser toCreateUser(CreateUserRequest createUserRequest, String createdBy);

  @Mapping(target="lessonInfo", source = "updateUserRequest.lessonInfo")
  UpdateUser toUpdateUser(String id, UpdateUserRequest updateUserRequest, String modifiedBy);

  SearchUserCourses toSearchUserCourses(ListUserCoursesRequest listUserCoursesRequest,
      String userId);

  @Mapping(target = "list", source = "coursePage.content")
  @Mapping(target = "totalCount", source = "coursePage.totalElements")
  PageResponse<ListUserCoursesResponse> toListUserCoursesResponse(Page<Course> coursePage,
      Search search);

  @Mapping(target = "createDate", source = "createdOn")
  @Mapping(target = "teacherId", source = "teacher.userId")
  @Mapping(target = "teacherName", source = "teacher.user.name")
  @Mapping(target = "assistantTeacherId", source = "assistantTeacher.userId")
  @Mapping(target = "assistantTeacherName", source = "assistantTeacher.user.name")
  @Mapping(target = "status", source = "status")
  ListUserCoursesResponse toListUserCoursesResponse(Course course);

  SearchUserReservations toSearchUserReservations(
      ListUserReservationsRequest listUserReservationsRequest, String userId);

  @Mapping(target = "list", source = "reservationPage.content")
  @Mapping(target = "totalCount", source = "reservationPage.totalElements")
  PageResponse<ListUserReservationsResponse> toListUserReservationsResponse(
      Page<Reservation> reservationPage, Search search);

  @Mapping(target = "teacherName", source = "teacher.user.name")
  @Mapping(target = "lessonType", source = "product.lessonType")
  @Mapping(target = "courseName", source = "course.name")
  ListUserReservationsResponse toListUserReservationsResponse(Reservation reservation);

  @Mapping(target = "courseName", source = "course.name")
  GetUserReservationResponse toGetUserReservationResponse(Reservation reservation);

  UpdateReservations toUpdateReservations(
      UpdateUserReservationsRequest updateUserReservationsRequest, String userId,
      String modifiedBy);

  UpdateReservation toUpdateReservation(long id, UpdateUserReservationRequest reservation,
      String userId, String modifiedBy);

  SearchCourseHistories toSearchCourseHistories(PageRequest pageRequest, String moduleId,
      CourseHistoryModule module, CourseHistoryType type);

  SearchCourseHistories toSearchCourseHistories(PageRequest pageRequest, Long courseId,
      CourseHistoryModule module);

  @Mapping(target = "list", source = "courseHistoryPage.content")
  @Mapping(target = "totalCount", source = "courseHistoryPage.totalElements")
  PageResponse<ListUserNotesResponse1> toListUserNotesResponse(
      Page<CourseHistory> courseHistoryPage, Search search);

  ListUserNotesResponse1 toListUserNotesResponse(CourseHistory courseHistory);

  @Mapping(target = "userId", source = "id")
  Note toNote(String id, ListUserNotesRequest request, CourseHistoryModule module,
      CourseHistoryType type);

  List<ListUserNotesResponse> toListNoteResponse(List<Note> note);

  CreateNote toCreateNote(CreateUserNoteRequest request, String userId, CourseHistoryModule module,
      String createdBy);

  GetUserNoteResponse toGetNoteResponse(Note note);

  CreateReservations toCreateReservations(
      CreateUserReservationsRequest createUserReservationsRequest, String userId, String createdBy);

  UpdateNote toUpdateNote(UpdateUserNoteRequest request, long id, String modifiedBy);

  @Mapping(target = "language", source = "orderProduct.product.language")
  @Mapping(target = "name", source = "orderProduct.product.name")
  @Mapping(target = "teacherId", source = "teacher.userId")
  @Mapping(target = "assistantTeacherId", source = "assistantTeacher.userId")
  GetUserCourseResponse toGetUserCourseResponse(Course course);

  UpdateCourse toUpdateCourse(long id, UpdateUserCourseRequest updateUserCourseRequest,
      String userId, String modifiedBy);

  @Mapping(target = "list", source = "courseHistoryPage.content")
  @Mapping(target = "totalCount", source = "courseHistoryPage.totalElements")
  PageResponse<ListUserCourseHistoriesResponse> toListUserCourseHistoriesResponse(
      Page<CourseHistory> courseHistoryPage, Search search);

  List<ListUserConsultationsResponse.Consultation> toListUserConsultationsResponse(
      List<MemberConsultation> memberConsultations);

  GetUserConsultationResponse toGetUserConsultationResponse(MemberConsultation memberConsultation);

  CreateMemberConsultation toCreateMemberConsultation(
      CreateUserConsultationRequest createUserConsultationRequest, String userId, String createdBy);

  UpdateMemberConsultation toUpdateMemberConsultation(
      UpdateUserConsultationRequest updateUserConsultationRequest, long id, String userId,
      String modifiedBy);

  List<ListUserOrdersResponse.Order> toListUserOrdersResponse(List<Order> orders);

  GetUserOrderResponse toGetUserOrderResponse(Order order);

  @Mapping(target = "billingAmount", source = "billingAmount")
  @Mapping(target = "productName", source = "product.name")
  @Mapping(target = "productType", source = "product.curriculumYN")
  GetUserOrderResponse.OrderProduct toOrderProduct(OrderProduct orderProduct);

  CreateOrderProduct toCreateOrderProduct(
      CreateUserOrderProductRequest createUserOrderProductRequest, String userId, String createdBy);

  /**
   * LDF 전체 출력
   */
  SearchUserLdfs toSearchUserLdfs(ListUserLdfsRequest listUserLdfsRequest, String userId);

  @Mapping(target = "list", source = "ldfPage.content")
  @Mapping(target = "totalCount", source = "ldfPage.totalElements")
  PageResponse<ListUserLdfsResponse> toListUserLdfsResponse(Page<LdfDto> ldfPage, Search search);

  @Mapping(target = "id", source = "ldfDto.reservation.id")
  @Mapping(target = "date", source = "ldfDto.reservation.date")
  @Mapping(target = "startTime", source = "ldfDto.reservation.startTime")
  @Mapping(target = "endTime", source = "ldfDto.reservation.endTime")
  @Mapping(target = "teacherName", source = "ldfDto.reservation.teacher.user.name")
  @Mapping(target = "attendanceStatus", source = "ldfDto.reservation.attendanceStatus")
  @Mapping(target = "courseName", source = "ldfDto.reservation.course.name")
  @Mapping(target = "ldfId", source = "ldfDto.ldf.id")
  @Mapping(target = "email", source = "ldfDto.email")
  ListUserLdfsResponse toListUserLdfsResponseInfo(LdfDto ldfDto);

  /**
   * LDF 개별출력
   */
  @Mapping(target = "teacherName", source = "reservation.teacher.user.name")
  @Mapping(target = "date", source = "reservation.date")
  @Mapping(target = "startTime", source = "reservation.startTime")
  @Mapping(target = "endTime", source = "reservation.endTime")
  GetUserLdfResponse toGetUserLdfResponse(Ldf ldf);

  @Mapping(target = "createdBy" , source = "loginId")
  @Mapping(target = "userId" , source = "id")
  CreateUserLdf toCreateUserLdf(String loginId, String id, CreateUserLdfRequest request);

  @Mapping(target = "modifiedBy" , source ="loginId")
  @Mapping(target = "lesson" , source ="request.lesson")
  @Mapping(target = "contentSp" , source ="request.contentSp")
  @Mapping(target = "contentV" , source ="request.contentV")
  @Mapping(target = "contentSg" , source ="request.contentSg")
  @Mapping(target = "contentC" , source ="request.contentC")
  UpdateLdf toUpdateLdf(String loginId, UpdateLdfRequest request);

  List<ListUserLevelTestsResponse.LevelTest> toListUserLevelTestsResponse(
      List<LevelTest> levelTests);

  /**
   * 레벨 테스트 조회
   */
  @Mapping(target ="studyType" , source = "response.studyType")
  @Mapping(target ="consonants" , source = "response.consonants")
  @Mapping(target ="vowels" , source = "response.vowels")
  @Mapping(target ="recommendedLevel" , source = "response.recommendedLevel")
  GetUserLevelTestResponse toGetUserLevelTestResponse(GetUserLevelTestResponse response);

  /**
   * 레벨테스트 등록
   */
  @Mapping(target = "userId", source = "id")
  @Mapping(target = "createdBy", source = "createdBy")
  @Mapping(target = "multipartFile", source = "request.file")
  @Mapping(target = "file", ignore = true)
  CreateUserLevelTest toCreateUserTest(String id, CreateUserLevelTestRequest request,
      String createdBy);


  /**
   * 레벨테스트 수정
   */
  @Mapping(target = "userId", source = "userId")
  @Mapping(target = "id", source = "id")
  @Mapping(target = "multipartFile", source = "request.file")
  @Mapping(target = "file", ignore = true)
  UpdateUserLevelTest toUpdateUserLevelTest(String userId, UpdateUserLevelTestRequest request,
      Long id, String modifiedBy);

  ListUserOrderPaymentsResponse toListUserOrderPaymentsResponse(Order order, List<ListUserOrderPaymentsResponse.Payment> payments, List<ListUserOrderPaymentsResponse.Refund> refunds);

  //@Mapping(target = "cardCompanyLabel", source = "cardCompany")
  @Mapping(target = "installmentMonthsLabel", source = "installmentMonths")
  @Mapping(target = "modifiedBy", source = "createdBy")
  @Mapping(target = "modifierName", source = "creatorName")
  @Mapping(target = "code", source = "code")
  @Mapping(target = "codeName", source = "codeName")
  ListUserOrderPaymentsResponse.Payment toListUserOrderPaymentsResponsePayment(Payment payment);

  @Mapping(target = "modifierName", source = "refund.creatorName")
  ListUserOrderPaymentsResponse.Refund toListUserOrderPaymentsResponseRefund(Refund refund, String orderProductName);

  /** 결제 등록*/
  @Mapping(target = "receiptIssued", source = "request.isReceiptIssued")
  @Mapping(target = "cards", source = "request.cards" , qualifiedByName = "mapCards")
  CreateOrderPayments toCreateOrderPayments(CreateUserOrderPaymentsRequest request, String userId, String orderId, String createdBy);

  @Named("mapCards")
  static List<CreateOrderPayments.Card> mapCards(List<CreateUserOrderPaymentsRequest.Card> cards) {
    return cards.stream()
            .map(card -> CreateOrderPayments.Card.builder()
                    .amount(card.getAmount())
                    .cardCompany(card.getCode())
                    .cardNumber(card.getCardNumber())
                    .installmentMonths(card.getInstallmentMonths())
                    .approvalNumber(card.getApprovalNumber())
                    .build())
            .collect(Collectors.toList());
  }

  CreateOrderRefund toCreateOrderRefund(CreateUserOrderRefundRequest request, String userId, String orderId, String orderProductId, String createdBy);

  UpdateOrderPayment toUpdateOrderPayment(UpdateUserOrderPaymentsRequest request, String userId, String orderId, String paymentId);

  @Mapping(target = "modifiedName", source = "payment.creatorName")
  @Mapping(target = "modifiedBy", source = "payment.createdBy")
  @Mapping(target = "installmentMonths", source = "installmentMonths")
  GetUserOrderPaymentResponse toGetUserOrderPaymentResponse(Payment payment , Integer installmentMonths );
}
