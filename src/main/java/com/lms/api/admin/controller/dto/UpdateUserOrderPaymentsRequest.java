package com.lms.api.admin.controller.dto;

import com.lms.api.common.code.CardCompany;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateUserOrderPaymentsRequest {

  @NotNull
  String modifiedBy;

  CardCompany cardCompany;
  String cardNumber;
  Integer installmentMonths;
  String approvalNumber;
}
