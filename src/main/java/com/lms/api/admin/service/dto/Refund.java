package com.lms.api.admin.service.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Refund {

  String id;
  LocalDate refundDate;
  int refundAmount;
  int cashAmount;
  int depositAmount;
  int cardAmount;
  String bank;
  String accountNumber;
  String refundReason;
  LocalDateTime createdOn;
  String createdBy;
  String creatorName;
}
