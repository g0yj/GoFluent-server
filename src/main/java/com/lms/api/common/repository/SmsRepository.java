package com.lms.api.common.repository;

import com.lms.api.common.code.SmsStatus;
import com.lms.api.common.entity.SmsEntity;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

public interface SmsRepository extends JpaRepository<SmsEntity, Long>,
    QuerydslPredicateExecutor<SmsEntity> {

//  List<SmsEntity> findAllByStatusAndRequestIdIsNotNull(SmsStatus status);

  @Query("""
        SELECT s FROM SmsEntity s 
          LEFT JOIN FETCH s.smsTargetEntities 
        WHERE s.status = :status 
          AND s.requestId IS NOT NULL
      """)
  List<SmsEntity> findAllByStatusAndRequestIdIsNotNull(@Param("status") SmsStatus status);

  @Modifying
  @Query(value = "UPDATE sms SET status = :status, modified_on = :modifiedOn WHERE id = :id", nativeQuery = true)
  void updateStatusAndModifiedOn(@Param("status") String status,
      @Param("modifiedOn") LocalDateTime modifiedOn, @Param("id") Long id);
}
