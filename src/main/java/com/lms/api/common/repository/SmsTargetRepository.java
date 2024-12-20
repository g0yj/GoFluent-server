package com.lms.api.common.repository;

import com.lms.api.common.code.SmsStatus;
import com.lms.api.common.entity.SmsTargetEntity;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

public interface SmsTargetRepository extends JpaRepository<SmsTargetEntity, Long>,
    QuerydslPredicateExecutor<SmsTargetEntity> {

  List<SmsTargetEntity> findByRecipientPhone(String recipientPhone);

  List<SmsTargetEntity> findByRecipientPhoneIn(List<String> recipientPhones);

  List<SmsTargetEntity> findByIdInAndStatus(List<Long> ids, SmsStatus status);

  List<SmsTargetEntity> findBySmsEntity_Id(Long smsId);

  @Modifying
  @Query(value = "UPDATE sms_target SET message_id = :messageId, modified_on = :modifiedOn WHERE id = :id", nativeQuery = true)
  void updateMessageIdAndModifiedOn(@Param("messageId") String messageId,
      @Param("modifiedOn") LocalDateTime modifiedOn, @Param("id") Long id);

  @Modifying
  @Query(value = "UPDATE sms_target SET status = :status, modified_on = :modifiedOn WHERE id = :id", nativeQuery = true)
  void updateStatusAndModifiedOn(@Param("status") String status,
      @Param("modifiedOn") LocalDateTime modifiedOn, @Param("id") Long id);
}
