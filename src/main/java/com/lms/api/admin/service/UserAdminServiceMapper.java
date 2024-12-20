package com.lms.api.admin.service;

import com.lms.api.admin.service.dto.Course;
import com.lms.api.admin.service.dto.CourseHistory;
import com.lms.api.admin.service.dto.CreateMemberConsultation;
import com.lms.api.admin.service.dto.CreateNote;
import com.lms.api.admin.service.dto.CreateOrderProduct;
import com.lms.api.admin.service.dto.CreateOrderRefund;
import com.lms.api.admin.service.dto.CreateUser;
import com.lms.api.admin.service.dto.CreateUserLdf;
import com.lms.api.admin.service.dto.CreateUserLevelTest;
import com.lms.api.admin.service.dto.GetUserLevelTest;
import com.lms.api.admin.service.dto.Ldf;
import com.lms.api.admin.service.dto.LevelTest;
import com.lms.api.admin.service.dto.MemberConsultation;
import com.lms.api.admin.service.dto.Note;
import com.lms.api.admin.service.dto.Order;
import com.lms.api.admin.service.dto.OrderProduct;
import com.lms.api.admin.service.dto.Payment;
import com.lms.api.admin.service.dto.Refund;
import com.lms.api.admin.service.dto.Reservation;
import com.lms.api.admin.service.dto.Teacher;
import com.lms.api.admin.service.dto.UpdateCourse;
import com.lms.api.admin.service.dto.UpdateMemberConsultation;
import com.lms.api.admin.service.dto.UpdateNote;
import com.lms.api.admin.service.dto.UpdateOrderPayment;
import com.lms.api.admin.service.dto.UpdateReservation;
import com.lms.api.admin.service.dto.UpdateReservations;
import com.lms.api.admin.service.dto.UpdateUser;
import com.lms.api.admin.service.dto.UpdateUserLevelTest;
import com.lms.api.admin.service.dto.User;
import com.lms.api.admin.service.dto.teacher.CreateTeacher;
import com.lms.api.admin.service.dto.teacher.MCreateTeacher;
import com.lms.api.common.code.CourseStatus;
import com.lms.api.common.code.OrderType;
import com.lms.api.common.code.UserType;
import com.lms.api.common.entity.CourseEntity;
import com.lms.api.common.entity.CourseHistoryEntity;
import com.lms.api.common.entity.LdfEntity;
import com.lms.api.common.entity.LevelTestEntity;
import com.lms.api.common.entity.MemberConsultationEntity;
import com.lms.api.common.entity.OrderEntity;
import com.lms.api.common.entity.OrderProductEntity;
import com.lms.api.common.entity.PaymentEntity;
import com.lms.api.common.entity.RefundEntity;
import com.lms.api.common.entity.ReservationEntity;
import com.lms.api.common.entity.TeacherEntity;
import com.lms.api.common.entity.UserEntity;
import com.lms.api.common.mapper.ServiceMapper;
import com.lms.api.common.mapper.ServiceMapperConfig;
import java.util.List;
import java.util.Optional;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", config = ServiceMapperConfig.class, uses = {ServiceMapper.class})
public interface UserAdminServiceMapper {

  @Mapping(target = "lessonInfo", source = "lessonInfo")
  User toUser(UserEntity userEntity);

  User toUser(Optional<UserEntity> userEntity);

  @Mapping(target = "password", source = "password")
  UserEntity toUserEntity(CreateUser createUser, String id, String password, UserType type);

  @Mapping(target = "password", source = "password")
  UserEntity toUserEntity(CreateTeacher createUser, String id, String password, UserType type);

