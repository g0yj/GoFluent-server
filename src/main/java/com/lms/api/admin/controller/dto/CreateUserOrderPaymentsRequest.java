package com.lms.api.admin.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lms.api.common.code.PaymentType;
import com.lms.api.common.exception.LmsErrorCode;
import com.lms.api.common.exception.LmsException;
import com.lms.api.common.util.NumberUtils;
import com.lms.api.common.util.ObjectUtils;
import com.lms.api.common.util.StringUtils;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateUserOrderPaymentsRequest {

  @NotNull
  PaymentType type; //결제 유형 (C:취소, I:신규, P:회수, R:환불, T:변경)

  @NotNull
  @JsonFormat(pattern = "yyyy-MM-dd")
  LocalDate paymentDate; //결제일

  Integer cashAmount; //현금금액
  Integer depositAmount; // 예금금액
  Boolean isReceiptIssued; //영수증 발행 여부
  String receiptNumber; // 영수증 번호
  String accountHolder; // 예금주명

  @NotNull
  Integer receivableAmount; //미수금액

  String receivableReason; //미수금사유

  @JsonFormat(pattern = "yyyy-MM-dd")
  LocalDate recallDate;//회수예정일

  String memo;
  List<Card> cards;

  public List<Card> getCards() {
    return cards == null ? List.of() : cards.stream().filter(card -> NumberUtils.isPositive(card.amount)).toList();
  }

  public int getTotalCardAmount() {
    return this.getCards().stream().mapToInt(Card::getAmount).sum();
  }

  public void validate() {
    if (!NumberUtils.isPositive(cashAmount) && !NumberUtils.isPositive(depositAmount) && !NumberUtils.isPositive(getTotalCardAmount())) {
      throw new LmsException(LmsErrorCode.BAD_REQUEST, "결제 금액을 입력해주세요.");
    }

    if (NumberUtils.isPositive(cashAmount) && ObjectUtils.equals(isReceiptIssued, true) && StringUtils.hasNotText(receiptNumber)) {
      throw new LmsException(LmsErrorCode.BAD_REQUEST, "영수증 번호를 입력해주세요.");
    }

    if (NumberUtils.isPositive(depositAmount) && StringUtils.hasNotText(accountHolder)) {
      throw new LmsException(LmsErrorCode.BAD_REQUEST, "예금자명을 입력해주세요.");
    }

    this.getCards().forEach(Card::validate);
  }

  @Getter
  @FieldDefaults(level = AccessLevel.PRIVATE)
  public static class Card {
    Integer amount; //결제금액

    // CardCompany cardCompany; //카드사
    String code;
    String codeName;

    String cardNumber; // 카드번호
    Integer installmentMonths; //할부개월
    String approvalNumber;//승인번호

    public void validate() {
      if (NumberUtils.isPositive(amount) && (code == null || StringUtils.hasNotText(cardNumber))) {
        throw new LmsException(LmsErrorCode.BAD_REQUEST, "카드 정보를 입력해주세요.");
      }
    }
  }
}
