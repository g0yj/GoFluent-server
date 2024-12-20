package com.lms.api.common.dto;

import java.time.LocalDateTime;

public interface Order {

  String getId();

  String getCreatorName();

  String getOrderProductName();

  String getUserName();

  String getUserId();

  Integer getBillingAmount();

  Integer getPaymentAmount();

  Integer getRefundAmount();

  Integer getReceivableAmount();

  LocalDateTime getCreatedOn();
}
