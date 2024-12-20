package com.lms.api.admin.service.dto;

import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateOrderRefund {

  String userId;
  String orderId;
  String orderProductId;
  LocalDate refundDate;
  int cardAmount;
  int cashAmount;
  int depositAmount;
  String bank;
  String accountNumber;
  String refundReason;
  String createdBy;

  public int getTotalRefundAmount() {
    return cardAmount + cashAmount + depositAmount;
  }
}
