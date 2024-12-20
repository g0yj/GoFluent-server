package com.lms.api.admin.service;

import com.lms.api.admin.service.dto.SearchTeacherUsers;
import com.lms.api.admin.service.dto.Teacher;
import com.lms.api.admin.service.dto.TeacherPageUserList;
import com.lms.api.common.entity.QCourseEntity;
import com.lms.api.common.entity.QReservationEntity;
import com.lms.api.common.entity.QUserEntity;
import com.lms.api.common.entity.ReservationEntity;
import com.lms.api.common.entity.TeacherEntity;
import com.lms.api.common.entity.UserEntity;
import com.lms.api.common.repository.ReservationRepository;
import com.lms.api.common.repository.TeacherRepository;
import com.lms.api.common.repository.UserRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeacherService {
  private final UserRepository userRepository;
  private final ReservationRepository reservationRepository;
  private final TeacherServiceMapper teacherServiceMapper;
  private final UserAdminServiceMapper userAdminServiceMapper;
  private final TeacherRepository teacherRepository;

  public Page<TeacherPageUserList> listUsers(SearchTeacherUsers searchUsers) {
    QUserEntity qUserEntity = QUserEntity.userEntity;
    QReservationEntity qReservationEntity = QReservationEntity.reservationEntity;
    QCourseEntity qCourseEntity = QCourseEntity.courseEntity;
    // 회원 검색 조건
    BooleanExpression where = Expressions.TRUE;

    LocalDate today = LocalDate.now();

    // 오늘 예약이 있는 경우 필터링
    if (searchUsers.isToday()) {
      where = where.and(
              qUserEntity.id.in(
                      JPAExpressions.select(qReservationEntity.userEntity.id)
                              .from(qReservationEntity)
                              .where(qReservationEntity.date.eq(today))
              )
      );
    }

    // 담당강사 ID
    if (searchUsers.getTeacherId() != null) {
      where = where.and(
              qUserEntity.id.in(
                      JPAExpressions.select(qReservationEntity.userEntity.id)
                              .from(qReservationEntity)
                              .where(qReservationEntity.teacherEntity.userEntity.id.eq(searchUsers.getTeacherId()))
              )
      );
    }
    // 검색어
    if (searchUsers.hasSearch()) {
      switch (searchUsers.getSearch()) {
        case "ALL":
          where = where.and(
                  qUserEntity.name.contains(searchUsers.getKeyword())
                          .or(qUserEntity.loginId.contains(searchUsers.getKeyword()))
                          .or(qUserEntity.email.contains(searchUsers.getKeyword()))
                          .or(qUserEntity.company.contains(searchUsers.getKeyword()))
                          .or(qUserEntity.phone.contains(searchUsers.getKeyword()))
                          .or(qUserEntity.cellPhone.contains(searchUsers.getKeyword()))
          );
          break;
        case "name":
          where = where.and(qUserEntity.name.contains(searchUsers.getKeyword()));
          break;
        case "email":
          where = where.and(qUserEntity.email.contains(searchUsers.getKeyword()));
          break;
        case "company":
          where = where.and(qUserEntity.company.contains(searchUsers.getKeyword()));
          break;
        case "cellPhone":
          where = where.and(qUserEntity.cellPhone.contains(searchUsers.getKeyword()));
          break;
        default:
          break;
      }
    }

    Page<UserEntity> userPage = userRepository.findAll(where, searchUsers.toPageRequest());

    // 결과를 TeacherPageUserList DTO로 변환
    return userPage.map(userEntity -> {
      // 가장 최근 예약 정보 조회
      ReservationEntity recentReservation = reservationRepository.findTopByUserEntityAndTeacherEntityOrderByDateAndStartTimeDesc(userEntity.getId(), searchUsers.getTeacherId());

      // teacherName, startTime, endTime 설정
      String teacherName = null;
      String startTime = null;
      String endTime = null;

      if (recentReservation != null) {
        startTime = recentReservation.getStartTime() != null ? recentReservation.getStartTime().toString() : null;
        endTime = recentReservation.getEndTime() != null ? recentReservation.getEndTime().toString() : null;

        if (recentReservation.getTeacherEntity() != null && recentReservation.getTeacherEntity().getUserEntity() != null) {
          teacherName = recentReservation.getTeacherEntity().getUserEntity().getName();
        }
      }

      // DTO 생성
      return TeacherPageUserList.builder()
              .id(userEntity.getId())
              .name(userEntity.getName())
              .textbook(userEntity.getTextbook())
              .startTime(startTime)
              .endTime(endTime)
              .teacherName(teacherName)
              .email(userEntity.getEmail())
              .build();
    });
  }

  public List<Teacher> listTeachers() {
    List<TeacherEntity> teacherEntities = teacherRepository.findAll();
    List<Teacher> teachers = teacherEntities.stream()
            .filter(TeacherEntity::isActive)
            .map(teacherEntity -> {
              Teacher teacher = teacherServiceMapper.toTeacher(teacherEntity);
              return teacher;
            })
            .collect(Collectors.toList());

    return teachers;
  }

}
