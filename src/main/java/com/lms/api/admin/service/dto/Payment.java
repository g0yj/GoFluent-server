package com.lms.api.admin.service.dto;

import com.lms.api.common.code.PaymentMethod;
import com.lms.api.common.code.PaymentType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Payment {

  String id;
  LocalDate paymentDate;
  PaymentType type;
  PaymentMethod paymentMethod;
  int paymentAmount;
  String accountHolder;
  String transactionName;
  Integer installmentMonths;

  //CardCompany cardCompany;
  String code;
  String codeName;

  String cardNumber;
  String approvalNumber;
  int cancelAmount;
  LocalDate cancelDate;
  String cancelManager;
  String memo;
  LocalDateTime createdOn;
  String createdBy;
  String creatorName;


  public Integer getInstallmentMonths() {
    if (installmentMonths != null && installmentMonths > 1) {
      return installmentMonths;
    }

    return null;
  }

  public boolean isCancelable() {
    return isCancelable(type, cancelDate);
  }

  public static boolean isCancelable(PaymentType type, LocalDate cancelDate) {
    return type != PaymentType.C && type != PaymentType.R && cancelDate == null;
  }
}
