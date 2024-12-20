package com.lms.api.admin.service.dto;

import com.lms.api.common.code.PaymentType;
import java.time.LocalDate;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateOrderPayments {

  String userId;
  String orderId;
  PaymentType type; //결제 유형 (C:취소, I:신규, P:회수, R:환불, T:변경)
  LocalDate paymentDate;
  int cashAmount;
  int depositAmount;
  boolean receiptIssued;
  String receiptNumber;
  String accountHolder;
  int receivableAmount;
  String receivableReason;
  LocalDate recallDate;
  String memo;
  String createdBy;
  List<Card> cards;

  public int getTotalPaymentAmount() {
    return cashAmount + depositAmount + getTotalCardAmount();
  }

  public int getTotalCardAmount() {
    return cards.stream().mapToInt(Card::getAmount).sum();
  }

  @Getter
  @Builder
  @FieldDefaults(level = AccessLevel.PRIVATE)
  public static class Card {
    int amount;

    //CardCompany cardCompany;
    String cardCompany;

    String cardNumber;
    int installmentMonths;
    String approvalNumber;

    public Integer getInstallmentMonths() {
      return installmentMonths < 2 ? null : installmentMonths;
    }
  }
}
