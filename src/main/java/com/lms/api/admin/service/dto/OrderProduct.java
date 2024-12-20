package com.lms.api.admin.service.dto;

import com.lms.api.common.code.YN;
import com.lms.api.common.util.ListUtils;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
//@Setter // totalRefundAmount 사용을 위해 추가
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderProduct {
  //
  String id;
  //추가
  String productName;
  YN curriculumYN ; // 강의 여부

  int months;
  int quantity;
  int amount;
  int discountAmount;
  int paymentAmount;
  int billingAmount;
  String productOption;
  LocalDateTime createdOn;
  Boolean hasReservations;

  Product product;
  List<Refund> refunds;

  //추가 (환불금액을 구하기 위함)
  @Setter
  int refundAmount;

  public int getRefundAmount() {
    if (ListUtils.isEmpty(refunds)) {
      return 0;
    }

    return refunds.stream().mapToInt(Refund::getRefundAmount).sum();
  }

  public String getName() {
    return getCurriculumName(product.getName(), months, quantity, product.getQuantityUnit());
  }

  public static String getCurriculumName(String productName, int months, int quantity,
      String productQuantityUnit) {
    return productName + "/" + months + "개월/" + quantity + productQuantityUnit;
  }

  public void addRefund(Refund refund) {
    if (refunds == null) {
      refunds = new ArrayList<>();
    }

    refunds.add(refund);
  }
}
