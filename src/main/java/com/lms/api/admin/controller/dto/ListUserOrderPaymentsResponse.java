package com.lms.api.admin.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListUserOrderPaymentsResponse {

  int billingAmount;
  int paymentAmount;
  int refundAmount;
  int receivableAmount;
  List<Payment> payments;
  List<Refund> refunds;


  @Getter
  @Builder
  @FieldDefaults(level = AccessLevel.PRIVATE)
  public static class Payment {
    String id;


    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate paymentDate;

    String type; // 결제구분(취소,신규,회수,환불,변경)
    String paymentMethod; // 결제방식(카드, 예금, 현금)
    int paymentAmount; // 결제금액
    String accountHolder; //예금자명

 /*   CardCompany cardCompany; // 카드사
    String cardCompanyLabel;
    */

    String code;
    String codeName;

    String cardNumber;
    Integer installmentMonths;
    String installmentMonthsLabel;
    String approvalNumber;
    String memo;
    String modifiedBy;
    String modifierName;

    @JsonProperty("isCancelable")
    boolean cancelable;

    public String getInstallmentMonthsLabel() {
      return installmentMonthsLabel == null ? "일시불" : installmentMonthsLabel;
    }
  }

  @Getter
  @Builder
  @FieldDefaults(level = AccessLevel.PRIVATE)
  public static class Refund {
    String id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate refundDate;

    String orderProductName;
    int refundAmount;
    int cardAmount;
    int cashAmount;
    int depositAmount;
    String bank;
    String accountNumber;
    String refundReason;
    String modifierName;
  }
}
