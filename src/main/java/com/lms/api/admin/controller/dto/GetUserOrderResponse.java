package com.lms.api.admin.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lms.api.common.code.YN;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter //추가. 총 환불금액을 구하기 위함
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetUserOrderResponse {

  String id;
  int supplyAmount;
  int discountAmount;
  int billingAmount;
  int refundAmount;

  @JsonProperty("isCancelable")
  boolean cancelable;

  List<OrderProduct> orderProducts;

  @Getter
  @Builder
  @FieldDefaults(level = AccessLevel.PRIVATE)
  public static class OrderProduct {

    String id;
    String name;
    int amount;
    int discountAmount;
    int billingAmount;
    int refundAmount;
    boolean isRetake;
    //추가
    int paymentAmount;
    int months;
    int quantity;
    String productName;
    YN productType ; // 수강권여부
    //int totalRefundAmount;//추가 (환불금액을 구하기 위함)


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    LocalDateTime createdOn;

    @Default
    String orderType = "신규";

    @JsonProperty("hasReservations")
    boolean hasReservations;

    @Setter
    @JsonProperty("hasPayments")
    boolean hasPayments;

    @Setter
    RefundType refundType;
  }

  public enum RefundType {
    CANCELABLE, REFUNDABLE, REFUNDED
  }
}
