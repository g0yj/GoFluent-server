package com.lms.api.admin.service.dto;

import com.lms.api.common.code.CardCompany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateOrderPayment {

  String userId;
  String orderId;
  String paymentId;
  String modifiedBy;
  CardCompany cardCompany;
  String cardNumber;
  Integer installmentMonths;
  String approvalNumber;
}
