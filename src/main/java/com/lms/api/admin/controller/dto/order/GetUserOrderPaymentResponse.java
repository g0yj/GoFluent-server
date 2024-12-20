package com.lms.api.admin.controller.dto.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lms.api.common.code.CardCompany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;



@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetUserOrderPaymentResponse {

  String id; // payment 식별키

  CardCompany cardCompany; //카드사(카드종류)
  String cardNumber; // 카드번호
  Integer installmentMonths; // 할부개월수(0 또는 null: 일시불)
  String approvalNumber; // 승인번호

  String modifiedBy; //처리자
  String modifiedName; //처리자명


}
