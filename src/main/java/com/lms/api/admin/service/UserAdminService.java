package com.lms.api.admin.service;

import static com.lms.api.common.util.StringUtils.convertListToString;

import com.lms.api.admin.code.SearchUsersCode;
import com.lms.api.admin.code.SearchUsersCode.ExpireType;
import com.lms.api.admin.code.SearchUsersCode.RegisterType;
import com.lms.api.admin.code.SearchUsersCode.RemainingType;
import com.lms.api.admin.code.SearchUsersCode.UserStatus;
import com.lms.api.admin.code.levelTest.TestConsonants;
import com.lms.api.admin.code.levelTest.TestRecommendLevel;
import com.lms.api.admin.code.levelTest.TestStudyType;
import com.lms.api.admin.code.levelTest.TestVowels;
import com.lms.api.admin.controller.dto.GetUserLevelTestResponse;
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
import com.lms.api.admin.service.dto.GetUserLevelTest;
import com.lms.api.admin.service.dto.Ldf;
import com.lms.api.admin.service.dto.LdfDto;
import com.lms.api.admin.service.dto.LevelTest;
import com.lms.api.admin.service.dto.MemberConsultation;
import com.lms.api.admin.service.dto.Note;
import com.lms.api.admin.service.dto.Order;
import com.lms.api.admin.service.dto.OrderProduct;
import com.lms.api.admin.service.dto.Payment;
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
import com.lms.api.common.code.AttendanceStatus;
import com.lms.api.common.code.CourseHistoryModule;
import com.lms.api.common.code.CourseHistoryType;
import com.lms.api.common.code.CourseStatus;
import com.lms.api.common.code.JoinPath;
import com.lms.api.common.code.OrderType;
import com.lms.api.common.code.PaymentMethod;
import com.lms.api.common.code.PaymentType;
import com.lms.api.common.code.UserType;
import com.lms.api.common.code.YN;
import com.lms.api.common.entity.CalendarEntity;
import com.lms.api.common.entity.CommonCodeEntity;
import com.lms.api.common.entity.CourseEntity;
import com.lms.api.common.entity.CourseHistoryEntity;
import com.lms.api.common.entity.LdfEntity;
import com.lms.api.common.entity.LevelTestEntity;
import com.lms.api.common.entity.MemberConsultationEntity;
import com.lms.api.common.entity.OrderEntity;
import com.lms.api.common.entity.OrderProductEntity;
import com.lms.api.common.entity.PaymentEntity;
import com.lms.api.common.entity.ProductEntity;
import com.lms.api.common.entity.QCourseEntity;
import com.lms.api.common.entity.QCourseHistoryEntity;
import com.lms.api.common.entity.QLdfEntity;
import com.lms.api.common.entity.QOrderEntity;
import com.lms.api.common.entity.QReservationEntity;
import com.lms.api.common.entity.QUserEntity;
import com.lms.api.common.entity.RefundEntity;
import com.lms.api.common.entity.ReservationEntity;
import com.lms.api.common.entity.ScheduleEntity;
import com.lms.api.common.entity.TeacherEntity;
import com.lms.api.common.entity.UserEntity;
import com.lms.api.common.exception.LmsErrorCode;
import com.lms.api.common.exception.LmsException;
import com.lms.api.common.mapper.ServiceMapper;
import com.lms.api.common.repository.CalendarRepository;
import com.lms.api.common.repository.CommonCodeRepository;
import com.lms.api.common.repository.CourseHistoryRepository;
import com.lms.api.common.repository.CourseRepository;
import com.lms.api.common.repository.EmailRepository;
import com.lms.api.common.repository.LdfRepository;
import com.lms.api.common.repository.LevelTestRepository;
import com.lms.api.common.repository.MemberConsultationRepository;
import com.lms.api.common.repository.OrderProductRepository;
import com.lms.api.common.repository.OrderRepository;
import com.lms.api.common.repository.PaymentRepository;
import com.lms.api.common.repository.ProductRepository;
import com.lms.api.common.repository.ReservationRepository;
import com.lms.api.common.repository.ScheduleRepository;
import com.lms.api.common.repository.TeacherRepository;
import com.lms.api.common.repository.UserRepository;
import com.lms.api.common.service.FileService;
import com.lms.api.common.util.LmsUtils;
import com.lms.api.common.util.ObjectUtils;
import com.lms.api.common.util.StringUtils;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserAdminService {

  private final UserRepository userRepository;
  private final CourseRepository courseRepository;
  private final ReservationRepository reservationRepository;
  private final CourseHistoryRepository courseHistoryRepository;
  private final ScheduleRepository scheduleRepository;
  private final TeacherRepository teacherRepository;
  private final MemberConsultationRepository memberConsultationRepository;
  private final OrderRepository orderRepository;
  private final OrderProductRepository orderProductRepository;
  private final ProductRepository productRepository;
  private final LdfRepository ldfRepository;
  private final LevelTestRepository levelTestRepository;
  private final FileService fileService;
  private final UserAdminServiceMapper userAdminServiceMapper;
  private final ServiceMapper serviceMapper;
  private final PaymentRepository paymentRepository;
  private final CommonCodeRepository commonCodeRepository;
  private final CalendarRepository calendarRepository;
  private final EmailRepository emailRepository;

  private boolean canCancelReservation(LocalDate reservationDate) {
    LocalDate now = LocalDate.now();
    // LocalDate now = LocalDate.of(2024, 9, 23);
    LocalDate cancelDeadline = reservationDate.minusDays(2);

    // 공휴일과 주말을 제외한 취소 가능 날짜 계산
    while (isHolidayOrWeekend(cancelDeadline)) {
      cancelDeadline = cancelDeadline.minusDays(1);
    }

    return now.isBefore(cancelDeadline) || now.isEqual(cancelDeadline);
  }

  private boolean isHolidayOrWeekend(LocalDate date) {
    // 주어진 날짜에 해당하는 CalendarEntity를 찾기
    CalendarEntity calendarEntity = calendarRepository.findById(date).orElse(null);
    if (calendarEntity == null) {
      return false; // 날짜가 캘린더에 없을 경우 false 반환
    }
    // 공휴일 또는 주말 여부
    return calendarEntity.isHoliday() || calendarEntity.isWeekend();
  }

  public List<User> listConsultants() {
    return userRepository.findAllByTypeAndActiveTrue(UserType.A).stream()
        .map(userAdminServiceMapper::toUser)
        .toList();
  }

  /**
   * 미부킹,잔여 등 필드 포함한 회원 목록 조회
   */
  public Page<UserDto> listUsers(SearchUsers searchUsers) {
    QUserEntity qUserEntity = QUserEntity.userEntity;
    QOrderEntity qOrderEntity = QOrderEntity.orderEntity;
    QCourseEntity qCourseEntity = QCourseEntity.courseEntity;
    LocalDate now = LocalDate.now();

    // 회원 검색 조건
    BooleanExpression where = Expressions.TRUE;

    // 회원과 관리자(직원) 목록 api 공동으로 사용하기 위한 type
    if (searchUsers.getType() != null) {
      if (searchUsers.getType() == UserType.S) {
        where = where.and(qUserEntity.type.eq(UserType.S));
      } else if (searchUsers.getType() == UserType.A) {
        where = where.and(qUserEntity.type.eq(UserType.A));
      }
    }

    // 회원 과정 검색 조건 (회원의 수강 여부 구분)
    BooleanExpression courseExists = qCourseEntity.userEntity.eq(qUserEntity);
    BooleanExpression courseNotExists = qCourseEntity.userEntity.eq(qUserEntity);

    // 가입일자
    if (searchUsers.getCreateDateFrom() != null && searchUsers.getCreateDateTo() != null) {
      LocalDateTime fromDateTime = searchUsers.getCreateDateFrom().atStartOfDay();
      LocalDateTime toDateTime = searchUsers.getCreateDateTo().atTime(23, 59, 59);
      where = where.and(qUserEntity.createdOn.between(fromDateTime, toDateTime));
    } else if (searchUsers.getCreateDateFrom() == null && searchUsers.getCreateDateTo() != null) {
      LocalDateTime fromDateTime = LocalDateTime.MIN;
      LocalDateTime toDateTime = searchUsers.getCreateDateTo().atTime(23, 59, 59);
      where = where.and(qUserEntity.createdOn.between(fromDateTime, toDateTime));
    } else if (searchUsers.getCreateDateFrom() != null && searchUsers.getCreateDateTo() == null) {
      LocalDateTime fromDateTime = searchUsers.getCreateDateFrom().atStartOfDay();
      LocalDateTime toDateTime = LocalDateTime.now();
      where = where.and(qUserEntity.createdOn.between(fromDateTime, toDateTime));
    }

    // 등록구분 등록회원/미등록회원 - 결제테이블의 결제 금액이 있냐 없냐 유무
    if (searchUsers.getRegisterType() == RegisterType.REGISTERED) {
//      where =
//          where.and(
//              JPAExpressions.selectFrom(qOrderEntity)
//                  .where(
//                      qOrderEntity.userEntity.eq(qUserEntity).and(qOrderEntity.paymentAmount.gt(0)))
//                  .exists());
      where =
          where.and(
              JPAExpressions.selectFrom(qOrderEntity)
                  .where(qOrderEntity.userEntity.eq(qUserEntity))
                  .exists());
    } else if (searchUsers.getRegisterType() == RegisterType.UNREGISTERED) {
//      where =
//          where.and(
//              JPAExpressions.selectFrom(qOrderEntity)
//                  .where(
//                      qOrderEntity.userEntity.eq(qUserEntity).and(qOrderEntity.paymentAmount.gt(0)))
//                  .notExists());
      where =
          where.and(
              JPAExpressions.selectFrom(qOrderEntity)
                  .where(qOrderEntity.userEntity.eq(qUserEntity))
                  .notExists());
    }

    // 상태 활동/비활동
    if (searchUsers.getStatus() == UserStatus.ACTIVE) {
      where = where.and(qUserEntity.active.isTrue());
    } else if (searchUsers.getStatus() == UserStatus.INACTIVE) {
      where = where.and(qUserEntity.active.isFalse());
    }

    // 담당강사 ID
    if (StringUtils.hasText(searchUsers.getTeacherId())) {
      courseExists =
          courseExists.and(qCourseEntity.teacherEntity.userId.eq(searchUsers.getTeacherId()));
    }

    // 수강상태 수강중/비수강중/대기중
    if (searchUsers.getCourseStatus() == SearchUsersCode.CourseStatus.ATTENDING) {
      // 수강중 : course(수강) 시작일<=현재 , 현재<= 종료일 , 레슨횟수 > 배정횟수
      courseExists =
          courseExists
              .and(qCourseEntity.startDate.loe(now))
              .and(qCourseEntity.endDate.goe(now))
              .and(qCourseEntity.lessonCount.gt(qCourseEntity.assignmentCount));
      // 비수강중 : 현재 <= 종료일 ,
    } else if (searchUsers.getCourseStatus() == SearchUsersCode.CourseStatus.NOT_ATTENDING) {
      courseNotExists =
          courseNotExists
              .and(qCourseEntity.endDate.goe(now))
              .and(qCourseEntity.lessonCount.gt(qCourseEntity.assignmentCount));
    } else if (searchUsers.getCourseStatus() == SearchUsersCode.CourseStatus.WAITING) {
      courseExists =
          courseExists
              .and(qCourseEntity.startDate.gt(now))
              .and(qCourseEntity.lessonCount.gt(qCourseEntity.assignmentCount));
    }

    // 만료구분 만료됨/만료안됨
    if (searchUsers.getExpireType() == ExpireType.EXPIRED) {
      courseNotExists = courseNotExists.and(qCourseEntity.endDate.goe(now));
    } else if (searchUsers.getExpireType() == ExpireType.NOT_EXPIRED) {
      courseExists = courseExists.and(qCourseEntity.endDate.goe(now));
    }

    // 잔여구분 잔여있음/잔여없음
    if (searchUsers.getRemainingType() == RemainingType.REMAINING) {
      courseExists = courseExists.and(qCourseEntity.lessonCount.gt(qCourseEntity.assignmentCount));
    } else if (searchUsers.getRemainingType() == RemainingType.NOT_REMAINING) {
      courseNotExists =
          courseNotExists.and(qCourseEntity.lessonCount.gt(qCourseEntity.assignmentCount));
    }

    // 조건 필터링
    if (Stream.of(
            StringUtils.hasText(searchUsers.getTeacherId()),
            searchUsers.getCourseStatus() == SearchUsersCode.CourseStatus.ATTENDING,
            searchUsers.getCourseStatus() == SearchUsersCode.CourseStatus.WAITING,
            searchUsers.getExpireType() == ExpireType.NOT_EXPIRED,
            searchUsers.getRemainingType() == RemainingType.REMAINING)
        .anyMatch(Boolean::booleanValue)) {

      where = where.and(JPAExpressions.selectFrom(qCourseEntity).where(courseExists).exists());
    }

    if (Stream.of(
            searchUsers.getCourseStatus() == SearchUsersCode.CourseStatus.NOT_ATTENDING,
            searchUsers.getExpireType() == ExpireType.EXPIRED,
            searchUsers.getRemainingType() == RemainingType.NOT_REMAINING)
        .anyMatch(Boolean::booleanValue)) {

      where =
          where.and(JPAExpressions.selectFrom(qCourseEntity).where(courseNotExists).notExists());
    }

    // 검색어 필터링
    if (searchUsers.hasSearch()) {
      switch (searchUsers.getSearch()) {
        case "ALL":
          where =
              where.and(
                  qUserEntity
                      .name
                      .contains(searchUsers.getKeyword())
                      .or(qUserEntity.loginId.contains(searchUsers.getKeyword()))
                      .or(qUserEntity.email.contains(searchUsers.getKeyword()))
                      .or(qUserEntity.company.contains(searchUsers.getKeyword()))
                      .or(qUserEntity.phone.contains(searchUsers.getKeyword()))
                      .or(qUserEntity.cellPhone.contains(searchUsers.getKeyword())));
          break;
        case "name":
          where = where.and(qUserEntity.name.contains(searchUsers.getKeyword()));
          break;
        case "loginId":
          where = where.and(qUserEntity.loginId.contains(searchUsers.getKeyword()));
          break;
        case "email":
          where = where.and(qUserEntity.email.contains(searchUsers.getKeyword()));
          break;
        case "company":
          where = where.and(qUserEntity.company.contains(searchUsers.getKeyword()));
          break;
        case "phone":
          where = where.and(qUserEntity.phone.contains(searchUsers.getKeyword()));
        case "cellPhone":
          where = where.and(qUserEntity.cellPhone.contains(searchUsers.getKeyword()));
          break;
        default:
          break;
      }
    }
    // 정렬조건 : 이름, 이메일, 수강만료일, 잔여
    String order = searchUsers.getOrder();
    Sort.Direction direction = Sort.Direction.valueOf(searchUsers.getDirection().toUpperCase());
    Sort sort;

    // type이 'A' 인경우 정렬 기준 추가 (10월 16일 조원빈 요청)
    if (searchUsers.getType() != null && searchUsers.getType() == UserType.A) {
      sort = Sort.by(Direction.DESC, "active")
          .and(Sort.by(Direction.ASC, "name"));
    } else {
      switch (order) {
        case "name":
          sort = Sort.by(direction, "name");
          break;
        case "email":
          sort = Sort.by(direction, "email");
          break;
        case "endDate":
          sort = Sort.by(direction, "courseEntities.endDate");
          break;
        case "remainCount":
          sort = Sort.by(direction, "courseEntities.remainCount");
          break;
        default:
          // 기본 정렬: createdOn 기준으로 내림차순
          sort = Sort.by(Sort.Order.desc("createdOn"));
          break;
      }
    }
    // JPA에서 페이징 및 정렬 처리
    PageRequest pageRequest =
        PageRequest.of(searchUsers.getPage() - 1, searchUsers.getLimit(), sort);
    Page<UserEntity> userPage = userRepository.findAll(where, pageRequest);

    Page<UserDto> userDtoPage =
        userPage.map(
            userEntity -> {
              Float remainingCount = null;
              LocalDate endDate = null;
              Integer notBookDays = null;

              if (userEntity.getCourseEntities() != null
                  && !userEntity.getCourseEntities().isEmpty()) {
                // 수강 강의 여부 확인
                Optional<CourseEntity> latestCourseOpt =
                    userEntity.getCourseEntities().stream()
                        .max(
                            Comparator.comparing(
                                courseEntity -> {
                                  Optional<LocalDate> latestReservationDateOpt =
                                      courseEntity.getReservationEntities().stream()
                                          .map(ReservationEntity::getDate)
                                          .max(LocalDate::compareTo);
                                  return latestReservationDateOpt.orElse(
                                      courseEntity.getStartDate());
                                }));

                if (latestCourseOpt.isPresent()) {
                  CourseEntity latestCourse = latestCourseOpt.get();
                  remainingCount = latestCourse.getRemainCount();
                  endDate = latestCourse.getEndDate();

                  if (remainingCount != null && remainingCount > 0) {
                    Optional<LocalDate> latestReservationDateOpt =
                        latestCourse.getReservationEntities().stream()
                            .map(ReservationEntity::getDate)
                            .max(LocalDate::compareTo);

                    if (latestReservationDateOpt.isPresent()
                        && latestReservationDateOpt.get().isBefore(now)) {
                      notBookDays =
                          Math.toIntExact(
                              ChronoUnit.DAYS.between(latestReservationDateOpt.get(), now));
                    }
                  }
                }
              }

              return UserDto.builder()
                  .user(userAdminServiceMapper.toUser(userEntity))
                  .endDate(endDate)
                  .remainingCount(remainingCount)
                  .notBook(notBookDays)
                  .build();
            });

    return userDtoPage;
  }

  public User getUser(String id) {
    User user =
        userRepository
            .findById(id)
            .map(userAdminServiceMapper::toUser)
            .orElseThrow(() -> new LmsException(LmsErrorCode.USER_NOT_FOUND));
    return user;
  }

  @Transactional
  public UserEntity createUser(CreateUser createUser) {
    if (userRepository.findByLoginId(createUser.getLoginId()).isPresent()) {
      throw new LmsException(LmsErrorCode.LOGIN_SERVER_ERROR);
    }
    if (createUser.getType() == null) {
      throw new IllegalArgumentException(" type은 S/A 필수");
    }

    if (createUser.getJoinPath() == JoinPath.ALL) {
      createUser.setJoinPath(null);
    }
    UserEntity userEntity = null;

    switch (createUser.getType()) {
      case "S":
        userEntity =
            userAdminServiceMapper.toUserEntity(
                createUser,
                LmsUtils.createUserId(),
                LmsUtils.encryptPassword(createUser.getPassword()),
                UserType.S);
        break;
      case "A":
        userEntity =
            userAdminServiceMapper.toUserEntity(
                createUser,
                LmsUtils.createUserId(),
                LmsUtils.encryptPassword(createUser.getPassword()),
                UserType.A);
        break;
      default:
        throw new IllegalArgumentException(" type은 S/A 필수.");
    }

    return userRepository.save(userEntity);
  }

  @Transactional
  public void updateUser(UpdateUser updateUser) {

    Optional<UserEntity> findUser = userRepository.findByLoginId(updateUser.getLoginId());
    if (findUser.isPresent() && !findUser.get().getId().equals(updateUser.getId())) {
      throw new LmsException(LmsErrorCode.LOGIN_SERVER_ERROR);
    }

    userRepository
        .findById(updateUser.getId())
        .ifPresentOrElse(
            userEntity -> {
              userAdminServiceMapper.mapUserEntity(updateUser, userEntity);

              if (ObjectUtils.isNotEmpty(updateUser.getPassword())) {
                userEntity.setPassword(LmsUtils.encryptPassword(updateUser.getPassword()));
              }
            },
            () -> {
              throw new LmsException(LmsErrorCode.USER_NOT_FOUND);
            });
  }

  @Transactional
  public void deleteUser(String id) {
    userRepository.deleteById(id);
  }

  public Page<Course> listCourses(SearchUserCourses searchUserCourses) {
    QCourseEntity qCourseEntity = QCourseEntity.courseEntity;
    LocalDate now = LocalDate.now();

    BooleanExpression where = qCourseEntity.userEntity.id.eq(searchUserCourses.getUserId());

    where =
        switch (searchUserCourses.getStatus()) {
          case VALID -> where.and(qCourseEntity.orderProductEntity.refundEntities.isEmpty());
          case ATTENDING -> where
              .and(qCourseEntity.startDate.loe(now))
              .and(qCourseEntity.endDate.goe(now))
              .and(qCourseEntity.lessonCount.gt(qCourseEntity.assignmentCount))
              .and(qCourseEntity.orderProductEntity.refundEntities.isEmpty());
          case WAITING -> where
              .and(qCourseEntity.startDate.gt(now))
              .and(qCourseEntity.lessonCount.gt(qCourseEntity.assignmentCount))
              .and(qCourseEntity.orderProductEntity.refundEntities.isEmpty());
          case COMPLETE -> where.and(
              qCourseEntity
                  .lessonCount
                  .eq(qCourseEntity.assignmentCount)
                  .and(qCourseEntity.assignmentCount.eq(qCourseEntity.attendanceCount))
                  .and(qCourseEntity.orderProductEntity.refundEntities.isEmpty())
                  .or(qCourseEntity.endDate.lt(now)));
          case REFUND -> where.and(qCourseEntity.orderProductEntity.refundEntities.isEmpty());
          // 수강권 끝나는 날 이전 or 수강횟수가 0보다 큰것
          case AVAILABLE -> where
              .and(qCourseEntity.endDate.goe(now))
              .and(qCourseEntity.lessonCount.gt(qCourseEntity.assignmentCount))
              .and(qCourseEntity.orderProductEntity.refundEntities.isEmpty());
        };

    return courseRepository
        .findAll(where, searchUserCourses.toPageRequest())
        .map(
            courseEntity -> {
              CourseStatus status =
                  !courseEntity.getOrderProductEntity().getRefundEntities().isEmpty()
                      ? CourseStatus.CANCEL
                      : courseEntity.getStartDate().isAfter(now)
                        && courseEntity.getLessonCount() > courseEntity.getAssignmentCount()
                          ? CourseStatus.WAITING
                          : CourseStatus.NORMAL;

              return userAdminServiceMapper.toCourse(courseEntity, status);
            });
  }

  public Page<Reservation> listReservations(SearchUserReservations searchUserReservations) {
    QReservationEntity qReservationEntity = QReservationEntity.reservationEntity;

    BooleanExpression where =
        qReservationEntity.userEntity.id.eq(searchUserReservations.getUserId());

    if (searchUserReservations.getCourseId() != null) {
      where =
          where.and(qReservationEntity.courseEntity.id.eq(searchUserReservations.getCourseId()));
    }

    if (searchUserReservations.getDateFrom() != null) {
      where = where.and(qReservationEntity.date.goe(searchUserReservations.getDateFrom()));
    }

    if (searchUserReservations.getDateTo() != null) {
      where = where.and(qReservationEntity.date.loe(searchUserReservations.getDateTo()));
    }
    // 취소조건
    if (ObjectUtils.equals(searchUserReservations.getExcludeCancel(), true)) {
      where = where.and(qReservationEntity.cancel.isFalse());
    }
    // 출석조건
    if (ObjectUtils.equals(searchUserReservations.getExcludeAttendance(), true)) {
      where = where.and(qReservationEntity.attendanceStatus.eq(AttendanceStatus.R));
    }

    Sort sort = Sort.by(Sort.Order.desc("date"), Sort.Order.asc("startTime"));
    Page<ReservationEntity> reservationEntities =
        reservationRepository.findAll(
            where,
            PageRequest.of(
                searchUserReservations.getPage() - 1, searchUserReservations.getLimit(), sort));
    ;
    // 처리직원
    List<UserEntity> modifiers =
        userRepository.findAllByIdIn(
            reservationEntities.map(ReservationEntity::getModifiedBy).stream().distinct().toList());
    List<UserEntity> cancelers =
        userRepository.findAllByIdIn(
            reservationEntities
                .filter(
                    reservationEntity ->
                        reservationEntity.isCancel() && reservationEntity.getCancelBy() != null)
                .map(ReservationEntity::getCancelBy)
                .stream()
                .distinct()
                .toList());

    return reservationEntities.map(
        reservationEntity ->
            userAdminServiceMapper.toReservation(
                reservationEntity,
                modifiers.stream()
                    .filter(
                        userEntity -> userEntity.getId().equals(reservationEntity.getModifiedBy()))
                    .findFirst()
                    .map(UserEntity::getName)
                    .orElse(null),
                cancelers.stream()
                    .filter(
                        userEntity ->
                            reservationEntity.isCancel()
                            && reservationEntity.getCancelBy() != null
                            && userEntity.getId().equals(reservationEntity.getCancelBy()))
                    .findFirst()
                    .map(UserEntity::getName)
                    .orElse(null)));
  }

  public Reservation getReservation(String userId, long id) {
    return reservationRepository
        .findById(id)
        .filter(reservationEntity -> reservationEntity.getUserEntity().getId().equals(userId))
        .map(
            reservationEntity ->
                userAdminServiceMapper.toReservation(reservationEntity, null, null))
        .orElseThrow(() -> new LmsException(LmsErrorCode.RESERVATION_NOT_FOUND));
  }

  /**
   * 예약 취소
   */
  @Transactional
  public void updateReservations(UpdateReservations updateReservations) {
    List<Long> reservationIds =
        updateReservations.getReservations().stream()
            .map(UpdateReservations.Reservation::getId)
            .toList();

    reservationRepository
        .findAllByUserEntity_IdAndIdIn(updateReservations.getUserId(), reservationIds)
        .forEach(
            reservationEntity ->
                updateReservations.getReservations().stream()
                    .filter(
                        updateReservation -> updateReservation.getId() == reservationEntity.getId())
                    .findFirst()
                    .ifPresent(
                        updateReservation -> {
                          if (updateReservation.getIsCancel() != null
                              && updateReservation.getIsCancel()) {
                            // 예약 취소가 가능한 조건 검사를 하지 않음 (10월 1일 조원빈 요청)
                            // if (canCancelReservation(reservationEntity.getDate())) {
                            // if
                            // (reservationEntity.canCancelReservation(reservationEntity.getDate()))
                            // {
                            // courseEntity의 assignmentCount 원복
                            reservationEntity.cancel(
                                updateReservation.getCancelReason(),
                                updateReservations.getModifiedBy());

                            // 스케줄 찾아서 예약수 원복
                            scheduleRepository
                                .findByTeacherEntityAndDateAndStartTime(
                                    reservationEntity.getTeacherEntity(),
                                    reservationEntity.getDate(),
                                    reservationEntity.getStartTime())
                                .filter(scheduleEntity -> scheduleEntity.getReservationCount() > 0)
                                .ifPresent(
                                    scheduleEntity -> {
                                      scheduleEntity.setReservationCount(
                                          scheduleEntity.getReservationCount() - 1);
                                      String content =
                                          "총배정수: "
                                          + reservationEntity
                                              .getCourseEntity()
                                              .getAssignmentCount()
                                          + "( "
                                          + updateReservations
                                              .getReservations()
                                              .get(0)
                                              .getCancelReason()
                                          + " )";
                                      CourseHistoryEntity courseHistoryEntity =
                                          CourseHistoryEntity.addCourseHistoryEntity(
                                              reservationEntity.getCourseEntity(),
                                              CourseHistoryModule.COURSE_USER,
                                              CourseHistoryType.CANCEL_ASSIGNMENT.getCode(),
                                              content,
                                              updateReservations.getModifiedBy());
                                      courseHistoryRepository.save(courseHistoryEntity);
                                    });
                            // } else {
                            // throw new LmsException(LmsErrorCode.RESERVATION_NOT_CANCELABLE);
                            // }
                          } else {
                            userAdminServiceMapper.mapReservationEntity(
                                updateReservation, reservationEntity);
                          }
                        }));
  }

  @Transactional
  public void updateReservation(UpdateReservation updateReservation) {
    reservationRepository
        .findById(updateReservation.getId())
        .filter(
            reservationEntity ->
                reservationEntity.getUserEntity().getId().equals(updateReservation.getUserId()))
        .ifPresentOrElse(
            reservationEntity ->
                userAdminServiceMapper.mapReservationEntity(updateReservation, reservationEntity),
            () -> {
              throw new LmsException(LmsErrorCode.RESERVATION_NOT_FOUND);
            });
  }

  public Page<CourseHistory> listCourseHistories(SearchCourseHistories searchCourseHistories) {
    QCourseHistoryEntity qCourseHistoryEntity = QCourseHistoryEntity.courseHistoryEntity;

    BooleanExpression where = qCourseHistoryEntity.module.eq(searchCourseHistories.getModule());

    if (searchCourseHistories.getCourseId() != null) {
      where =
          where.and(qCourseHistoryEntity.courseEntity.id.eq(searchCourseHistories.getCourseId()));
    }

    if (searchCourseHistories.getModuleId() != null) {
      where = where.and(qCourseHistoryEntity.moduleId.eq(searchCourseHistories.getModuleId()));
    }

    if (searchCourseHistories.getType() != null) {
      where = where.and(qCourseHistoryEntity.type.eq(searchCourseHistories.getType().getCode()));
    }

    return courseHistoryRepository
        .findAll(where, searchCourseHistories.toPageRequest())
        .map(
            courseHistoryEntity -> {
              String modifierName = null;

              if (searchCourseHistories.getModule() == CourseHistoryModule.ASSIGN
                  || searchCourseHistories.getModule() == CourseHistoryModule.COURSE_USER) {

                modifierName =
                    userRepository
                        .findById(courseHistoryEntity.getModifiedBy())
                        .map(UserEntity::getName)
                        .orElse(null);
              }

              return userAdminServiceMapper.toCourseHistory(courseHistoryEntity, modifierName);
            });
  }

  /**
   * 비고 목록
   */
  public List<Note> listNotes(Note note) {
    QCourseHistoryEntity qCourseHistoryEntity = QCourseHistoryEntity.courseHistoryEntity;

    BooleanExpression where =
        qCourseHistoryEntity
            .courseEntity
            .userEntity
            .id
            .eq(note.getUserId())
            .and(qCourseHistoryEntity.module.eq(note.getModule()));
    if (note.getCourseId() != null) {
      where = where.and(qCourseHistoryEntity.courseEntity.id.eq(note.getCourseId()));
    }
    if (note.getModuleId() != null) {
      where = where.and(qCourseHistoryEntity.moduleId.eq(note.getModuleId()));
    }
    if (note.getType() != null) {
      where = where.and(qCourseHistoryEntity.type.eq(note.getType().getCode()));
    }

    Sort sort = Sort.by(Sort.Direction.DESC, "createdOn");
    List<CourseHistoryEntity> courseHistoryEntities =
        (List<CourseHistoryEntity>) courseHistoryRepository.findAll(where, sort);

    return courseHistoryEntities.stream()
        .map(
            courseHistoryEntity -> {
              String modifierName = null;
              String creatorName = null;
              if (note.getModule() == CourseHistoryModule.ASSIGN
                  || note.getModule() == CourseHistoryModule.COURSE_USER) {
                modifierName =
                    userRepository
                        .findById(courseHistoryEntity.getModifiedBy())
                        .map(UserEntity::getName)
                        .orElse(null);
              }
              if (note.getModule() == CourseHistoryModule.ASSIGN
                  || note.getModule() == CourseHistoryModule.COURSE_USER) {
                creatorName =
                    userRepository
                        .findById(courseHistoryEntity.getModifiedBy())
                        .map(UserEntity::getName)
                        .orElse(null);
              }
              return userAdminServiceMapper.toNote(courseHistoryEntity, modifierName, creatorName);
            })
        .collect(Collectors.toList());
  }

  @Transactional
  public void createNote(CreateNote createNote) {

    UserEntity userEntity =
        userRepository
            .findById(createNote.getUserId())
            .orElseThrow(() -> new LmsException(LmsErrorCode.USER_NOT_FOUND));

    CourseEntity courseEntity =
        courseRepository
            .findById(createNote.getCourseId())
            .orElseThrow(() -> new LmsException(LmsErrorCode.COURSE_NOT_FOUND));

    CourseHistoryEntity courseHistoryEntity =
        userAdminServiceMapper.toCourseHistoryEntity(createNote);
    courseHistoryRepository.save(courseHistoryEntity);
  }

  public Note getNote(String userId, long id) {
    return courseHistoryRepository
        .findById(id)
        .filter(
            courseHistoryEntity ->
                courseHistoryEntity.getCourseEntity().getUserEntity().getId().equals(userId))
        .map(courseHistoryEntity -> userAdminServiceMapper.toNote(courseHistoryEntity))
        .orElseThrow(() -> new LmsException(LmsErrorCode.NOTE_NOT_FOUND));
  }

  @Transactional
  public void updateNote(UpdateNote updateNote) {

    courseHistoryRepository
        .findById(updateNote.getId())
        .ifPresentOrElse(
            courseHistoryEntity ->
                userAdminServiceMapper.mapCourseHistoryEntity(updateNote, courseHistoryEntity),
            () -> {
              throw new LmsException(LmsErrorCode.NOTE_NOT_FOUND);
            });
  }

  @Transactional
  public void deleteNote(long id) {
    courseHistoryRepository.deleteById(id);
  }

  @Transactional
  public void createReservations(CreateReservations createReservations) {
    // 유저 조회
    UserEntity userEntity =
        userRepository
            .findById(createReservations.getUserId())
            .orElseThrow(() -> new LmsException(LmsErrorCode.USER_NOT_FOUND));

    // 코스 조회
    CourseEntity courseEntity =
        courseRepository
            .findById(createReservations.getCourseId())
            .filter(entity -> entity.getUserEntity().getId().equals(createReservations.getUserId()))
            .orElseThrow(() -> new LmsException(LmsErrorCode.COURSE_NOT_FOUND));

    // 남은 수업 수가 충분한지 체크
    if (courseEntity.getRemainCount() < createReservations.getScheduleIds().size()) {
      throw new LmsException(LmsErrorCode.LESSON_COUNT_NOT_ENOUGH);
    }

    // 스케줄 조회
    List<ScheduleEntity> scheduleEntities =
        scheduleRepository.findAllById(createReservations.getScheduleIds());

    // 예약 가능 여부 확인 (기본 최대 인원 1명)
    if (scheduleEntities.stream()
        .anyMatch(scheduleEntity -> scheduleEntity.getReservationCount() > 1)) {
      throw new LmsException(LmsErrorCode.SCHEDULE_ALREADY_RESERVED);
    }

    // 코스 날짜 범위 벗어나는지 체크
    if (scheduleEntities.stream()
        .anyMatch(scheduleEntity ->
            scheduleEntity.getDate().isBefore(courseEntity.getStartDate()) ||
            scheduleEntity.getDate().isAfter(courseEntity.getEndDate()))) {
      throw new LmsException(LmsErrorCode.SCHEDULE_OUTSIDE_COURSE_DATE_RANGE);
    }

    // 예약 엔티티 생성
    List<ReservationEntity> reservationEntities =
        scheduleEntities.stream()
            .map(
                scheduleEntity -> {
                  ReservationEntity reservationEntity = new ReservationEntity();
                  reservationEntity.setDate(scheduleEntity.getDate());
                  reservationEntity.setStartTime(scheduleEntity.getStartTime());
                  reservationEntity.setEndTime(scheduleEntity.getStartTime().plusMinutes(30L));
                  reservationEntity.setAttendanceStatus(AttendanceStatus.R);
                  reservationEntity.setCourseEntity(courseEntity);
                  reservationEntity.setUserEntity(userEntity);
                  reservationEntity.setTeacherEntity(scheduleEntity.getTeacherEntity());
                  reservationEntity.setProductEntity(
                      courseEntity.getOrderProductEntity().getProductEntity());
                  reservationEntity.setCreatedBy(createReservations.getCreatedBy());

                  return reservationEntity;
                })
            .toList();

    // 예약 엔티티 저장
    reservationRepository.saveAll(reservationEntities);

    // 예약 수 업데이트 (예약 생성 후 처리)
    scheduleEntities.forEach(
        scheduleEntity ->
            scheduleEntity.setReservationCount(scheduleEntity.getReservationCount() + 1));

    // 제품 엔티티 조회
    ProductEntity productEntity = courseEntity.getOrderProductEntity().getProductEntity();
    if (productEntity == null) {
      throw new LmsException(LmsErrorCode.PRODUCT_NOT_FOUND);
    }

    // 배정 수 계산 (CurriculumYN과 ShortCourseYN에 따른 계산)
    Float assignmentCount;
    if (productEntity.getCurriculumYN() == YN.Y && productEntity.getShortCourseYN() == YN.Y) {
      assignmentCount = courseEntity.getAssignmentCount() + (float) reservationEntities.size();
    } else {
      assignmentCount = courseEntity.getAssignmentCount() + (float) reservationEntities.size() / 2; // 스케줄 하나당 배정 수 0.5
    }
    courseEntity.setAssignmentCount(assignmentCount);
    courseEntity.setIsReservation(YN.Y);

    // 수강 이력 추가
    String content = "총배정수: " + courseEntity.getAssignmentCount();
    CourseHistoryEntity courseHistoryEntity =
        CourseHistoryEntity.addCourseHistoryEntity(
            courseEntity,
            CourseHistoryModule.COURSE_USER,
            CourseHistoryType.ASSIGNMENT.getCode(),
            content,
            createReservations.getCreatedBy());
    courseEntity.getCourseHistoryEntities().add(courseHistoryEntity);

    // 코스 및 이력 저장
    courseRepository.save(courseEntity);
  }

  public Course getCourse(String userId, long id) {
    LocalDate now = LocalDate.now();

    CourseEntity courseEntity =
        courseRepository
            .findById(id)
            .filter(entity -> entity.getUserEntity().getId().equals(userId))
            .orElseThrow(() -> new LmsException(LmsErrorCode.COURSE_NOT_FOUND));

    // 환불이 있다면 CANCEL(취소)
    CourseStatus status =
        !courseEntity.getOrderProductEntity().getRefundEntities().isEmpty()
            ? CourseStatus.CANCEL
            :
                // 수강시작일이 현재 이후이고, 레슨횟수 > 배정횟수면 WAITING(대기)
                courseEntity.getStartDate().isAfter(now)
                && courseEntity.getLessonCount() > courseEntity.getAssignmentCount()
                    ? CourseStatus.WAITING
                    :
                        // 그 외에는 NORMAL(정상)
                        CourseStatus.NORMAL;

    return userAdminServiceMapper.toCourse(courseEntity, status);
  }

  @Transactional
  public void updateCourse(UpdateCourse updateCourse) {
    courseRepository
        .findById(updateCourse.getId())
        .filter(
            courseEntity -> courseEntity.getUserEntity().getId().equals(updateCourse.getUserId()))
        .ifPresentOrElse(
            courseEntity -> {
              // 코스 기간 외 예약 확인
              boolean hasReservationsOutsideCourseDate =
                  reservationRepository.existsReservationOutsideCourseDate(
                      courseEntity.getId(), updateCourse.getStartDate(), updateCourse.getEndDate());

              if (hasReservationsOutsideCourseDate) {
                throw new LmsException(LmsErrorCode.RESERVATIONS_EXIST_OUTSIDE_COURSE_DATE);
              }

              String content = "";
              if (updateCourse.getLessonCount() != courseEntity.getLessonCount()) {
                String countChangeReason =
                    updateCourse.getCountChangeReason() != null
                        ? " ( " + updateCourse.getCountChangeReason() + " )"
                        : "";
                content +=
                    "레슨횟수 : "
                    + courseEntity.getLessonCount()
                    + " -> "
                    + updateCourse.getLessonCount()
                    + countChangeReason
                    + "\n";
              }
              if (!updateCourse.getStartDate().equals(courseEntity.getStartDate())) {
                String dateChangeReason =
                    updateCourse.getDateChangeReason() != null
                        ? " ( " + updateCourse.getDateChangeReason() + " )"
                        : "";
                content +=
                    "수강시작일 : "
                    + courseEntity.getStartDate()
                    + " -> "
                    + updateCourse.getStartDate()
                    + dateChangeReason
                    + "\n";
              }
              if (!updateCourse.getEndDate().equals(courseEntity.getEndDate())) {
                String dateChangeReason =
                    updateCourse.getDateChangeReason() != null
                        ? " ( " + updateCourse.getDateChangeReason() + " )"
                        : "";
                content +=
                    "수강종료일 : "
                    + courseEntity.getEndDate()
                    + " -> "
                    + updateCourse.getEndDate()
                    + dateChangeReason
                    + "\n";
              }
              if (!updateCourse
                  .getTeacherId()
                  .equals(courseEntity.getTeacherEntity().getUserId())) {
                String updateName =
                    userRepository
                        .findById(updateCourse.getTeacherId())
                        .map(UserEntity::getName)
                        .orElse("");
                String beforeName =
                    userRepository
                        .findById(courseEntity.getTeacherEntity().getUserId())
                        .map(UserEntity::getName)
                        .orElse("");
                content += "담당강사 : " + beforeName + " -> " + updateName + "\n";
              }

              CourseHistoryEntity courseHistoryEntity =
                  CourseHistoryEntity.addCourseHistoryEntity(
                      courseEntity,
                      CourseHistoryModule.COURSE_USER,
                      CourseHistoryType.CHANGE.getCode(),
                      content,
                      updateCourse.getModifiedBy());
              courseHistoryRepository.save(courseHistoryEntity);
              userAdminServiceMapper.mapCourseEntity(updateCourse, courseEntity);

              TeacherEntity teacherEntity =
                  teacherRepository
                      .findById(updateCourse.getTeacherId())
                      .orElseThrow(() -> new LmsException(LmsErrorCode.TEACHER_NOT_FOUND));

              courseEntity.setTeacherEntity(teacherEntity);

              if (updateCourse.getAssistantTeacherId() != null) {
                TeacherEntity assistantTeacherEntity =
                    teacherRepository
                        .findById(updateCourse.getAssistantTeacherId())
                        .orElseThrow(() -> new LmsException(LmsErrorCode.TEACHER_NOT_FOUND));

                courseEntity.setAssistantTeacherEntity(assistantTeacherEntity);
              }
            },
            () -> {
              throw new LmsException(LmsErrorCode.COURSE_NOT_FOUND);
            });
  }

  public List<MemberConsultation> listMemberConsultations(String userId) {
    List<MemberConsultationEntity> memberConsultationEntities =
        memberConsultationRepository.findAllByUserEntity_Id(userId);
    List<UserEntity> creators =
        userRepository.findAllByIdIn(
            memberConsultationEntities.stream()
                .map(MemberConsultationEntity::getCreatedBy)
                .distinct()
                .toList());

    // topFixedYn 기준으로 우선 정렬하고, 그 다음 consultationDate 기준으로 내림차순 정렬
    List<MemberConsultationEntity> sortedEntities =
        memberConsultationEntities.stream()
            .sorted(
                (e1, e2) -> {
                  // topFixedYn이 'Y'인 항목을 우선적으로 정렬
                  if (e1.getTopFixedYn() == YN.Y && e2.getTopFixedYn() != YN.Y) {
                    return -1; // e1이 e2보다 먼저 오도록
                  } else if (e1.getTopFixedYn() != YN.Y && e2.getTopFixedYn() == YN.Y) {
                    return 1; // e2가 e1보다 먼저 오도록
                  } else {
                    // topFixedYn이 동일한 경우 consultationDate 기준으로 내림차순 정렬
                    return e2.getConsultationDate().compareTo(e1.getConsultationDate());
                  }
                })
            .toList();

    return sortedEntities.stream()
        .map(
            memberConsultationEntity ->
                userAdminServiceMapper.toMemberConsultation(
                    memberConsultationEntity,
                    creators.stream()
                        .filter(
                            userEntity ->
                                userEntity.getId().equals(memberConsultationEntity.getCreatedBy()))
                        .findFirst()
                        .map(UserEntity::getName)
                        .orElse(null)))
        .toList();
  }

  public MemberConsultation getMemberConsultation(String userId, long id) {
    return memberConsultationRepository
        .findById(id)
        .filter(
            memberConsultationEntity ->
                memberConsultationEntity.getUserEntity().getId().equals(userId))
        .map(
            memberConsultationEntity ->
                userAdminServiceMapper.toMemberConsultation(memberConsultationEntity, null))
        .orElseThrow(() -> new LmsException(LmsErrorCode.CONSULTATION_NOT_FOUND));
  }

  @Transactional
  public void createMemberConsultation(CreateMemberConsultation createMemberConsultation) {
    UserEntity userEntity =
        userRepository
            .findById(createMemberConsultation.getUserId())
            .orElseThrow(() -> new LmsException(LmsErrorCode.USER_NOT_FOUND));

    MemberConsultationEntity memberConsultationEntity =
        userAdminServiceMapper.toMemberConsultationEntity(createMemberConsultation);
    memberConsultationEntity.setUserEntity(userEntity);

    memberConsultationRepository.save(memberConsultationEntity);
  }

  @Transactional
  public void updateMemberConsultation(UpdateMemberConsultation updateMemberConsultation) {
    memberConsultationRepository
        .findById(updateMemberConsultation.getId())
        .filter(
            memberConsultationEntity ->
                memberConsultationEntity
                    .getUserEntity()
                    .getId()
                    .equals(updateMemberConsultation.getUserId()))
        .ifPresentOrElse(
            memberConsultationEntity ->
                userAdminServiceMapper.mapMemberConsultationEntity(
                    updateMemberConsultation, memberConsultationEntity),
            () -> {
              throw new LmsException(LmsErrorCode.CONSULTATION_NOT_FOUND);
            });
  }

  @Transactional
  public void deleteMemberConsultation(long id, String userId) {
    memberConsultationRepository
        .findById(id)
        .filter(
            memberConsultationEntity ->
                memberConsultationEntity.getUserEntity().getId().equals(userId))
        .ifPresentOrElse(
            memberConsultationRepository::delete,
            () -> {
              throw new LmsException(LmsErrorCode.CONSULTATION_NOT_FOUND);
            });
  }

  public List<Order> listOrders(String userId) {
    List<OrderEntity> orderEntities = orderRepository.findAllByUserEntity_Id(userId);
    List<UserEntity> creators =
        userRepository.findAllByIdIn(
            orderEntities.stream().map(OrderEntity::getCreatedBy).distinct().toList());

    return orderEntities.stream()
        .map(
            orderEntity ->
                userAdminServiceMapper.toOrder(
                    orderEntity,
                    creators.stream()
                        .filter(userEntity -> userEntity.getId().equals(orderEntity.getCreatedBy()))
                        .findFirst()
                        .map(UserEntity::getName)
                        .orElse(null)))
        .toList();
  }

  public Order getOrder(String userId, String id) {
    return orderRepository
        .findById(id)
        .filter(orderEntity -> orderEntity.getUserEntity().getId().equals(userId))
        .map(
            orderEntity -> {
              log.debug("{}", orderEntity);
              List<OrderProduct> orderProducts =
                  orderEntity.getOrderProductEntities().stream()
                      .map(
                          orderProductEntity -> {
//                            int supplyAmount =
//                                orderProductEntity.getAmount() * orderProductEntity.getQuantity();
                            int supplyAmount = orderProductEntity.getAmount();
                            int billingAmount =
                                orderProductEntity.getPaymentAmount()
                                - orderProductEntity.getDiscountAmount();
                            return userAdminServiceMapper.toOrderProduct(
                                orderProductEntity,
                                Optional.ofNullable(orderProductEntity.getCourseEntities())
                                    .orElseGet(List::of)
                                    .stream()
                                    .anyMatch(
                                        courseEntity ->
                                            YN.Y.equals(courseEntity.getIsReservation())),
                                supplyAmount,
                                billingAmount);
                          })
                      .toList();

              return userAdminServiceMapper.toOrder(orderEntity, orderProducts);
            })
        .orElseThrow(() -> new LmsException(LmsErrorCode.ORDER_NOT_FOUND));
  }

  /*
  *   public Order getOrder(String userId, String id) {
    return orderRepository.findById(id)
        .filter(orderEntity -> orderEntity.getUserEntity().getId().equals(userId))
        .map(orderEntity -> {
          List<OrderProduct> orderProducts = orderEntity.getOrderProductEntities().stream()
              .map(orderProductEntity -> {
                int supplyAmount =
                    orderProductEntity.getAmount() * orderProductEntity.getQuantity();
                int billingAmount =
                    orderProductEntity.getPaymentAmount() - orderProductEntity.getDiscountAmount();
                OrderProduct orderProduct = userAdminServiceMapper.toOrderProduct(orderProductEntity,
                        Optional.ofNullable(orderProductEntity.getCourseEntities())
                                .orElseGet(List::of).stream()
                                .anyMatch(courseEntity -> YN.Y.equals(courseEntity.getIsReservation())),
                        supplyAmount, billingAmount);
                int totalRefundAmount = orderProductEntity.getTotalRefundAmount();
                orderProduct.setTotalRefundAmount(totalRefundAmount);
                return orderProduct;
              })
              .toList();

            int refundAmount = orderProducts.stream()
                    .mapToInt(OrderProduct::getTotalRefundAmount)
                    .sum();
          return userAdminServiceMapper.toOrderList(orderEntity, orderProducts , refundAmount);
        })
        .orElseThrow(() -> new LmsException(LmsErrorCode.ORDER_NOT_FOUND));
  }
  *
  *
  *
  * */

  /**
   * order(주문)와 order_product(주문상품)가 1:n으로 저장.
   * -> 여러 상품을 선택해서 한번에 주문할 수 있는 구조 수강 외 상품의 주문은 주문 데이터
   * 저장만 하고, 수강의 주문은 주문 데이터와 수강 데이터까지 저장
   * -> 수강탭과 연결 주문 처리 로직:
   * 1. 선택한 상품ID와 클라이언트에서 입력한 정보로 order_product 생성
   * 2. 생성한 order_product와 주문한 회원 정보로 order 데이터 생성
   * 3. 수강을 주문한 경우 course 데이터를 생성
   * 4.생성한 course 정보로 course_history 이력 데이터를 생성
   * -> 주문이나 상품이름 등이 content 컬럼으로 들어가 저장. 5. 모두 저장해 주문을 완료
   */
  @Transactional
  public String createOrderProduct(CreateOrderProduct createOrderProduct) {
    if (createOrderProduct.getQuantity() == 0) {
      createOrderProduct.setQuantity(1);
    }
    if (createOrderProduct.getMonths() == 0) {
      createOrderProduct.setMonths(1);
    }

    UserEntity userEntity =
        userRepository
            .findById(createOrderProduct.getUserId())
            .orElseThrow(() -> new LmsException(LmsErrorCode.USER_NOT_FOUND));

    ProductEntity productEntity =
        productRepository
            .findById(createOrderProduct.getProductId())
            .orElseThrow(() -> new LmsException(LmsErrorCode.PRODUCT_NOT_FOUND));

    // 강사정보 ,상품정보 등을 받아와 새로운 order_product 생성
    OrderProductEntity orderProductEntity =
        userAdminServiceMapper.toOrderProductEntity(
            createOrderProduct, LmsUtils.createOrderProductId(), productEntity.getPrice());
    orderProductEntity.setProductEntity(productEntity);

    OrderEntity orderEntity;
    // 주문이 처음인 경우
    if (ObjectUtils.isEmpty(createOrderProduct.getOrderId())) {
      /*
      orderEntity = userAdminServiceMapper.toOrderEntity(createOrderProduct, LmsUtils.createOrderId(), OrderType.F,
               productEntity.getPrice(), createOrderProduct.getBillingAmount());
       */
      // 수정 > 주문 등록 시 사용 (공급가액을 상품원가*횟수 로 변경)
      // 수정 > 주문 등록 시 사용 (공급가액을 상품원가로 변경)
      orderEntity =
          userAdminServiceMapper.toOrderEntity(
              createOrderProduct,
              LmsUtils.createOrderId(),
              OrderType.F,
//              (productEntity.getPrice()) * createOrderProduct.getQuantity(),
              productEntity.getPrice(),
              createOrderProduct.getBillingAmount());
      orderEntity.setUserEntity(userEntity);

      orderEntity.setOrderProductEntities(List.of(orderProductEntity)); // 모든 order_product를 저장
      // 주문이 처음이 아닌 경우
    } else {
      orderEntity =
          orderRepository
              .findById(createOrderProduct.getOrderId())
              .filter(
                  entity -> entity.getUserEntity().getId().equals(createOrderProduct.getUserId()))
              .orElseThrow(() -> new LmsException(LmsErrorCode.ORDER_NOT_FOUND));

      // 수정한 부분 : 공급가액은 기존 공급가액 + (추가 상품의 공급가액) * 수량
      orderEntity.setSupplyAmount(orderEntity.getSupplyAmount() + productEntity.getPrice());
//          orderEntity.getSupplyAmount()
//          + (productEntity.getPrice())
//            * createOrderProduct.getQuantity()); // 기존 주문 건의 가격 + (추가 상품의 가격)*횟수
      orderEntity.setDiscountAmount(
          orderEntity.getDiscountAmount() + createOrderProduct.getDiscountAmount());
      orderEntity.setBillingAmount(
          orderEntity.getBillingAmount() + createOrderProduct.getBillingAmount());
      orderEntity.setReceivableAmount(
          orderEntity.getReceivableAmount()
          + createOrderProduct.getBillingAmount()); // 미수금+ 추가 상품의 청구금액
      orderEntity.setModifiedBy(createOrderProduct.getCreatedBy());
      orderEntity.getOrderProductEntities().add(orderProductEntity);
    }

    orderProductEntity.setOrderEntity(orderEntity);

    // 수강을 주문한 경우 course 데이터를 생성
    if (YN.Y == productEntity.getCurriculumYN()) {
      LocalDate now = LocalDate.now();
      now = now.plusDays(7); // 수강시작일은 7일 후로 설정

      CourseEntity courseEntity = new CourseEntity();
      courseEntity.setLessonCount(createOrderProduct.getQuantity()); // 총수업횟수
      courseEntity.setStartDate(now); // 수강시작일
      courseEntity.setEndDate(now.plusMonths(3).minusDays(1)); // 수강종료일
      courseEntity.setIsCompletion(YN.N);
      courseEntity.setIsReservation(YN.N);
      courseEntity.setCreatedBy(createOrderProduct.getCreatedBy());
      courseEntity.setUserEntity(userEntity);
      courseEntity.setOrderProductEntity(orderProductEntity);

      if (createOrderProduct.getTeacherId() != null) {
        teacherRepository
            .findById(createOrderProduct.getTeacherId())
            .ifPresent(
                teacherEntity -> {
                  orderProductEntity.setTeacherEntity(teacherEntity);
                  courseEntity.setTeacherEntity(teacherEntity);
                });
      }

      if (createOrderProduct.getAssistantTeacherId() != null) {
        teacherRepository
            .findById(createOrderProduct.getAssistantTeacherId())
            .ifPresent(courseEntity::setAssistantTeacherEntity);
      }

      orderProductEntity.setCourseEntities(List.of(courseEntity));

      // 주문 시 상품명 등 주문건에 대한 내용이 course_history에 저장
      CourseHistoryEntity courseHistoryEntity = new CourseHistoryEntity();
      courseHistoryEntity.setModule(CourseHistoryModule.COURSE_USER);
      courseHistoryEntity.setType(CourseHistoryType.ORDER.getCode());
      courseHistoryEntity.setContent(
          OrderProduct.getCurriculumName(
              productEntity.getName(),
              createOrderProduct.getMonths(),
              createOrderProduct.getQuantity(),
              productEntity.getQuantityUnit()));
      courseHistoryEntity.setCreatedBy(userEntity.getId());
      courseHistoryEntity.setCourseEntity(courseEntity);

      courseEntity.setCourseHistoryEntities(List.of(courseHistoryEntity));
    }

    orderRepository.save(orderEntity);

    return orderEntity.getId();
  }

  @Transactional
  public Page<LdfDto> listLdfs(SearchUserLdfs searchUserLdfs) {
    QReservationEntity qReservationEntity = QReservationEntity.reservationEntity;
    QLdfEntity qLdfEntity = QLdfEntity.ldfEntity;

    BooleanExpression where = qReservationEntity.userEntity.id.eq(searchUserLdfs.getUserId());

    if (searchUserLdfs.getDate() != null) {
      where = where.and(qReservationEntity.date.eq(searchUserLdfs.getDate()));
    }

    Sort sort = Sort.by(Sort.Order.desc("date"), Sort.Order.desc("startTime"));
    Page<ReservationEntity> reservationEntities =
        reservationRepository.findAll(
            where, PageRequest.of(searchUserLdfs.getPage() - 1, searchUserLdfs.getLimit(), sort));

    return reservationEntities.map(
        reservationEntity -> {
          // Reservation : 예약 여부
          Reservation reservation =
              userAdminServiceMapper.toReservation(reservationEntity); // Reservation

          LdfEntity ldfEntity =
              ldfRepository.findByReservationEntity_Id(reservation.getId()).orElse(null);
          Ldf ldf = ldfEntity == null ? null : userAdminServiceMapper.toLdf(ldfEntity);
          String email = null;
          if (ldf != null) {
            if (emailRepository.existsByLdfId(ldf.getId())) {
              email = "SENT";
            }
          }

          return LdfDto.builder()
              .reservation(reservation)
              .ldf(ldf)
              .email(email)
              .build();
        });
  }

  public Ldf getLdf(String userId, long id) {
    return ldfRepository
        .findById(id)
        .filter(ldfEntity -> ldfEntity.getUserEntity().getId().equals(userId))
        .map(ldfEntity -> userAdminServiceMapper.toLdf(ldfEntity))
        .orElseThrow(() -> new LmsException(LmsErrorCode.LDF_NOT_FOUND));
  }

  @Transactional
  public void createLdf(CreateUserLdf createUserLdf) {
    LdfEntity ldfEntity = userAdminServiceMapper.toLdfEntity(createUserLdf);
    ldfRepository.save(ldfEntity);
  }

  @Transactional
  public void updateLdf(long id, UpdateLdf updateLdf) {
    LdfEntity ldfEntity =
        ldfRepository.findById(id).orElseThrow(() -> new LmsException(LmsErrorCode.LDF_NOT_FOUND));
    ldfEntity.setLesson(updateLdf.getLesson());
    ldfEntity.setContentC(updateLdf.getContentC());
    ldfEntity.setContentSg(updateLdf.getContentSg());
    ldfEntity.setContentSp(updateLdf.getContentSp());
    ldfEntity.setContentV(updateLdf.getContentV());

    ldfRepository.save(ldfEntity);
  }

  public List<LevelTest> listLevelTests(String userId) {
    List<LevelTestEntity> levelTestEntities = levelTestRepository.findAllByUserEntity_Id(userId);
    return levelTestEntities.stream()
        .map(levelTestEntity -> userAdminServiceMapper.toLevelTest(levelTestEntity))
        .collect(Collectors.toList());
  }

  /**
   * 테스트 조회
   */
  public GetUserLevelTestResponse getLevelTest(String userId, Long id) {

    LevelTestEntity levelTestEntity =
        levelTestRepository
            .findById(id)
            .filter(entity -> entity.getUserEntity().getId().equals(userId))
            .orElseThrow(() -> new LmsException(LmsErrorCode.LEVELTEST_NOT_FOUND));

    MultipartFile file = null;
    GetUserLevelTest getUserLevelTest =
        userAdminServiceMapper.toGetUserLevelTest(
            levelTestEntity,
            fileService.getUrl(levelTestEntity.getFile(), levelTestEntity.getOriginalFile()));

    // string -> enum 배열로 변환
    List<TestStudyType> studyType =
        getUserLevelTest.getStudyType() != null
            ? serviceMapper.toTestStudyTypeList(getUserLevelTest.getStudyType())
            : Collections.emptyList();

    List<TestConsonants> consonants =
        getUserLevelTest.getConsonants() != null
            ? serviceMapper.toTestConsonantsList(getUserLevelTest.getConsonants())
            : Collections.emptyList();

    List<TestVowels> vowels =
        getUserLevelTest.getVowels() != null
            ? serviceMapper.toTestVowelsList(getUserLevelTest.getVowels())
            : Collections.emptyList();

    List<TestRecommendLevel> recommendedLevel =
        getUserLevelTest.getRecommendedLevel() != null
            ? serviceMapper.toTestRecommendLevelList(getUserLevelTest.getRecommendedLevel())
            : Collections.emptyList();

    GetUserLevelTestResponse getUserLevelTestResponse =
        GetUserLevelTestResponse.builder()
            .studyType(studyType)
            .consonants(consonants)
            .vowels(vowels)
            .recommendedLevel(recommendedLevel)
            .levelTest(getUserLevelTest)
            .build();

    return getUserLevelTestResponse;
  }

  /**
   * 테스트 삭제
   */
  @Transactional
  public void deleteLeverTest(String userId, Long id) {
    levelTestRepository.deleteById(id);
  }

  /**
   * 테스트 등록
   */
  @Transactional
  public void createUserTest(CreateUserLevelTest createTest) {
    UserEntity userEntity =
        userRepository
            .findById(createTest.getUserId())
            .orElseThrow(() -> new LmsException(LmsErrorCode.USER_NOT_FOUND));

    String file = null;
    if (createTest.getMultipartFile() != null) {
      createTest.setFileSize(createTest.getMultipartFile().getSize());
      file = fileService.upload(createTest.getMultipartFile());
    }

    LevelTestEntity levelTestEntity = new LevelTestEntity();

    levelTestEntity.setTestStartTime(createTest.getTestStartTime());
    levelTestEntity.setTestEndTime(createTest.getTestEndTime());
    levelTestEntity.setLbt(createTest.getLbt());
    levelTestEntity.setRbt(createTest.getRbt());
    levelTestEntity.setObt(createTest.getObt());
    levelTestEntity.setTestIp(createTest.getTestIp());
    levelTestEntity.setFile(file);
    levelTestEntity.setOriginalFile(createTest.getOriginalFile());
    levelTestEntity.setNote(createTest.getNote());
    levelTestEntity.setPurpose(createTest.getPurpose());
    levelTestEntity.setStudyType(
        String.join(
            ",",
            createTest.getStudyType().stream()
                .map(TestStudyType::getCode)
                .collect(Collectors.toList())));
    levelTestEntity.setStudyTypeEtc(createTest.getStudyTypeEtc());
    levelTestEntity.setFamilyBackground(createTest.getFamilyBackground());
    levelTestEntity.setNote(createTest.getNote());
    levelTestEntity.setPurpose(createTest.getPurpose());
    levelTestEntity.setStudyType(
        String.join(
            ",",
            createTest.getStudyType().stream()
                .map(TestStudyType::getCode)
                .collect(Collectors.toList())));
    levelTestEntity.setStudyTypeEtc(createTest.getStudyTypeEtc());
    levelTestEntity.setFamilyBackground(createTest.getFamilyBackground());
    levelTestEntity.setUsageType(createTest.getUsageType());
    levelTestEntity.setOccupation(createTest.getOccupation());
    levelTestEntity.setSpareTime(createTest.getSpareTime());
    levelTestEntity.setTravelAbroad(createTest.getTravelAbroad());
    levelTestEntity.setFuturePlans(createTest.getFuturePlans());
    levelTestEntity.setConsonants(
        String.join(
            ",",
            createTest.getConsonants().stream()
                .map(TestConsonants::getCode)
                .collect(Collectors.toList())));
    levelTestEntity.setVowels(
        String.join(
            ",",
            createTest.getVowels().stream().map(TestVowels::getCode).collect(Collectors.toList())));
    levelTestEntity.setClarity(createTest.getClarity());
    levelTestEntity.setIntonation(createTest.getIntonation());
    levelTestEntity.setVocabulary(createTest.getVocabulary());
    levelTestEntity.setVerbsTense(createTest.getVerbsTense());
    levelTestEntity.setAgreement(createTest.getAgreement());
    levelTestEntity.setPrepositions(createTest.getPrepositions());
    levelTestEntity.setArticles(createTest.getArticles());
    levelTestEntity.setPlurals(createTest.getPlurals());
    levelTestEntity.setOthers(createTest.getOthers());
    levelTestEntity.setStrongPoint(createTest.getStrongPoint());
    levelTestEntity.setWeakPoint(createTest.getWeakPoint());
    levelTestEntity.setComprehension(createTest.getComprehension());
    levelTestEntity.setConfidence(createTest.getConfidence());
    levelTestEntity.setComments(createTest.getComments());
    levelTestEntity.setRecommendedLevel(
        String.join(
            ",",
            createTest.getRecommendedLevel().stream()
                .map(TestRecommendLevel::getCode)
                .collect(Collectors.toList())));
    levelTestEntity.setRecommendedLevelEtc(createTest.getRecommendedLevelEtc());
    levelTestEntity.setInterviewer(createTest.getInterviewer());
    levelTestEntity.setUserEntity(userEntity);
    levelTestEntity.setCreatedBy(createTest.getCreatedBy());
    levelTestEntity.setFileSize(createTest.getFileSize());
    levelTestRepository.save(levelTestEntity);
  }

  /**
   * 테스트 수정
   */
  @Transactional
  public void updateLevelTest(UpdateUserLevelTest updateUserLevelTest) {
    String file = null;
    // 수정의 경우 첨부 파일이 존재하는 경우는 삭제 후 새로 첨부한 경우만 존재함
    if (updateUserLevelTest.getMultipartFile() != null
        && Boolean.FALSE.equals(updateUserLevelTest.getIsDeleteFile())) {
      throw new LmsException(LmsErrorCode.BAD_REQUEST, "isDeleteFile");
    }

    // 파일을 새로 첨부한 경우
    if (updateUserLevelTest.getMultipartFile() != null
        && Boolean.TRUE.equals(updateUserLevelTest.getIsDeleteFile())) {
      updateUserLevelTest.setFileSize(updateUserLevelTest.getMultipartFile().getSize());
      file = fileService.upload(updateUserLevelTest.getMultipartFile());
    } else {
      updateUserLevelTest.setFileSize(null);
    }

    UserEntity userEntity =
        userRepository
            .findById(updateUserLevelTest.getUserId())
            .orElseThrow(() -> new LmsException(LmsErrorCode.USER_NOT_FOUND));

    LevelTestEntity levelTestEntity =
        levelTestRepository
            .findById(updateUserLevelTest.getId())
            .orElseThrow(() -> new LmsException(LmsErrorCode.LEVELTEST_NOT_FOUND));

    // 파일을 삭제한 경우
    if (updateUserLevelTest.getIsDeleteFile()) {
      levelTestEntity.setFile(null);
      levelTestEntity.setOriginalFile(null);
      // TODO 기존 업로드한 파일 삭제 로직 추가
      log.warn("기존에 첨부한 파일을 삭제하는 로직을 구현하세요!");
    }

    // 수정시 파일이 존재하는 경우는 파일을 삭제 후 다시 첨부한 경우만 존재한다.
    if (file != null && Boolean.TRUE.equals(updateUserLevelTest.getIsDeleteFile())) {
      levelTestEntity.setFile(file);
      levelTestEntity.setOriginalFile(updateUserLevelTest.getOriginalFile());
      levelTestEntity.setFileSize(updateUserLevelTest.getFileSize());
    }

    String studyTypeList =
        convertListToString(updateUserLevelTest.getStudyType(), TestStudyType::getCode);
    String consonantsList =
        convertListToString(updateUserLevelTest.getConsonants(), TestConsonants::getCode);
    String vowelsList = convertListToString(updateUserLevelTest.getVowels(), TestVowels::getCode);
    String recommendedLevelList =
        convertListToString(updateUserLevelTest.getRecommendedLevel(), TestRecommendLevel::getCode);

    levelTestEntity.setTestStartTime(updateUserLevelTest.getTestStartTime());
    levelTestEntity.setTestEndTime(updateUserLevelTest.getTestEndTime());
    levelTestEntity.setInterviewer(updateUserLevelTest.getInterviewer());
    levelTestEntity.setLbt(updateUserLevelTest.getLbt());
    levelTestEntity.setRbt(updateUserLevelTest.getRbt());
    levelTestEntity.setObt(updateUserLevelTest.getObt());
    levelTestEntity.setTestIp(updateUserLevelTest.getTestIp());
    levelTestEntity.setNote(updateUserLevelTest.getNote());
    levelTestEntity.setPurpose(updateUserLevelTest.getPurpose());
    levelTestEntity.setStudyType(studyTypeList);
    levelTestEntity.setStudyTypeEtc(updateUserLevelTest.getStudyTypeEtc());
    levelTestEntity.setFamilyBackground(updateUserLevelTest.getFamilyBackground());
    levelTestEntity.setUsageType(updateUserLevelTest.getUsageType());
    levelTestEntity.setOccupation(updateUserLevelTest.getOccupation());
    levelTestEntity.setSpareTime(updateUserLevelTest.getSpareTime());
    levelTestEntity.setTravelAbroad(updateUserLevelTest.getTravelAbroad());
    levelTestEntity.setFuturePlans(updateUserLevelTest.getFuturePlans());
    levelTestEntity.setConsonants(consonantsList);
    levelTestEntity.setVowels(vowelsList);
    levelTestEntity.setClarity(updateUserLevelTest.getClarity());
    levelTestEntity.setIntonation(updateUserLevelTest.getIntonation());
    levelTestEntity.setVocabulary(updateUserLevelTest.getVocabulary());
    levelTestEntity.setVerbsTense(updateUserLevelTest.getVerbsTense());
    levelTestEntity.setAgreement(updateUserLevelTest.getAgreement());
    levelTestEntity.setPrepositions(updateUserLevelTest.getPrepositions());
    levelTestEntity.setArticles(updateUserLevelTest.getArticles());
    levelTestEntity.setPlurals(updateUserLevelTest.getPlurals());
    levelTestEntity.setOthers(updateUserLevelTest.getOthers());
    levelTestEntity.setStrongPoint(updateUserLevelTest.getStrongPoint());
    levelTestEntity.setWeakPoint(updateUserLevelTest.getWeakPoint());
    levelTestEntity.setComprehension(updateUserLevelTest.getComprehension());
    levelTestEntity.setConfidence(updateUserLevelTest.getConfidence());
    levelTestEntity.setRecommendedLevel(recommendedLevelList);
    levelTestEntity.setRecommendedLevelEtc(updateUserLevelTest.getRecommendedLevelEtc());
    levelTestEntity.setUserEntity(userEntity);
    levelTestEntity.setModifiedBy(updateUserLevelTest.getModifiedBy());
    levelTestRepository.save(levelTestEntity);
  }

  public Order getOrderWithPayments(String orderId) {
    OrderEntity orderEntity =
        orderRepository
            .findById(orderId)
            .orElseThrow(() -> new LmsException(LmsErrorCode.ORDER_NOT_FOUND));

    // 환불 테이블과 연관
    List<OrderProduct> orderProducts =
        orderEntity.getOrderProductEntities().stream()
            .map(
                orderProductEntity ->
                    userAdminServiceMapper.toOrderProduct(
                        orderProductEntity,
                        orderProductEntity.getRefundEntities().stream()
                            .map(
                                refundEntity ->
                                    userAdminServiceMapper.toRefund(
                                        refundEntity,
                                        userRepository
                                            .findById(refundEntity.getModifiedBy())
                                            .map(UserEntity::getName)
                                            .orElse(null)))
                            .collect(Collectors.toList())))
            .toList();
    // 결제 테이블 연관
    List<Payment> payments =
        orderEntity.getPaymentEntities().stream()
            .map(
                paymentEntity -> {
                  String cardCompanyName =
                      commonCodeRepository
                          .findByCode(paymentEntity.getCardCompany())
                          .map(CommonCodeEntity::getName)
                          .orElse(null);

                  return userAdminServiceMapper.toListPayment(
                      paymentEntity,
                      userRepository
                          .findById(paymentEntity.getModifiedBy())
                          .map(UserEntity::getName)
                          .orElse(null),
                      cardCompanyName);
                })
            .toList();

    return userAdminServiceMapper.toOrder(orderEntity, orderProducts, payments);
  }

  /**
   * 결제 처리가 불가능한 경우: 클라이언트에서 입력한 결제 금액이 주문의 미수금보다 클때 결제 수단에 따라 저장되는 데이터가 다름 결제 처리 로직 : payment(생성)와
   * order(업데이트) 연관
   */
  @Transactional
  public void createOrderPayments(CreateOrderPayments createOrderPayments) {
    // 총 결제 금액은 0보다 커야됨
    if (createOrderPayments.getTotalPaymentAmount() <= 0) {
      throw new LmsException(LmsErrorCode.PAYMENT_AMOUNT_NOT_POSITIVE);
    }

    orderRepository
        .findById(createOrderPayments.getOrderId())
        .filter(entity -> entity.getUserEntity().getId().equals(createOrderPayments.getUserId()))
        .ifPresentOrElse(
            orderEntity -> {
              // 미수금 < 총 지불 금액
              if (orderEntity.getReceivableAmount() < createOrderPayments.getTotalPaymentAmount()) {
                throw new LmsException(LmsErrorCode.PAYMENT_AMOUNT_EXCEEDED);
              }
              // 받아야할 미수금액 - 총 받은 금액 != 받은 미수금액
              if (orderEntity.getReceivableAmount() - createOrderPayments.getTotalPaymentAmount()
                  != createOrderPayments.getReceivableAmount()) {
                throw new LmsException(LmsErrorCode.RECEIVABLE_AMOUNT_NOT_MATCH);
              }

              // 현금
              if (createOrderPayments.getCashAmount() > 0) {
                PaymentEntity paymentEntity = new PaymentEntity(); // payment 테이블 생성
                paymentEntity.setId(LmsUtils.createPaymentId());
                paymentEntity.setUserEntity(orderEntity.getUserEntity());
                paymentEntity.setOrderEntity(orderEntity);
                paymentEntity.setPaymentDate(createOrderPayments.getPaymentDate());
                paymentEntity.setType(createOrderPayments.getType());
                paymentEntity.setPaymentMethod(PaymentMethod.CASH);
                paymentEntity.setPaymentAmount(createOrderPayments.getCashAmount());
                paymentEntity.setMemo(createOrderPayments.getMemo());
                paymentEntity.setIsReceiptIssued(YN.of(createOrderPayments.isReceiptIssued()));
                paymentEntity.setReceiptNumber(createOrderPayments.getReceiptNumber());
                paymentEntity.setCreatedBy(createOrderPayments.getCreatedBy());
                // order(주문)에 누적 더하기
                orderEntity.getPaymentEntities().add(paymentEntity);
              }

              // 예금
              if (createOrderPayments.getDepositAmount() > 0) {
                PaymentEntity paymentEntity = new PaymentEntity();
                paymentEntity.setId(LmsUtils.createPaymentId());
                paymentEntity.setUserEntity(orderEntity.getUserEntity());
                paymentEntity.setOrderEntity(orderEntity);
                paymentEntity.setPaymentDate(createOrderPayments.getPaymentDate());
                paymentEntity.setType(createOrderPayments.getType());
                paymentEntity.setPaymentMethod(PaymentMethod.BANK);
                paymentEntity.setPaymentAmount(createOrderPayments.getDepositAmount());
                paymentEntity.setAccountHolder(createOrderPayments.getAccountHolder());
                paymentEntity.setMemo(createOrderPayments.getMemo());
                paymentEntity.setCreatedBy(createOrderPayments.getCreatedBy());

                orderEntity.getPaymentEntities().add(paymentEntity);
              }

              // 카드 (여러 카드 사용 시에 payment 테이블에 각각 하나씩 추가됨)
              if (ObjectUtils.isNotEmpty(createOrderPayments.getCards())) {
                createOrderPayments
                    .getCards()
                    .forEach(
                        card -> {
                          // 카드 종류 정보(코드) 검사
                          if (StringUtils.hasNotText(card.getCardCompany())) {
                            throw new LmsException(LmsErrorCode.BAD_REQUEST, "카드 종류 정보가 없습니다.");
                          }
                          // 카드 종류 코드 조회
                          CommonCodeEntity commonCode = commonCodeRepository.findByCode(card.getCardCompany())
                              .orElseThrow(() -> new LmsException(LmsErrorCode.COMMONCODE_NOT_FOUND));

                          PaymentEntity paymentEntity = new PaymentEntity();
                          paymentEntity.setId(LmsUtils.createPaymentId());
                          paymentEntity.setUserEntity(orderEntity.getUserEntity());
                          paymentEntity.setOrderEntity(orderEntity);
                          paymentEntity.setPaymentDate(createOrderPayments.getPaymentDate());
                          paymentEntity.setType(createOrderPayments.getType());
                          paymentEntity.setPaymentMethod(PaymentMethod.CARD);
                          paymentEntity.setPaymentAmount(card.getAmount());
                          paymentEntity.setCardCompany(card.getCardCompany()); // request cards.code 정보
                          paymentEntity.setTransactionName(commonCode.getName()); // 공통코드의 카드사명 저장(10월 10일 조원빈 요구)
                          paymentEntity.setCardNumber(card.getCardNumber());
                          paymentEntity.setInstallmentMonths(card.getInstallmentMonths());
                          paymentEntity.setApprovalNumber(card.getApprovalNumber());
                          paymentEntity.setMemo(createOrderPayments.getMemo());
                          paymentEntity.setCreatedBy(createOrderPayments.getCreatedBy());

                          orderEntity.getPaymentEntities().add(paymentEntity);
                        });
              }
              // 주문 업데이트
              orderEntity.setCashAmount(
                  orderEntity.getCashAmount()
                  + createOrderPayments.getCashAmount()); // 기존 현금금액 + 받은 현금금액
              orderEntity.setDepositAmount(
                  orderEntity.getDepositAmount()
                  + createOrderPayments.getDepositAmount()); // 예금 금액 + 받을
              orderEntity.setCardCount(
                  orderEntity.getCardCount() + createOrderPayments.getCards().size()); // 결제한 카드 수
              orderEntity.setCardAmount(
                  orderEntity.getCardAmount()
                  + createOrderPayments.getTotalCardAmount()); // 카드 금액 합계
              orderEntity.setPaymentAmount(
                  orderEntity.getPaymentAmount()
                  + createOrderPayments.getTotalPaymentAmount()); // 기존 결제금액 + 새로 받은 금액합계
              orderEntity.setReceivableAmount(
                  orderEntity.getReceivableAmount()
                  - createOrderPayments.getTotalPaymentAmount()); // 기존 미수금- 총 받은 금액
              orderEntity.setReceivableReason(createOrderPayments.getReceivableReason()); // 미수금사유
              orderEntity.setRecallDate(createOrderPayments.getRecallDate()); // 회수예정일
              orderEntity.setModifiedBy(createOrderPayments.getCreatedBy());

              // 추가 > 주문 관련 정보 update
              orderRepository.save(orderEntity);

              /*
              // 추가 > 결제가 모두 완료된 수강권(courseId)에 대해 수강 시작일과 종료일을 설정하고 courseEntity를 update
              if (orderEntity.getReceivableAmount() == 0) {
                  orderEntity.getOrderProductEntities().stream()
                          // 수강인 상품 필터링
                          .filter(orderProductEntity -> orderProductEntity.getCourseEntities().size() > 0)
                          .forEach(orderProductEntity -> {
                              orderProductEntity.getCourseEntities().forEach(courseEntity -> {
                                  // 결제일을 startDate로 설정
                                  LocalDate startDate = createOrderPayments.getPaymentDate();
                                  courseEntity.setStartDate(startDate);

                                  // endDate는 startDate로부터 orderProductEntity의 month를 더하고 -1 한 날짜로 설정
                                  if (startDate != null) {
                                      LocalDate endDate = startDate.plusMonths(orderProductEntity.getMonths()).minusDays(1);
                                      courseEntity.setEndDate(endDate);
                                  }
                                  courseRepository.save(courseEntity);
                              });
                          });
              }
              */
            },
            () -> {
              throw new LmsException(LmsErrorCode.ORDER_NOT_FOUND);
            });
  }

  /**
   * 취소가 불가능한 경우 : 이미 취소된 결제 결제 취소 로직 - 결제 취소 row 저장 (payment)-> payment에 type을 C , 결제금액 - , 메모 컬럼에
   * 결제ID 저장 - 원 결제 정보 업데이트(payment) -> 취소 금액, 취소 날짜, 처리담당자 수정 - 주문 정보 업데이트(order) -> 결제 금액을 취소한 금액
   * 만큼 뺌 , 미수금액을 취소한 금액만큼 더함.
   */
  @Transactional
  public void deleteOrderPayment(
      String userId, String orderId, String paymentId, String modifiedBy) {
    orderRepository
        .findById(orderId)
        .filter(orderEntity -> orderEntity.getUserEntity().getId().equals(userId))
        .ifPresentOrElse(
            orderEntity -> {
              PaymentEntity paymentEntity =
                  orderEntity.getPaymentEntities().stream()
                      .filter(entity -> entity.getId().equals(paymentId))
                      .findFirst()
                      .orElseThrow(() -> new LmsException(LmsErrorCode.PAYMENT_NOT_FOUND));
              // 취소되거나 환불된 건 취소 불가
              if (!Payment.isCancelable(paymentEntity.getType(), paymentEntity.getCancelDate())) {
                throw new LmsException(LmsErrorCode.PAYMENT_CANNOT_CANCEL);
              }
              // 로직1 : 결제 취소 row 저장 (payment)
              PaymentEntity cancelPaymentEntity =
                  userAdminServiceMapper.toCancelPaymentEntity(paymentEntity);
              cancelPaymentEntity.setId(LmsUtils.createPaymentId());
              cancelPaymentEntity.setPaymentDate(LocalDate.now());
              cancelPaymentEntity.setType(PaymentType.C);
              cancelPaymentEntity.setPaymentAmount(-paymentEntity.getPaymentAmount());
              cancelPaymentEntity.setMemo("#" + paymentEntity.getId());
              cancelPaymentEntity.setCreatedBy(modifiedBy);
              // 로직2 :원 결제 정보 업데이트(payment)
              paymentEntity.setCancelAmount(paymentEntity.getPaymentAmount());
              paymentEntity.setCancelDate(cancelPaymentEntity.getPaymentDate());
              paymentEntity.setCancelManager(modifiedBy);
              paymentEntity.setModifiedBy(modifiedBy);

              // 로직3: 주문 정보 업데이트(order)
              orderEntity.getPaymentEntities().add(cancelPaymentEntity);

              if (cancelPaymentEntity.getPaymentMethod() == PaymentMethod.CASH) {
                orderEntity.setCashAmount(
                    orderEntity.getCashAmount() - paymentEntity.getPaymentAmount());
              } else if (cancelPaymentEntity.getPaymentMethod() == PaymentMethod.BANK) {
                orderEntity.setDepositAmount(
                    orderEntity.getDepositAmount() - paymentEntity.getPaymentAmount());
              } else if (cancelPaymentEntity.getPaymentMethod() == PaymentMethod.CARD) {
                orderEntity.setCardCount(orderEntity.getCardCount() - 1);
                orderEntity.setCardAmount(
                    orderEntity.getCardAmount() - paymentEntity.getPaymentAmount());
              }

              orderEntity.setPaymentAmount(
                  orderEntity.getPaymentAmount() - paymentEntity.getPaymentAmount());
              orderEntity.setReceivableAmount(
                  orderEntity.getReceivableAmount() + paymentEntity.getPaymentAmount());
              orderEntity.setModifiedBy(modifiedBy);
            },
            () -> {
              throw new LmsException(LmsErrorCode.ORDER_NOT_FOUND);
            });
  }

  /**
   * 환불이 불가능한 경우 : 클라이언트에서 입력한 환불금액이 결제한 금액과 다를 때, 취소하지 않은 결제 내역이 있을 때, 예약한 수강이 있을때 환불 처리 로직 -
   * refund(생성) : 환불한 order_product와 환불날짜, 환불금액, 결제수단 별 정보로 refund를 생성 - order(업데이트)에 환불금액을 업데이트하고
   * 미수금액은 기존 미수금액에서 환불금액만큼 뺀 값을 업데이트 - payment(생성): 금액(-) 로 저장하고 type을 환불로 해서 저장
   */
  @Transactional
  public void createOrderRefund(CreateOrderRefund createOrderRefund) {
    if (createOrderRefund.getTotalRefundAmount() < 1) { // 결제금액이 없을때
      throw new LmsException(LmsErrorCode.REFUND_AMOUNT_NOT_POSITIVE);
    }

    orderProductRepository
        .findById(createOrderRefund.getOrderProductId())
        .filter(
            orderProductEntity ->
                orderProductEntity.getOrderEntity().getId().equals(createOrderRefund.getOrderId())
                && orderProductEntity
                    .getOrderEntity()
                    .getUserEntity()
                    .getId()
                    .equals(createOrderRefund.getUserId()))
        .ifPresentOrElse(
            orderProductEntity -> {
              OrderEntity orderEntity = orderProductEntity.getOrderEntity();
              List<CourseEntity> courseEntities = orderProductEntity.getCourseEntities();
              // (수정) 상품 총 가격과 해당 식별키에 해당하는 refund의 총 합이 다를 때 불가 > 해당 상품의 환불 금액을 초과했습니다
              if (orderEntity.getPaymentAmount() > createOrderRefund.getTotalRefundAmount()) {
                throw new LmsException(LmsErrorCode.PAYMENT_AMOUNT_EXCEEDED);
              }

              // (추가) 해당 상품의 환불 금액을 초과했을 때,
              if (orderProductEntity.getPaymentAmount()
                  < createOrderRefund.getTotalRefundAmount()) {
                throw new LmsException(LmsErrorCode.REFUND_AMOUNT_EXCEEDED);
              }
              // 환불금액이 결제한 금액과 다를 때,
              if (orderProductEntity.getPaymentAmount()
                  != createOrderRefund.getTotalRefundAmount()) {
                throw new LmsException(LmsErrorCode.REFUND_AMOUNT_NOT_MATCH);
              }

              // 취소하지 않은 결제 내역이 있을 때
              if (orderEntity.getPaymentAmount() > 0) {
                throw new LmsException(LmsErrorCode.PAYMENT_EXISTS);
              }
              // 예약한 수강이 있을때
              if (courseEntities.stream()
                  .anyMatch(courseEntity -> courseEntity.getIsReservation() == YN.Y)) {
                throw new LmsException(LmsErrorCode.RESERVATION_EXISTS);
              }

              RefundEntity refundEntity =
                  userAdminServiceMapper.toRefundEntity(
                      createOrderRefund, LmsUtils.createRefundId());
              refundEntity.setUserEntity(orderEntity.getUserEntity());
              refundEntity.setOrderProductEntity(orderProductEntity);

              orderProductEntity
                  .getRefundEntities()
                  .add(refundEntity); // 환불 방법에 따라 각각 refund 테이블에 저장

              orderEntity.setRefundAmount(refundEntity.getRefundAmount());
              orderEntity.setReceivableAmount(
                  orderEntity.getBillingAmount()
                  - orderEntity.getPaymentAmount()
                  - orderEntity.getRefundAmount());

              // 결제 테이블에 Payment 테이블에 추가
              // 현금
              if (createOrderRefund.getCashAmount() > 0) {
                PaymentEntity paymentEntity = new PaymentEntity(); // payment 테이블 생성
                paymentEntity.setId(LmsUtils.createPaymentId());
                paymentEntity.setUserEntity(orderEntity.getUserEntity());
                paymentEntity.setOrderEntity(orderEntity);
                paymentEntity.setPaymentDate(createOrderRefund.getRefundDate());
                paymentEntity.setType(PaymentType.R);
                paymentEntity.setPaymentMethod(PaymentMethod.CASH);
                paymentEntity.setPaymentAmount(-createOrderRefund.getCashAmount());
                paymentEntity.setMemo(createOrderRefund.getRefundReason());
                paymentEntity.setCreatedBy(createOrderRefund.getCreatedBy());
                paymentEntity.setCancelAmount(0);
                paymentEntity.setIsReceiptIssued(YN.N);
                paymentEntity.setModifiedBy(createOrderRefund.getCreatedBy());
                orderEntity.getPaymentEntities().add(paymentEntity);
              }

              // 예금
              if (createOrderRefund.getDepositAmount() > 0) {
                PaymentEntity paymentEntity = new PaymentEntity();
                paymentEntity.setId(LmsUtils.createPaymentId());
                paymentEntity.setUserEntity(orderEntity.getUserEntity());
                paymentEntity.setOrderEntity(orderEntity);
                paymentEntity.setPaymentDate(createOrderRefund.getRefundDate());
                paymentEntity.setType(PaymentType.R);
                paymentEntity.setPaymentMethod(PaymentMethod.BANK);
                paymentEntity.setPaymentAmount(-createOrderRefund.getDepositAmount());
                paymentEntity.setAccountNumber(createOrderRefund.getAccountNumber());
                paymentEntity.setMemo(createOrderRefund.getRefundReason());
                paymentEntity.setCreatedBy(createOrderRefund.getCreatedBy());
                paymentEntity.setCancelAmount(0);
                paymentEntity.setIsReceiptIssued(YN.N);
                paymentEntity.setModifiedBy(createOrderRefund.getCreatedBy());
                orderEntity.getPaymentEntities().add(paymentEntity);
              }
              // 카드 (여러 카드 사용 시에 payment 테이블에 각각 하나씩 추가됨)
              if (createOrderRefund.getCardAmount() > 0) {
                PaymentEntity paymentEntity = new PaymentEntity();
                paymentEntity.setId(LmsUtils.createPaymentId());
                paymentEntity.setUserEntity(orderEntity.getUserEntity());
                paymentEntity.setOrderEntity(orderEntity);
                paymentEntity.setPaymentDate(createOrderRefund.getRefundDate());
                paymentEntity.setType(PaymentType.R);
                paymentEntity.setPaymentMethod(PaymentMethod.CARD);
                paymentEntity.setPaymentAmount(-createOrderRefund.getCardAmount());
                paymentEntity.setMemo(createOrderRefund.getRefundReason());
                paymentEntity.setCreatedBy(createOrderRefund.getCreatedBy());
                paymentEntity.setCancelAmount(0);
                paymentEntity.setIsReceiptIssued(YN.N);
                paymentEntity.setModifiedBy(createOrderRefund.getCreatedBy());
                orderEntity.getPaymentEntities().add(paymentEntity);
              }
              ;
              courseEntities.clear();
            },
            () -> {
              throw new LmsException(LmsErrorCode.ORDER_NOT_FOUND);
            });
  }

  /**
   * 주문 취소 로직 경우1: 일부 주문한 상품에 대해서만 부분 취소 하는 경우 : 선택한 order_product 삭제 > order에 금액 변경 경우2: 주문 자체를
   * 취소하는 경우 : order삭제 + order_product 삭제 주문 삭제가 불가능한 경우 : 주문에 대한 결제 정보나 환불 정보가 있음. 이미 예약한 정보가 있음
   */
  @Transactional
  public void deleteOrderProduct(
      String userId, String orderId, String orderProductId, String modifiedBy) {
    orderProductRepository
        .findById(orderProductId)
        // 경우1과 경우2를 나누기 위함
        .filter(
            orderProductEntity ->
                orderProductEntity.getOrderEntity().getId().equals(orderId)
                && orderProductEntity.getOrderEntity().getUserEntity().getId().equals(userId))
        .ifPresentOrElse(
            orderProductEntity -> {
              // 삭제 불가능한 경우를 위함
              OrderEntity orderEntity = orderProductEntity.getOrderEntity();
              List<RefundEntity> refundEntities = orderProductEntity.getRefundEntities();
              List<CourseEntity> courseEntities = orderProductEntity.getCourseEntities();

              if (!orderEntity.getPaymentEntities().isEmpty() || !refundEntities.isEmpty()) {
                throw new LmsException(LmsErrorCode.PAYMENT_EXISTS);
              }

              if (courseEntities.stream()
                  .anyMatch(courseEntity -> courseEntity.getIsReservation() == YN.Y)) {
                throw new LmsException(LmsErrorCode.RESERVATION_EXISTS);
              }

              courseEntities.clear(); // pk-fk로 연결된거 삭제
              // 현재 주문이 한개일 경우 주문 자체가 삭제됨
              if (orderEntity.getOrderProductEntities().size() == 1) {
                orderProductRepository.delete(orderProductEntity);
                orderRepository.delete(orderEntity);
              } else {
                // 여러 주문 상품이 있으면 orderProductEntities에서 해당 상품을 제거하고 저장
                orderEntity.getOrderProductEntities().remove(orderProductEntity);

                // 일부 취소일 경우 가격만 수정됨
                orderEntity.setSupplyAmount(
                    orderEntity.getSupplyAmount() - orderProductEntity.getAmount());
                orderEntity.setDiscountAmount(
                    orderEntity.getDiscountAmount() - orderProductEntity.getDiscountAmount());
                orderEntity.setBillingAmount(
                    orderEntity.getBillingAmount() - orderProductEntity.getPaymentAmount());
                orderEntity.setReceivableAmount(
                    orderEntity.getReceivableAmount() - orderProductEntity.getPaymentAmount());
                orderEntity.setModifiedBy(modifiedBy);

                orderRepository.save(orderEntity);
                //                        orderProductRepository.delete(orderProductEntity);
              }
            },
            () -> {
              throw new LmsException(LmsErrorCode.ORDER_NOT_FOUND);
            });
  }

  @Transactional
  public void deleteOrder(String userId, String orderId, String modifiedBy) {
    orderRepository
        .findById(orderId)
        .filter(orderEntity -> orderEntity.getUserEntity().getId().equals(userId))
        .ifPresentOrElse(
            orderEntity -> {
              orderEntity
                  .getOrderProductEntities()
                  .forEach(
                      orderProductEntity ->
                          this.deleteOrderProduct(
                              userId, orderId, orderProductEntity.getId(), modifiedBy));
            },
            () -> {
              throw new LmsException(LmsErrorCode.ORDER_NOT_FOUND);
            });
  }

  @Transactional
  public Payment getPayment(String paymentId) {
    PaymentEntity paymentEntity =
        paymentRepository
            .findById(paymentId)
            .orElseThrow(() -> new LmsException(LmsErrorCode.PAYMENT_NOT_FOUND));
    String modifiedBy = paymentEntity.getModifiedBy();

    String modifiedName =
        userRepository.findById(modifiedBy).map(userEntity -> userEntity.getName()).orElse(null);

    Payment payment = userAdminServiceMapper.toPayment(paymentEntity, modifiedName);

    return payment;
  }

  @Transactional
  public void updateOrderPayment(UpdateOrderPayment updateOrderPayment) {
    paymentRepository
        .findById(updateOrderPayment.getPaymentId())
        .filter(
            paymentEntity ->
                paymentEntity.getOrderEntity().getId().equals(updateOrderPayment.getOrderId())
                && paymentEntity
                    .getOrderEntity()
                    .getUserEntity()
                    .getId()
                    .equals(updateOrderPayment.getUserId()))
        .ifPresentOrElse(
            paymentEntity -> {
              userAdminServiceMapper.mapPaymentEntity(updateOrderPayment, paymentEntity);
            },
            () -> {
              throw new LmsException(LmsErrorCode.PAYMENT_NOT_FOUND);
            });
  }
}
