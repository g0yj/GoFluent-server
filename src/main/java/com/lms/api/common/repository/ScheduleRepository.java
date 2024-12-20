package com.lms.api.common.repository;

import com.lms.api.common.code.ScheduleType;
import com.lms.api.common.entity.ScheduleEntity;
import com.lms.api.common.entity.TeacherEntity;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Long>, QuerydslPredicateExecutor<ScheduleEntity> {

  List<ScheduleEntity> findAllByDateBetween(LocalDate startDate, LocalDate endDate);

  List<ScheduleEntity> findAllByTeacherEntityAndDateBetween(TeacherEntity teacherEntity, LocalDate startDate, LocalDate endDate);

  Optional<ScheduleEntity> findByTeacherEntityAndDateAndStartTime(TeacherEntity teacherEntity, LocalDate date, LocalTime startTime);

  List<ScheduleEntity> findAllByDateAndTypeAndTeacherEntity_ActiveTrue(LocalDate date, ScheduleType type);

  List<ScheduleEntity> findAllByDateAndTypeAndTeacherEntity_UserIdAndTeacherEntity_ActiveTrue(LocalDate date, ScheduleType type, String teacherId);

  List<ScheduleEntity> findAllByDateAndTypeAndStartTimeAndTeacherEntity_ActiveTrue(LocalDate date, ScheduleType type, LocalTime time);

  List<ScheduleEntity> findByDate(LocalDate date);

  List<ScheduleEntity> findByDateAndTeacherEntity_UserId(LocalDate date, String teacherId);

  List<ScheduleEntity> findByTeacherEntityAndDateAndStartTimeBetween(
      TeacherEntity teacherEntity,
      LocalDate date,
      LocalTime startTime,
      LocalTime endTime
  );

  List<ScheduleEntity> findAllByTypeAndDateBetween(ScheduleType type, LocalDate startDate, LocalDate endDate);

  @Query("SELECT s.teacherEntity.userEntity.id, s.teacherEntity.userEntity.name " +
          "FROM ScheduleEntity s " +
          "WHERE s.date BETWEEN :dateFrom AND :dateTo " +
          "GROUP BY s.teacherEntity.userEntity.id, s.teacherEntity.userEntity.name")
  List<Object[]> findTeacherIdsByDateBetweenGroupedByStartTime(@Param("dateFrom") LocalDate dateFrom, @Param("dateTo") LocalDate dateTo);

  List<ScheduleEntity> findAllByDateAndStartTimeAndTypeAndTeacherEntity_UserId(LocalDate date, LocalTime startTime, ScheduleType type, String teacherId);
}
