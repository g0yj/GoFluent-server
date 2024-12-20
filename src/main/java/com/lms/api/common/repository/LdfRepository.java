package com.lms.api.common.repository;

import com.lms.api.admin.service.dto.statistics.Evaluation;
import com.lms.api.common.entity.LdfEntity;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LdfRepository extends JpaRepository<LdfEntity, Long>,
    QuerydslPredicateExecutor<LdfEntity> {

  Optional<LdfEntity> findByReservationEntity_Id(Long reservationId);

  List<LdfEntity> findByUserEntity_Id(String userId);

  @Query("SELECT new com.lms.api.admin.service.dto.statistics.Evaluation(" +
         "ldf.reservationEntity.teacherEntity.userEntity.name, " +
         "ldf.reservationEntity.teacherEntity.userId, " +
         "COUNT(ldf), " +
         "ROUND(AVG(ldf.grade),2), " +
         "ROUND((AVG(ldf.grade) * COUNT(ldf)),2), " +
         ":startDate) " +
         "FROM LdfEntity ldf " +
         "WHERE ldf.reservationEntity.date BETWEEN :startDate AND :endDate " +
         "AND ldf.grade IS NOT NULL " +
         "GROUP BY ldf.reservationEntity.teacherEntity.userEntity.name, " +
         "ldf.reservationEntity.teacherEntity.userId")
  List<Evaluation> findEvaluationsByDateRange(@Param("startDate") LocalDate startDate,
      @Param("endDate") LocalDate endDate);

  @Query("SELECT ldf FROM LdfEntity ldf " +
         "JOIN ldf.reservationEntity reservation " +
         "JOIN reservation.teacherEntity teacher " +
         "JOIN teacher.userEntity user " +
         "WHERE teacher.userId = :teacherId " +
         "AND reservation.date BETWEEN :startDate AND :endDate " +
         "AND ldf.grade IS NOT NULL")
  List<LdfEntity> findLdfsByTeacherIdAndDateRange(@Param("teacherId") String teacherId,
      @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    Optional<LdfEntity> findByReservationEntityId(Long reservationEntityId);
}