  /**
   * M 강사 등록
   */
  @Mapping(target = "password", source = "password")
  UserEntity toUserEntity(MCreateTeacher createUser, String id, String password, UserType type);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "password", source = "password")
  @Mapping(target = "lessonInfo", source = "lessonInfo")
  void mapUserEntity(UpdateUser updateUser, @MappingTarget UserEntity userEntity);


  @Mapping(target = "id", ignore = true)
  void mapUserEntity(String id, @MappingTarget UserEntity userEntity);

  @Mapping(target = "user", source = "courseEntity.userEntity")
  @Mapping(target = "orderProduct", source = "courseEntity.orderProductEntity")
  @Mapping(target = "teacher", source = "courseEntity.teacherEntity")
  @Mapping(target = "assistantTeacher", source = "courseEntity.assistantTeacherEntity")
  Course toCourse(CourseEntity courseEntity);

  @Mapping(target = "user", source = "courseEntity.userEntity")
  @Mapping(target = "orderProduct", source = "courseEntity.orderProductEntity")
  @Mapping(target = "teacher", source = "courseEntity.teacherEntity")
  @Mapping(target = "assistantTeacher", source = "courseEntity.assistantTeacherEntity")
  @Mapping(target = "status", source = "status")
  Course toCourse(CourseEntity courseEntity, CourseStatus status);

  @Mapping(target = "product", source = "productEntity")
  @Mapping(target = "refunds", source = "refundEntities")
  OrderProduct toOrderProduct(OrderProductEntity orderProductEntity);

  @Mapping(target = "product", source = "orderProductEntity.productEntity")
  @Mapping(target = "refunds", source = "orderProductEntity.refundEntities")
  @Mapping(target = "amount", source = "supplyAmount")
  @Mapping(target = "billingAmount", source = "billingAmount")
  OrderProduct toOrderProduct(OrderProductEntity orderProductEntity, Boolean hasReservations, int supplyAmount , int billingAmount);

  @Mapping(target = "course", source = "reservationEntity.courseEntity")
  @Mapping(target = "teacher", source = "reservationEntity.teacherEntity")
  @Mapping(target = "product", source = "reservationEntity.courseEntity.orderProductEntity.productEntity")
  Reservation toReservation(ReservationEntity reservationEntity, String modifierName, String cancelerName);

  @Mapping(target = "user", source = "userEntity")
  Teacher toTeacher(TeacherEntity teacherEntity);

  @Mapping(target = "id", ignore = true)
  void mapReservationEntity(UpdateReservations.Reservation reservation,
      @MappingTarget ReservationEntity reservationEntity);


  CourseHistory toCourseHistory(CourseHistoryEntity courseHistoryEntity, String modifierName);

  Note toNote(CourseHistoryEntity courseHistoryEntity, String modifierName, String creatorName);

  Note toNote(CourseHistoryEntity courseHistoryEntity);

  // 비고등록
  @Mapping(target = "courseEntity.id", source = "courseId")
  CourseHistoryEntity toCourseHistoryEntity(CreateNote createCourseHistory);

  @Mapping(target = "id", ignore = true)
  void mapCourseHistoryEntity(UpdateNote updateNote,
      @MappingTarget CourseHistoryEntity courseHistoryEntity);


  @Mapping(target = "id", ignore = true)
  void mapCourseEntity(UpdateCourse updateCourse, @MappingTarget CourseEntity courseEntity);

  MemberConsultation toMemberConsultation(MemberConsultationEntity memberConsultationEntity,
      String creatorName);

  MemberConsultationEntity toMemberConsultationEntity(
      CreateMemberConsultation createMemberConsultation);

  @Mapping(target = "id", ignore = true)
  void mapMemberConsultationEntity(UpdateMemberConsultation updateMemberConsultation,
      @MappingTarget MemberConsultationEntity memberConsultationEntity);

  @Mapping(target = "orderProducts", source = "orderEntity.orderProductEntities")
  @Mapping(target = "payments", source = "orderEntity.paymentEntities")
  Order toOrder(OrderEntity orderEntity, String creatorName);

  @Mapping(target = "payments", source = "orderEntity.paymentEntities")
  Order toOrder(OrderEntity orderEntity, List<OrderProduct> orderProducts);

/*
  @Mapping(target = "refundAmount", source = "refundAmount")
  Order toOrderList(OrderEntity orderEntity, List<OrderProduct> orderProducts , int refundAmount);
*/

