package com.lms.api.admin.service;


import com.lms.api.admin.code.SearchSmsCode.SearchType;
import com.lms.api.admin.controller.dto.reservation.ReservationUserRequest;
import com.lms.api.admin.controller.dto.statistics.DeleteCancelSmsRequest;
import com.lms.api.admin.controller.dto.statistics.DeleteSmsRequest;
import com.lms.api.admin.controller.dto.statistics.ListLdfRequest;
import com.lms.api.admin.service.dto.User;
import com.lms.api.admin.service.dto.reservation.ReservationUser;
import com.lms.api.admin.service.dto.statistics.Evaluation;
import com.lms.api.admin.service.dto.statistics.GetLdf;
import com.lms.api.admin.service.dto.statistics.GetSms;
import com.lms.api.admin.service.dto.statistics.ListLdf;
import com.lms.api.admin.service.dto.statistics.SearchEvaluation;
import com.lms.api.admin.service.dto.statistics.SearchListSms;
import com.lms.api.admin.service.dto.statistics.SearchListUser;
import com.lms.api.admin.service.dto.statistics.SearchSmsTarget;
import com.lms.api.admin.service.dto.statistics.Sms;
import com.lms.api.admin.service.dto.statistics.SmsTarget;
import com.lms.api.admin.service.dto.statistics.SuccessSms;
import com.lms.api.admin.service.dto.statistics.WaitingSms;
import com.lms.api.common.code.SmsStatus;
import com.lms.api.common.code.UserType;
import com.lms.api.common.entity.LdfEntity;
import com.lms.api.common.entity.QSmsEntity;
import com.lms.api.common.entity.QSmsTargetEntity;
import com.lms.api.common.entity.QUserEntity;
import com.lms.api.common.entity.SmsEntity;
import com.lms.api.common.entity.SmsTargetEntity;
import com.lms.api.common.entity.UserEntity;
import com.lms.api.common.exception.LmsErrorCode;
import com.lms.api.common.exception.LmsException;
import com.lms.api.common.repository.LdfRepository;
import com.lms.api.common.repository.ReservationRepository;
import com.lms.api.common.repository.SmsRepository;
import com.lms.api.common.repository.SmsTargetRepository;
import com.lms.api.common.repository.UserRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StatisticsAdminService {

  private final SmsRepository smsRepository;
  private final StatisticsAdminServiceMapper statisticsAdminServiceMapper;
  private final UserRepository userRepository;
  private final UserAdminServiceMapper userAdminServiceMapper;
  private final SmsTargetRepository smsTargetRepository;
  private final LdfRepository ldfRepository;
  private final ReservationRepository reservationRepository;

  private final JPAQueryFactory queryFactory;

  public Page<SuccessSms> toListSms(SearchListSms searchSms) {
    QSmsEntity qsmsEntity = QSmsEntity.smsEntity;

    BooleanExpression where = Expressions.TRUE;

    if (searchSms.getSendDateFrom() != null && searchSms.getSendDateTo() != null) {
      where = where.and(qsmsEntity.sendDate.between(searchSms.getSendDateFrom().atStartOfDay(),
          searchSms.getSendDateTo().atTime(23, 59, 59)));
    }

    if (searchSms.getSendType() != null && searchSms.getSendType() != SearchType.ALL) {
      where = where.and(qsmsEntity.sendType.eq(SearchType.valueOf(searchSms.getSendType().name())));
    }

    if (searchSms.hasSearch()) {
      switch (searchSms.getSearch()) {
        case "ALL" :
          where = where.and(
              qsmsEntity.content.contains(searchSms.getKeyword())
                  .or(qsmsEntity.senderName.contains(searchSms.getKeyword()))
              );
              break;
        case "content":
          where = where.and(qsmsEntity.content.contains(searchSms.getKeyword()));
          break;
        case "senderName":
          where = where.and(qsmsEntity.senderName.contains(searchSms.getKeyword()));
          break;
        default:
          break;
      }
    }
    Page<SmsEntity> smsEntities = smsRepository.findAll(where, searchSms.toPageRequest());

    return smsEntities.map(smsEntity -> {
      Sms sms = statisticsAdminServiceMapper.toSms(smsEntity);

      // 발송 건수, 성공, 실패 정보 설정
      long total = smsEntity.getSmsTargetEntities().size();
      long success = smsEntity.getSmsTargetEntities().stream()
          .filter(target -> target.getStatus() == SmsStatus.SUCCESS)
          .count();
      long fail = smsEntity.getSmsTargetEntities().stream()
          .filter(target -> target.getStatus() == SmsStatus.FAIL)
          .count();
      long waiting = smsEntity.getSmsTargetEntities().stream()
          .filter(target -> target.getStatus() == SmsStatus.WAITING)
          .count();

      SuccessSms successSms = SuccessSms.builder()
          .sms(sms)
          .total(total)
          .success(success)
          .fail(fail)
          .waiting(waiting)
          .build();

      return successSms;
    });
  }
  /** 통계보고서 > SMS 현황 > 대기내역 */
  public Page<WaitingSms> listWaitingSms(SearchListSms searchSms) {
    QSmsTargetEntity qSmsTargetEntity = QSmsTargetEntity.smsTargetEntity;

    BooleanExpression where = qSmsTargetEntity.status.eq(SmsStatus.WAITING);

    if (searchSms.getSendType() != null && searchSms.getSendType() != SearchType.ALL) {
      where = where.and(qSmsTargetEntity.smsEntity.sendType.eq(
          SearchType.valueOf(searchSms.getSendType().name())));
    }

    if (searchSms.hasSearch()) {
      switch (searchSms.getSearch()) {
        // 수신번호, 발신번호, 내용
        case "ALL":
          where = where.and(
              qSmsTargetEntity.recipientPhone.contains(searchSms.getKeyword())
                  .or(qSmsTargetEntity.smsEntity.senderPhone.contains(searchSms.getKeyword()))
                  .or(qSmsTargetEntity.smsEntity.content.contains(searchSms.getKeyword()))
          );
          break;
        case "recipientPhone":
          where = where.and(qSmsTargetEntity.recipientPhone.contains(searchSms.getKeyword()));
          break;
        case "senderPhone":
          where = where.and(qSmsTargetEntity.smsEntity.senderPhone.contains(searchSms.getKeyword()));
          break;
        case "content":
          where = where.and(qSmsTargetEntity.smsEntity.content.contains(searchSms.getKeyword()));
          break;
        default:
          break;
      }
    }

    Page<SmsTargetEntity> smsTargetEntities = smsTargetRepository.findAll(where, searchSms.toPageRequest());

    return smsTargetEntities.map(smsTargetEntity -> {
      SmsTarget smsTarget = statisticsAdminServiceMapper.toSmsTarget(smsTargetEntity);
      SearchType sendType = smsTargetEntity.getSmsEntity().getSendType();
      String senderPhone = smsTargetEntity.getSmsEntity().getSenderPhone();
      String senderName = smsTargetEntity.getSmsEntity().getSenderName();
      String content = smsTargetEntity.getSmsEntity().getContent();

      WaitingSms waitingSms = WaitingSms.builder()
          .smsTarget(smsTarget)
          .sendType(sendType)
          .senderPhone(senderPhone)
          .senderName(senderName)
          .content(content)
          .count(1)
          .build();


      return waitingSms;
    });
  }

  /**
   * 발송 내역 상세 조회
   */
  public GetSms getSms(long id) {
    SmsEntity smsEntity = smsRepository.findById(id)
        .orElseThrow(() -> new LmsException(LmsErrorCode.SMS_NOT_FOUND));

    GetSms sms = statisticsAdminServiceMapper.toGetSms(smsEntity);

    return sms;
  }

  /**
   * 발송 내역 삭제
   */
  @Transactional
  public void deleteSms(DeleteSmsRequest id) {
    List<Long> smsIds = id.getSmsId();
    if(smsIds != null && !smsIds.isEmpty()) {
      smsRepository.deleteAllById(smsIds);
    }
  }

  /** 대상자 보기*/
  public Page<SmsTarget> toListSmsTarget(SearchSmsTarget searchListSmsTarget) {
    QSmsTargetEntity qSmsTargetEntity = QSmsTargetEntity.smsTargetEntity;

    BooleanExpression where = Expressions.TRUE;

    // smsId 조건 추가
    if (searchListSmsTarget.getSmsId() != null) {
      where = where.and(qSmsTargetEntity.smsEntity.id.eq(searchListSmsTarget.getSmsId()));
    }

    if (searchListSmsTarget.hasSearch()) {
      switch (searchListSmsTarget.getSearch()) {
        case "ALL":
          where = where.and(
              qSmsTargetEntity.email.contains(searchListSmsTarget.getKeyword())
                  .or(qSmsTargetEntity.recipientName.contains(searchListSmsTarget.getKeyword()))
          );
          break;
        case "recipientName":
          where = where.and(
              qSmsTargetEntity.recipientName.contains(searchListSmsTarget.getKeyword()));
          break;
        case "email":
          where = where.and(qSmsTargetEntity.email.contains(searchListSmsTarget.getKeyword()));
          break;
        default:
          break;
      }
    }

    Page<SmsTargetEntity> smsTargetEntities = smsTargetRepository.findAll(where, searchListSmsTarget.toPageRequest());

    return smsTargetEntities.map(smsTargetEntity -> {
      // recipientPhone으로 UserEntity의 cellPhone과 매핑하여 email 가져오기
//      String recipientPhone = smsTargetEntity.getRecipientPhone();
//      String email = userRepository.findByCellPhone(recipientPhone)
//              .map(UserEntity::getEmail)
//              .orElse(null);

      // SmsTargetEntity를 SmsTarget으로 매핑
      SmsTarget smsTarget = statisticsAdminServiceMapper.toSmsTarget(smsTargetEntity);
//      smsTarget.setEmail(email);
      return smsTarget;
    });
  }

  /**
   * SMS 발송 대상자 목록
   */
  public List<User> listUser(SearchListUser searchListUser) {
    QUserEntity qUserEntity = QUserEntity.userEntity;
    BooleanExpression where = Expressions.TRUE;
    if (searchListUser.getType() != null) {
      if (searchListUser.getType() == UserType.A) {
        where = where.and(qUserEntity.type.eq(searchListUser.getType()));
      } else if (searchListUser.getType() == UserType.S) {
        where = where.and(qUserEntity.type.eq(searchListUser.getType()));
      } else if (searchListUser.getType() == UserType.T) {
        where = where.and(qUserEntity.type.eq(searchListUser.getType()));
      }
    }
    if (searchListUser.hasSearch()) {
      switch (searchListUser.getSearch()) {
        case "name":
          where = where.and(qUserEntity.name.contains(searchListUser.getKeyword()));
          break;
        case "loginId":
          where = where.and(qUserEntity.loginId.contains(searchListUser.getKeyword()));
          break;
      }
    }
    Sort sort = Sort.by(Sort.Direction.DESC, "name");
    List<UserEntity> userEntities = (List<UserEntity>) userRepository.findAll(where, sort);

    return userEntities.stream()
        .map(userEntity -> statisticsAdminServiceMapper.toUser(userEntity))
        .collect(Collectors.toList());
  }

  @Transactional
  public void deleteCancelSms(DeleteCancelSmsRequest smsList) {
    List<Long> ids = smsList.getId();
    if(ids != null && !ids.isEmpty()){
      List<SmsTargetEntity> waitingSms = smsTargetRepository.findByIdInAndStatus(ids, SmsStatus.WAITING);
      smsTargetRepository.deleteAll(waitingSms);
    }
  }

  /**
   * 평가 현황
   */

  private static final AtomicLong counter = new AtomicLong(1);

  public List<Evaluation> listEvaluation(SearchEvaluation searchEvaluation) {
    LocalDate date = searchEvaluation.getDate();
    LocalDate startDate = LocalDate.of(date.getYear(), date.getMonth(), 1);
    LocalDate endDate = startDate.plusMonths(1).minusDays(1);

    return ldfRepository.findEvaluationsByDateRange(startDate, endDate);
  }

  public List<ListLdf> listLdfs(ListLdfRequest request) {
    String teacherId = request.getTeacherId();
    YearMonth date = request.getDate();
    LocalDate startDate = LocalDate.of(date.getYear(), date.getMonth(), 1);
    LocalDate endDate = startDate.plusMonths(1).minusDays(1);

    List<LdfEntity> ldfEntities = ldfRepository.findLdfsByTeacherIdAndDateRange(teacherId, startDate, endDate);

    return ldfEntities.stream().map(ldfEntity -> ListLdf.builder()
            .date(ldfEntity.getReservationEntity().getDate())
            .startTime(ldfEntity.getReservationEntity().getStartTime())
            .endTime(ldfEntity.getReservationEntity().getEndTime())
            .userId(ldfEntity.getUserEntity().getId())
            .userName(ldfEntity.getUserEntity().getName())
            .grade(ldfEntity.getGrade())
            .evaluation(ldfEntity.getEvaluation())
            .ldf(statisticsAdminServiceMapper.toListLdf(ldfEntity))
            .build()
    ).collect(Collectors.toList());
  }
/*
  private static final AtomicLong counter = new AtomicLong(1);

  public List<Evaluation> toListEvaluation(SearchEvaluation searchEvaluation) {
    QReservationEntity qReservationEntity = QReservationEntity.reservationEntity;
    QLdfEntity qLdfEntity = QLdfEntity.ldfEntity;
    QTeacherEntity qTeacherEntity = QTeacherEntity.teacherEntity;
    QUserEntity qUserEntity = QUserEntity.userEntity;

    BooleanExpression where = Expressions.TRUE;

    // 검색 조건에 따라 수업일 범위 설정
    if (searchEvaluation.getDate() != null) {
      LocalDate date = searchEvaluation.getDate();
      LocalDate startDate = LocalDate.of(date.getYear(), date.getMonth(), 1);
      LocalDate endDate = startDate.plusMonths(1).minusDays(1);
      where = where.and(qLdfEntity.reservationEntity.date.between(startDate, endDate));
    }

    if (searchEvaluation.hasSearch()) {
      switch (searchEvaluation.getSearch()) {
        case "name":
          where = where.and(
                  qTeacherEntity.userEntity.name.contains(searchEvaluation.getKeyword())
          );
        default:
          break;
      }
    }

    // 해당 기간의 LdfEntity를 조회
    List<LdfEntity> ldfEntities = queryFactory.selectFrom(qLdfEntity)
        .where(where)
        .fetch();

    // LdfEntity를 ReservationEntity의 TeacherEntity의 userId로 그룹핑
    return ldfEntities.stream()
        .filter(ldfEntity -> ldfEntity.getReservationEntity() != null
            && ldfEntity.getReservationEntity().getTeacherEntity() != null)
        .collect(Collectors.groupingBy(
            ldfEntity -> ldfEntity.getReservationEntity().getTeacherEntity().getUserId(),
            Collectors.collectingAndThen(
                Collectors.toList(),
                entities -> {

                  // Ldf 로 변환.
                  List<Ldf> ldfList = statisticsAdminServiceMapper.toEvaluationLdf(entities);

                  // 유효한 LdfEntity만 필터링
                  List<LdfEntity> validLdfEntities = entities.stream()
                      .filter(ldf -> ldf.getGrade() != null && ldf.getGrade() >= 0.0)
                      .collect(Collectors.toList());

                  // 평균 점수 계산
                  double gradeAvg = validLdfEntities.stream()
                      .mapToDouble(LdfEntity::getGrade)
                      .average()
                      .orElse(0.0);

                  // 평가 횟수 계산 (validLdfEntities의 갯수)
                  long gradeCount = validLdfEntities.size();

                  double total = validLdfEntities.stream()
                      .mapToDouble(LdfEntity::getGrade)
                      .sum();

                  // 강사 이름과 ID를 가져오기
                  String teacherName = entities.get(0).getReservationEntity()
                      .getTeacherEntity().getUserEntity().getName();

                  String teacherId = entities.get(0).getReservationEntity()
                      .getTeacherEntity().getUserId();

                  long detailId = counter.getAndIncrement();
                  // 평가 정보 반환
                  return new Evaluation(detailId, teacherName, teacherId, gradeCount, gradeAvg,
                      total, ldfList);
                }
            )
        ))
        .values().stream()
        .filter(Objects::nonNull)
        .collect(Collectors.toList());

  }
*/


  /**
   * Ldf 상세 조회
   */
  public GetLdf getLdf(long id) {
    LdfEntity qLdfEntity = ldfRepository.findById(id)
        .orElseThrow(() -> new LmsException(LmsErrorCode.LDF_NOT_FOUND));
    return statisticsAdminServiceMapper.toGetLdf(qLdfEntity);
  }


  @Transactional
  public List<ReservationUser> listEvaluationsUser(ReservationUserRequest request) {
    List<Object[]> reservationEntities = reservationRepository.findDistinctUserIdsByDateBetween(request.getDateFrom(), request.getDateTo());
    return reservationEntities.stream()
            .map(reservation -> ReservationUser.builder()
                    .userId((String) reservation[0])
                    .name((String) reservation[1])
                    .build())
            .collect(Collectors.toList());
  }
}

