package com.lms.api.common.dto;

import java.time.LocalDateTime;

public interface Payment {

  String getOrderId();

  String getPaymentId();

  String getCreatorName();

  LocalDateTime getCreatedOn();

  String getPaymentType();

  String getTransactionName();

  Integer getInstallmentMonths();

  String getCardNumber();

  Integer getPaymentAmount();

  Integer getRefundAmount();
}