/*
  주문 등록 시 사용
  OrderEntity toOrderEntity(CreateOrderProduct createOrderProduct, String id, OrderType type,
      int supplyAmount, int receivableAmount);
  */
  //수정 > 주문 등록시 사용
  @Mapping(target = "supplyAmount", source = "supplyAmount")
  OrderEntity toOrderEntity(CreateOrderProduct createOrderProduct, String id, OrderType type,
      int supplyAmount, int receivableAmount);


  // 주문 상품의 청구금액 -> 주문 상품의 결제금액으로 변경
  @Mapping(target = "paymentAmount", source = "createOrderProduct.billingAmount")
  OrderProductEntity toOrderProductEntity(CreateOrderProduct createOrderProduct, String id,
      int amount);

  @Mapping(target = "id", ignore = true)
  void mapReservationEntity(UpdateReservation updateReservation,
      @MappingTarget ReservationEntity reservationEntity);

  @Mapping(target = "reservation", source = "ldfEntity.reservationEntity")
  Ldf toLdf(LdfEntity ldfEntity);

  @Mapping(target = "userEntity.id" , source = "userId")
  @Mapping(target = "reservationEntity.id" , source = "reservationId")
  LdfEntity toLdfEntity(CreateUserLdf createUserLdf);

  @Mapping(target = "user", source = "userEntity")
  @Mapping(target = "teacher", source = "teacherEntity")
  @Mapping(target = "course", source = "courseEntity")
  Reservation toReservation(ReservationEntity reservation);


  LevelTest toLevelTest(LevelTestEntity memberConsultationEntity, String creatorName);

  LevelTest toLevelTest(LevelTestEntity memberConsultationEntity);

  /**
   * 테스트 등록
   */
  @Mapping(target = "userEntity.id", source = "userId")
  @Mapping(target = "studyType", source = "studyTypeList")
  @Mapping(target = "consonants", source = "consonantsList")
  @Mapping(target = "vowels", source = "vowelsList")
  @Mapping(target = "recommendedLevel", source = "recommendedLevelList")
  LevelTestEntity toLevelTestEntity(CreateUserLevelTest createUserTest);



  /**
   * 상세 조회
   */
  @Mapping(target = "fileSize",source = "levelTestEntity.fileSize")
  @Mapping(target = "fileUrl",source = "fileUrl")
  GetUserLevelTest toGetUserLevelTest(LevelTestEntity levelTestEntity, String fileUrl);

  /**
   * 테스트 수정
   */
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "studyType", source = "studyTypeList")
  @Mapping(target = "consonants", source = "consonantsList")
  @Mapping(target = "vowels", source = "vowelsList")
  @Mapping(target = "recommendedLevel", source = "recommendedLevelList")
  void mapLevelTestEntity(UpdateUserLevelTest updateUserLevelTest,
      @MappingTarget LevelTestEntity levelTestEntity);


  Order toOrder(OrderEntity orderEntity, List<OrderProduct> orderProducts, List<Payment> payments);

  @Mapping(target = "refundAmount" , source = "totalRefundAmount")
  Order toOrder(OrderEntity orderEntity, List<OrderProduct> orderProducts,  int totalRefundAmount);

  @Mapping(target = "product", source = "orderProductEntity.productEntity")
  OrderProduct toOrderProduct(OrderProductEntity orderProductEntity, List<Refund> refunds);

  @Mapping(target = "createdBy", source = "paymentEntity.modifiedBy")
  Payment toPayment(PaymentEntity paymentEntity, String creatorName);

  /** 결제/환불 목록 조회 시 사용*/
  @Mapping(target = "createdBy", source = "paymentEntity.modifiedBy")
  @Mapping(target = "codeName", source = "cardCompanyName")
  @Mapping(target = "code", source = "paymentEntity.cardCompany")
  Payment toListPayment(PaymentEntity paymentEntity, String creatorName, String cardCompanyName);

  Refund toRefund(RefundEntity refundEntity, String modifierName);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "paymentDate", ignore = true)
  @Mapping(target = "type", ignore = true)
  @Mapping(target = "paymentAmount", ignore = true)
  @Mapping(target = "memo", ignore = true)
  @Mapping(target = "createdBy", ignore = true)
  @Mapping(target = "createdOn", ignore = true)
  @Mapping(target = "modifiedBy", ignore = true)
  @Mapping(target = "modifiedOn", ignore = true)
  PaymentEntity toCancelPaymentEntity(PaymentEntity paymentEntity);

  @Mapping(target = "refundAmount", source = "createOrderRefund.totalRefundAmount")
  RefundEntity toRefundEntity(CreateOrderRefund createOrderRefund, String id);

  void mapPaymentEntity(UpdateOrderPayment updateOrderPayment, @MappingTarget PaymentEntity paymentEntity);
}
