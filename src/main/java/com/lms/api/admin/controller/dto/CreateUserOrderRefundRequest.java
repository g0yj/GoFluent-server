package com.lms.api.admin.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lms.api.common.util.NumberUtils;
import com.lms.api.common.util.StringUtils;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateUserOrderRefundRequest {

  @NotNull
  @JsonFormat(pattern = "yyyy-MM-dd")
  LocalDate refundDate;

  Integer cardAmount;
  Integer cashAmount;
  Integer depositAmount;
  String bank;
  String accountNumber;
  String refundReason;

  public void validate() {
    if (!NumberUtils.isPositive(cardAmount) && !NumberUtils.isPositive(cashAmount) && !NumberUtils.isPositive(depositAmount)) {
      throw new IllegalArgumentException("환불 금액을 입력해주세요.");
    }

    if (NumberUtils.isPositive(depositAmount) && !StringUtils.hasAllText(bank, accountNumber)) {
      throw new IllegalArgumentException("환불 계좌 정보를 입력해주세요.");
    }
  }
}
