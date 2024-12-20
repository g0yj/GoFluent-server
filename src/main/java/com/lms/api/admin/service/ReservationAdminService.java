package com.lms.api.admin.service;

import com.lms.api.admin.controller.dto.ListTeacherAttendancesDateRequest;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lms.api.admin.code.SearchReservationCode;
import com.lms.api.admin.controller.dto.ListTeacherAttendancesResponse;
import com.lms.api.admin.controller.dto.reservation.UpdateAttendanceStatusRequest;
import com.lms.api.admin.service.dto.Reservation;
import com.lms.api.admin.service.dto.ReservationAttendance;
import com.lms.api.admin.service.dto.reservation.SearchReport;
import com.lms.api.admin.service.dto.reservation.UpdateReport;
import com.lms.api.common.code.AttendanceStatus;
import com.lms.api.common.code.CourseHistoryModule;
import com.lms.api.common.code.CourseHistoryType;
import com.lms.api.common.code.TeacherType;
import com.lms.api.common.code.UserType;
import com.lms.api.common.entity.CourseEntity;
import com.lms.api.common.entity.CourseHistoryEntity;
import com.lms.api.common.entity.QReservationEntity;
import com.lms.api.common.entity.ReservationEntity;
import com.lms.api.common.exception.LmsErrorCode;
import com.lms.api.common.exception.LmsException;
import com.lms.api.common.repository.CourseHistoryRepository;
import com.lms.api.common.repository.ReservationRepository;
import com.lms.api.common.util.DateUtils;
import com.lms.api.common.util.StringUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReservationAdminService {

  private final JPAQueryFactory jpaQueryFactory;
  private final ReservationRepository reservationRepository;
  private final ReservationAdminServiceMapper reservationAdminServiceMapper;
  private final CourseHistoryRepository courseHistoryrepository;

  /**
   * 학사보고서 조회
   */
  public Page<Reservation> listReservationReport(SearchReport searchReport) {
    if (searchReport.getReportCondition() == SearchReservationCode.ReportCondition.REPORT) {
      return listNoReport(searchReport);
    } else {
      return listReport(searchReport);
    }
  }

  /**
   * 학사보고서 검색 조건
   */
  private BooleanExpression listReservationCondition(SearchReport searchReport,
      QReservationEntity qReservationEntity) {
    BooleanExpression where = Expressions.TRUE;

    // 강사인 경우 로그인 사용자 확인
    if (searchReport.getUserType() == UserType.T) {
      where = where.and(qReservationEntity.teacherEntity.userId.eq(searchReport.getLoginId()));
    }
    // 가입 시작일
    if (searchReport.getDateFrom() != null) {
      where = where.and(qReservationEntity.date.goe(searchReport.getDateFrom()));
    }
    // 가입 종료일
    if (searchReport.getDateTo() != null) {
      where = where.and(qReservationEntity.date.loe(searchReport.getDateTo()));
    }
    // 사용자 이름
    if (searchReport.getSearch() != null) {
      where = where.and(qReservationEntity.userEntity.name.contains(searchReport.getSearch()));
    }
    // 강사식별키
    if (StringUtils.hasText(searchReport.getTeacherId())) {
      where = where.and(qReservationEntity.teacherEntity.userId.eq(searchReport.getTeacherId()));
    }

    return where;
  }

  /**
   * 학사보고서 작성
   */
  public Page<Reservation> listReport(SearchReport searchReport) {
    // @formatter:off
    QReservationEntity qReservationEntity = QReservationEntity.reservationEntity;
    // 검색 조건
    BooleanExpression where = listReservationCondition(searchReport, qReservationEntity);
    // 보고서 검색조건: 출석
    if (searchReport.getReportCondition() == SearchReservationCode.ReportCondition.ATTENDANCE) {
      where = where.and(qReservationEntity.attendanceStatus.eq(AttendanceStatus.Y));
    }

    // 기본 정렬 기준
    Sort sort = determineSortOrder(searchReport);
    PageRequest pageRequest = PageRequest.of(searchReport.getPage() - 1, searchReport.getLimit(), sort);
    Page<ReservationEntity> reservationEntities = reservationRepository.findAll(where, pageRequest);

    return reservationEntities.map(reservationAdminServiceMapper::toReservation);
    // @formatter: on
  }

  /**
   * 학사보고서는 report, todayLesson, nextLesson 필드의 값이 모두 null인 경우 미작성이고 report, todayLesson, nextLesson
   * 필드의 값중 하나라도 작성이 되어있으면 작성으로 판단합니다. 하나의 학사 보고서는 courseId, userId, teacherId, date,
   * startTime,endTime 이 모두 같아야 합니다. 하나의 학사 보고서는 startTime과 endTime이 30분 간격이어야 합니다. courseId,
   * userId, teacherId, date가 같고 endTime과 startTime이 동일한 시간이면 연속된 예약으로 판단합니다. 시간이 연속된 예약은 하나의 수업으로
   * 판단하여 학사보고서의 작성여부를 판단합니다. 예를 들면 아래와 같습니다.
   * <p>동일 courseId, userId, teacherId, date로 된 예약이 06:00, 06:30, 07:00, 11:00, 11:30 5개의 경우
   * 1. 모든 예약시간에 학사보고서가 작성되지 않은 경우 미작성 예약은 5건 입니다.
   * 2. 06:00 시간에 학사보고서가 작성된 우 미작성 예약은 11:00, 11:30의2건 입니다.
   * 3. 07:00 시간에 사보고서가 작성된 경우 미작성 예약은 11:00 , 11:30의 2건 입니다.
   * 4. 11:00 시간에 학사보고서가 작성된 경우 미작성 예약은 06:00 , 06:30, 07:00의 3건 입니다.
   * 5. 11:30 시간에 학사보고서가 작성된 경우 미작성 예약은 06:00 , 06:30, 07:00의 3건 입니다.
   * 6. 06:30, 11:00 시간에 학사보고서가 작성된 경우 미작성 예약은 0건 입니다.
   * </p>
   */
  public Page<Reservation> listNoReport(SearchReport searchReport) {
    PageRequest pageRequest = PageRequest.of(searchReport.getPage() - 1, searchReport.getLimit());

    Page<ReservationEntity> reservationEntities = reservationRepository.listNoReportNative(
        searchReport.getDateFrom(),
        searchReport.getDateTo(),
        searchReport.getTeacherId(),
        searchReport.getSearch(),
        searchReport.getUserType() == UserType.T,
        searchReport.getLoginId(),
        pageRequest
    );

    return reservationEntities.map(reservationAdminServiceMapper::toReservation);
  }

  private Sort determineSortOrder(SearchReport searchReport) {
    // @formatter:off
    if (searchReport.getOrder() != null) {
      return switch (searchReport.getOrder()) {
        case "date"->Sort.by(Sort.Direction.valueOf(searchReport.getDirection().toUpperCase()),
            "date", "startTime");
        case "name"->Sort.by(Sort.Direction.valueOf(searchReport.getDirection().toUpperCase()),
            "userEntity.name");
        case "courseName"->Sort.by(Sort.Direction.valueOf(searchReport.getDirection().toUpperCase()),
            "courseEntity.orderProductEntity.productEntity.name");
        case "lessonCount"->Sort.by(Sort.Direction.valueOf(searchReport.getDirection().toUpperCase()),
                  "courseEntity.lessonCount");
        case "assignmentCount"->Sort.by(Sort.Direction.valueOf(searchReport.getDirection().toUpperCase()),
                  "courseEntity.assignmentCount");
        case "remainCount"->Sort.by(Sort.Direction.valueOf(searchReport.getDirection().toUpperCase()),
                  "courseEntity.remainCount");
        case "teacher"->Sort.by(Sort.Direction.valueOf(searchReport.getDirection().toUpperCase()),
                  "teacherEntity.userId");
        default -> Sort.by(Sort.Direction.DESC, "date", "startTime");};
    }

    return Sort.by(Sort.Direction.DESC, "date", "startTime");
    // @formatter:on
  }

  /**
   * 학사 보고서 수정
   */
  @Transactional
  public void updateReport(UpdateReport updateReport) {
    reservationRepository
        .findById(updateReport.getReservationId())
        .filter(
            reservationEntity -> reservationEntity.getId().equals(updateReport.getReservationId()))
        .ifPresentOrElse(
            reservationEntity ->
                reservationAdminServiceMapper.mapReservationEntity(updateReport, reservationEntity),
            () -> {
              throw new LmsException(LmsErrorCode.RESERVATION_NOT_FOUND);
            });
  }

  /**
   * 학사 보고서 상세 력
   */
  public Reservation getReport(long id) {
    return reservationRepository
        .findById(id)
        .filter(reservationEntity -> reservationEntity.getId().equals(id))
        .map(reservationAdminServiceMapper::toReport)
        .orElseThrow(() -> new LmsException(LmsErrorCode.RESERVATION_NOT_FOUND));
  }

  public List<ReservationAttendance> listAttendances(
      LocalDate startDate,
      LocalDate endDate,
      AttendanceStatus attendanceStatus,
      TeacherType teacherType) {
    QReservationEntity qReservationEntity = QReservationEntity.reservationEntity;

    return jpaQueryFactory
        .select(
            Projections.fields(
                ReservationAttendance.class,
                qReservationEntity.teacherEntity.userId.as("teacherId"),
                qReservationEntity.date,
                qReservationEntity.id.count().as("reservationCount"),
                qReservationEntity
                    .attendanceStatus
                    .when(attendanceStatus)
                    .then(1L)
                    .otherwise(0L)
                    .sum()
                    .as("attendanceCount")))
        .from(qReservationEntity)
        .where(
            qReservationEntity
                .date
                .between(startDate, endDate)
                .and(qReservationEntity.teacherEntity.type.eq(teacherType))
                .and(qReservationEntity.teacherEntity.active.isTrue()))
        .groupBy(qReservationEntity.teacherEntity.userId, qReservationEntity.date)
        .fetch();
  }

  /**
   * courseId 필요,
   */
  @Transactional
  public void updateAttendanceStatus(String id, UpdateAttendanceStatusRequest request) {
    Optional<ReservationEntity> reservationEntityOpt =
        reservationRepository.findById(request.getReservationId());
    ReservationEntity reservationEntity =
        reservationEntityOpt.orElseThrow(
            () -> new LmsException(LmsErrorCode.RESERVATION_NOT_FOUND));
    CourseEntity courseEntity = reservationEntity.getCourseEntity();
    CourseHistoryEntity courseHistoryEntity = new CourseHistoryEntity();

    if (reservationEntity.getAttendanceStatus() != request.getAttendanceStatus()) {
      switch (request.getAttendanceStatus()) {
        case Y:
          reservationEntity.setAttendanceStatus(AttendanceStatus.Y);
          courseHistoryEntity.setCourseEntity(courseEntity);
          courseHistoryEntity.setModule(CourseHistoryModule.COURSE_USER);
          courseHistoryEntity.setType(CourseHistoryType.ATTENDANCE.getCode());
          courseHistoryEntity.setCreatedBy(id);
          courseHistoryEntity.setContent(
              reservationEntity.getDate() + " " + reservationEntity.getStartTime() + " 수업을 출석 처리");
          courseHistoryrepository.save(courseHistoryEntity);
          break;
        case N:
          reservationEntity.setAttendanceStatus(AttendanceStatus.N);
          courseHistoryEntity.setCourseEntity(courseEntity);
          courseHistoryEntity.setModule(CourseHistoryModule.COURSE_USER);
          courseHistoryEntity.setType(CourseHistoryType.ATTENDANCE.getCode());
          courseHistoryEntity.setCreatedBy(id);
          courseHistoryEntity.setContent(
              reservationEntity.getDate() + " " + reservationEntity.getStartTime() + " 수업을 결석 처리");
          courseHistoryrepository.save(courseHistoryEntity);
          break;
        case R:
          reservationEntity.setAttendanceStatus(AttendanceStatus.R);
          courseHistoryEntity.setCourseEntity(courseEntity);
          courseHistoryEntity.setModule(CourseHistoryModule.COURSE_USER);
          courseHistoryEntity.setType(CourseHistoryType.ATTENDANCE.getCode());
          courseHistoryEntity.setCreatedBy(id);
          courseHistoryEntity.setContent(
              reservationEntity.getDate()
              + " "
              + reservationEntity.getStartTime()
              + " 수업을 출결 취소 처리");
          courseHistoryrepository.save(courseHistoryEntity);
          break;
        default:
          break;
      }
    }
  }

//  public ListTeacherAttendancesResponse getTeacherAttendances(String startDate, String status) {
//    // 주어진 시작 날짜로부터 해당 월의 마지막 날짜를 계산
//    String endDate = DateUtils.getLastDayOfMonthFromDateString(startDate);
//
//    // 데이터베이스에서 주어진 날짜 범위와 상태에 해당하는 출석 데이터를 조회
//    List<Object[]> results = reservationRepository.findTeacherAttendances(startDate, endDate, status);
//
//    // 날짜별 출석 데이터를 저장할 맵
//    Map<LocalDate, List<ListTeacherAttendancesResponse.Attendance>> scheduleMap = new LinkedHashMap<>();
//    // 총 출석 데이터를 저장할 맵
//    Map<String, ListTeacherAttendancesResponse.Attendance> totalAttendanceMap = new LinkedHashMap<>();
//
//    // 전체 기간 HT와 LT 데이터를 합산하기 위한 변수들
//    long totalHTReservationCountAll = 0;
//    long totalHTAttendanceCountAll = 0;
//    long totalLTReservationCountAll = 0;
//    long totalLTAttendanceCountAll = 0;
//    int totalHTAvgIndex = 0;
//
//    // 조회된 결과를 순회하며 출석 데이터를 처리
//    for (Object[] row : results) {
//      LocalDate date = ((java.sql.Date) row[0]).toLocalDate();
//      String id = String.valueOf(row[1]);
//      String type = (String) row[2];
//      String name = (String) row[3];
//      Integer sort = (Integer) row[4];
//      Long reservationCount = ((Number) row[5]).longValue();
//      Long attendanceCount = ((Number) row[6]).longValue();
//      BigDecimal attendanceRate = (BigDecimal) row[7]; // SQL에서 제공된 출석률 사용
//
//      // Attendance 객체 생성
//      ListTeacherAttendancesResponse.Attendance attendance = ListTeacherAttendancesResponse.Attendance.builder()
//          .id(id)
//          .name(name)
//          .type(type)
//          .sort(sort)
//          .reservationCount(reservationCount)
//          .attendanceCount(attendanceCount)
//          .attendanceRate(attendanceRate != null ? attendanceRate : BigDecimal.ZERO) // null 체크
//          .build();
//
//      // 날짜별로 출석 데이터를 추가
//      scheduleMap.computeIfAbsent(date, k -> new ArrayList<>()).add(attendance);
//
//      // 총 출석 데이터를 업데이트
//      totalAttendanceMap.merge(name, attendance, (existing, newAttendance) -> {
//        long totalReservationCount = existing.getReservationCount() + newAttendance.getReservationCount();
//        long totalAttendanceCount = existing.getAttendanceCount() + newAttendance.getAttendanceCount();
//        BigDecimal totalAttendanceRate = totalReservationCount == 0 ? BigDecimal.ZERO :
//            BigDecimal.valueOf((double) totalAttendanceCount / totalReservationCount * 100).setScale(2, RoundingMode.HALF_UP);
//        return ListTeacherAttendancesResponse.Attendance.builder()
//            .id(existing.getId())
//            .name(existing.getName())
//            .type(existing.getType())
//            .sort(existing.getSort())
//            .reservationCount(totalReservationCount)
//            .attendanceCount(totalAttendanceCount)
//            .attendanceRate(totalAttendanceRate)
//            .build();
//      });
//
//      // 전체 기간 HT와 LT 데이터를 합산
//      if ("HT".equals(type)) {
//        totalHTReservationCountAll += reservationCount;
//        totalHTAttendanceCountAll += attendanceCount;
//      } else if ("LT".equals(type)) {
//        totalLTReservationCountAll += reservationCount;
//        totalLTAttendanceCountAll += attendanceCount;
//      }
//    }
//
//    // 각 날짜별로 HT Avg.와 LT Avg.를 계산
//    for (Map.Entry<LocalDate, List<ListTeacherAttendancesResponse.Attendance>> entry : scheduleMap.entrySet()) {
//      List<ListTeacherAttendancesResponse.Attendance> attendances = entry.getValue();
//      long totalHTReservationCount = 0;
//      long totalHTAttendanceCount = 0;
//      long totalLTReservationCount = 0;
//      long totalLTAttendanceCount = 0;
//
//      // HT와 LT를 구분하여 출석률을 합산
//      int htAvgIndex = 0;
//      for (ListTeacherAttendancesResponse.Attendance attendance : attendances) {
//        if ("HT".equals(attendance.getType())) {
//          totalHTReservationCount += attendance.getReservationCount();
//          totalHTAttendanceCount += attendance.getAttendanceCount();
//          htAvgIndex++;
//        } else if ("LT".equals(attendance.getType())) {
//          totalLTReservationCount += attendance.getReservationCount();
//          totalLTAttendanceCount += attendance.getAttendanceCount();
//        }
//      }
//
//      // HT Avg. 출석률 계산
//      BigDecimal htAvgRate = (totalHTReservationCount == 0) ? BigDecimal.ZERO :
//          BigDecimal.valueOf((double) totalHTAttendanceCount / totalHTReservationCount * 100)
//              .setScale(2, RoundingMode.HALF_UP);
//
//      // LT Avg. 출석률 계산
//      BigDecimal ltAvgRate = (totalLTReservationCount == 0) ? BigDecimal.ZERO :
//          BigDecimal.valueOf((double) totalLTAttendanceCount / totalLTReservationCount * 100)
//              .setScale(2, RoundingMode.HALF_UP);
//
//      // HT Avg.를 출석 데이터에 추가
//      attendances.add(htAvgIndex, ListTeacherAttendancesResponse.Attendance.builder()
//          .name("HT Avg.")
//          .reservationCount(totalHTReservationCount)
//          .attendanceCount(totalHTAttendanceCount)
//          .attendanceRate(htAvgRate)
//          .build());
//      totalHTAvgIndex = htAvgIndex;
//
//      // LT Avg.를 출석 데이터에 추가
//      attendances.add(ListTeacherAttendancesResponse.Attendance.builder()
//          .name("LT Avg.")
//          .reservationCount(totalLTReservationCount)
//          .attendanceCount(totalLTAttendanceCount)
//          .attendanceRate(ltAvgRate)
//          .build());
//    }
//
//    // total HT 및 total LT 평균 계산
//    BigDecimal totalHTAvgRate = (totalHTReservationCountAll == 0) ? BigDecimal.ZERO :
//        BigDecimal.valueOf((double) totalHTAttendanceCountAll / totalHTReservationCountAll * 100)
//            .setScale(2, RoundingMode.HALF_UP);
//
//    BigDecimal totalLTAvgRate = (totalLTReservationCountAll == 0) ? BigDecimal.ZERO :
//        BigDecimal.valueOf((double) totalLTAttendanceCountAll / totalLTReservationCountAll * 100)
//            .setScale(2, RoundingMode.HALF_UP);
//
//    // 스케줄 리스트 생성
//    List<ListTeacherAttendancesResponse.Schedule> schedules = scheduleMap.entrySet().stream()
//        .map(entry -> ListTeacherAttendancesResponse.Schedule.builder()
//            .date(entry.getKey())
//            .attendances(entry.getValue())
//            .build())
//        .collect(Collectors.toList());
//
//    // 총 출석 리스트 생성
//    List<ListTeacherAttendancesResponse.Attendance> totalAttendances = new ArrayList<>(totalAttendanceMap.values());
//
//    // Total HT Avg.와 Total LT Avg.를 출석 데이터에 추가
//    totalAttendances.add(totalHTAvgIndex, ListTeacherAttendancesResponse.Attendance.builder()
//        .name("Total HT Avg.")
//        .reservationCount(totalHTReservationCountAll)
//        .attendanceCount(totalHTAttendanceCountAll)
//        .attendanceRate(totalHTAvgRate)
//        .build());
//
//    totalAttendances.add(ListTeacherAttendancesResponse.Attendance.builder()
//        .name("Total LT Avg.")
//        .reservationCount(totalLTReservationCountAll)
//        .attendanceCount(totalLTAttendanceCountAll)
//        .attendanceRate(totalLTAvgRate)
//        .build());
//
//    // 최종 응답 객체 생성 및 반환
//    return ListTeacherAttendancesResponse.builder()
//        .schedules(schedules)
//        .totalAttendances(totalAttendances)
//        .build();
//  }

  public ListTeacherAttendancesResponse getTeacherAttendances(String startDate, String status) {
    // 주어진 시작 날짜로부터 해당 월의 마지막 날짜를 계산
    String endDate = DateUtils.getLastDayOfMonthFromDateString(startDate);
    return getTeacherAttendances(startDate, endDate, status);
  }

  public ListTeacherAttendancesResponse getTeacherAttendances(String startDate, String endDate, String status) {
    // 데이터베이스에서 주어진 날짜 범위와 상태에 해당하는 출석 데이터를 조회
    List<Object[]> results = reservationRepository.findTeacherAttendances(startDate, endDate, status);

    // 날짜별 출석 데이터를 저장할 맵
    Map<LocalDate, List<ListTeacherAttendancesResponse.Attendance>> scheduleMap = new LinkedHashMap<>();
    // 총 출석 데이터를 저장할 맵
    Map<String, ListTeacherAttendancesResponse.Attendance> totalAttendanceMap = new LinkedHashMap<>();

    // 전체 기간 HT와 LT 데이터를 합산하기 위한 변수들
    long totalHTReservationCountAll = 0;
    long totalHTAttendanceCountAll = 0;
    long totalLTReservationCountAll = 0;
    long totalLTAttendanceCountAll = 0;
    int totalHtLastIndex = 0;

    // 조회된 결과를 순회하며 출석 데이터를 처리
    for (Object[] row : results) {
      LocalDate date = ((java.sql.Date) row[0]).toLocalDate();
      String id = String.valueOf(row[1]);
      String type = (String) row[2];
      String name = (String) row[3];
      Integer sort = (Integer) row[4];
      Long reservationCount = ((Number) row[5]).longValue();
      Long attendanceCount = ((Number) row[6]).longValue();
      BigDecimal attendanceRate = (BigDecimal) row[7]; // SQL에서 제공된 출석률 사용

      // Attendance 객체 생성
      ListTeacherAttendancesResponse.Attendance attendance = ListTeacherAttendancesResponse.Attendance.builder()
          .id(id)
          .name(name)
          .type(type)
          .sort(sort)
          .reservationCount(reservationCount)
          .attendanceCount(attendanceCount)
          .attendanceRate(attendanceRate != null ? attendanceRate : BigDecimal.ZERO) // null 체크
          .build();

      // 날짜별로 출석 데이터를 추가
      scheduleMap.computeIfAbsent(date, k -> new ArrayList<>()).add(attendance);

      // 총 출석 데이터를 업데이트
      totalAttendanceMap.merge(name, attendance, (existing, newAttendance) -> {
        long totalReservationCount = existing.getReservationCount() + newAttendance.getReservationCount();
        long totalAttendanceCount = existing.getAttendanceCount() + newAttendance.getAttendanceCount();
        BigDecimal totalAttendanceRate = totalReservationCount == 0 ? BigDecimal.ZERO :
            BigDecimal.valueOf((double) totalAttendanceCount / totalReservationCount * 100).setScale(2, RoundingMode.HALF_UP);
        return ListTeacherAttendancesResponse.Attendance.builder()
            .id(existing.getId())
            .name(existing.getName())
            .type(existing.getType())
            .sort(existing.getSort())
            .reservationCount(totalReservationCount)
            .attendanceCount(totalAttendanceCount)
            .attendanceRate(totalAttendanceRate)
            .build();
      });

      // 전체 기간 HT와 LT 데이터를 합산
      if ("HT".equals(type)) {
        totalHTReservationCountAll += reservationCount;
        totalHTAttendanceCountAll += attendanceCount;
      } else if ("LT".equals(type)) {
        totalLTReservationCountAll += reservationCount;
        totalLTAttendanceCountAll += attendanceCount;
      }
    }

    // 각 날짜별로 HT Avg.와 LT Avg.를 계산
    for (Map.Entry<LocalDate, List<ListTeacherAttendancesResponse.Attendance>> entry : scheduleMap.entrySet()) {
      List<ListTeacherAttendancesResponse.Attendance> attendances = entry.getValue();
      long totalHTReservationCount = 0;
      long totalHTAttendanceCount = 0;
      long totalLTReservationCount = 0;
      long totalLTAttendanceCount = 0;

      int htLastIndex = -1;

      // HT와 LT를 구분하여 출석률을 합산하고 HT의 마지막 위치를 기록
      for (int i = 0; i < attendances.size(); i++) {
        ListTeacherAttendancesResponse.Attendance attendance = attendances.get(i);
        if ("HT".equals(attendance.getType())) {
          totalHTReservationCount += attendance.getReservationCount();
          totalHTAttendanceCount += attendance.getAttendanceCount();
          htLastIndex = i;  // HT 타입의 마지막 인덱스
        } else if ("LT".equals(attendance.getType())) {
          totalLTReservationCount += attendance.getReservationCount();
          totalLTAttendanceCount += attendance.getAttendanceCount();
        }
      }

      // HT Avg. 출석률 계산
      BigDecimal htAvgRate = (totalHTReservationCount == 0) ? BigDecimal.ZERO :
          BigDecimal.valueOf((double) totalHTAttendanceCount / totalHTReservationCount * 100)
              .setScale(2, RoundingMode.HALF_UP);

      // LT Avg. 출석률 계산
      BigDecimal ltAvgRate = (totalLTReservationCount == 0) ? BigDecimal.ZERO :
          BigDecimal.valueOf((double) totalLTAttendanceCount / totalLTReservationCount * 100)
              .setScale(2, RoundingMode.HALF_UP);

      // HT Avg.는 HT 강사의 마지막 위치에 추가
      if (htLastIndex >= 0) {
        attendances.add(htLastIndex + 1, ListTeacherAttendancesResponse.Attendance.builder()
            .name("HT Avg.")
            .reservationCount(totalHTReservationCount)
            .attendanceCount(totalHTAttendanceCount)
            .attendanceRate(htAvgRate)
            .build());
      }
      totalHtLastIndex = htLastIndex;

      // LT Avg.는 항상 마지막에 추가
      attendances.add(ListTeacherAttendancesResponse.Attendance.builder()
          .name("LT Avg.")
          .reservationCount(totalLTReservationCount)
          .attendanceCount(totalLTAttendanceCount)
          .attendanceRate(ltAvgRate)
          .build());
    }

    // total HT 및 total LT 평균 계산
    BigDecimal totalHTAvgRate = (totalHTReservationCountAll == 0) ? BigDecimal.ZERO :
        BigDecimal.valueOf((double) totalHTAttendanceCountAll / totalHTReservationCountAll * 100)
            .setScale(2, RoundingMode.HALF_UP);

    BigDecimal totalLTAvgRate = (totalLTReservationCountAll == 0) ? BigDecimal.ZERO :
        BigDecimal.valueOf((double) totalLTAttendanceCountAll / totalLTReservationCountAll * 100)
            .setScale(2, RoundingMode.HALF_UP);

    // 스케줄 리스트 생성
    List<ListTeacherAttendancesResponse.Schedule> schedules = scheduleMap.entrySet().stream()
        .map(entry -> ListTeacherAttendancesResponse.Schedule.builder()
            .date(entry.getKey())
            .attendances(entry.getValue())
            .build())
        .collect(Collectors.toList());

    // 총 출석 리스트 생성
    List<ListTeacherAttendancesResponse.Attendance> totalAttendances = new ArrayList<>(totalAttendanceMap.values());

    // Total HT Avg.는 HT 강사의 마지막에 추가
    totalAttendances.add(totalHtLastIndex + 1, ListTeacherAttendancesResponse.Attendance.builder()
        .name("Total HT Avg.")
        .reservationCount(totalHTReservationCountAll)
        .attendanceCount(totalHTAttendanceCountAll)
        .attendanceRate(totalHTAvgRate)
        .build());

    // Total LT Avg.는 항상 마지막에 추가
    totalAttendances.add(ListTeacherAttendancesResponse.Attendance.builder()
        .name("Total LT Avg.")
        .reservationCount(totalLTReservationCountAll)
        .attendanceCount(totalLTAttendanceCountAll)
        .attendanceRate(totalLTAvgRate)
        .build());

    // 최종 응답 객체 생성 및 반환
    return ListTeacherAttendancesResponse.builder()
        .schedules(schedules)
        .totalAttendances(totalAttendances)
        .build();
  }


}