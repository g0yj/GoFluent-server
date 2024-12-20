package com.lms.api.admin.service.dto;

import com.lms.api.common.util.ListUtils;
import com.lms.api.common.util.ObjectUtils;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Data
@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order {

  String id;
  int supplyAmount;
  int discountAmount;
  int billingAmount;
  int paymentAmount;
  int receivableAmount;
  int refundAmount;
  LocalDateTime createdOn;
  String createdBy;
  String creatorName;

  User user;
  List<OrderProduct> orderProducts;
  List<Payment> payments;


  public String getOrderProductName() {
    if (ListUtils.isEmpty(orderProducts)) {
      return null;
    }

    return orderProducts.get(0).getName();
  }

  public void addPayment(Payment payment) {
    if (payments == null) {
      payments = new ArrayList<>();
    }

    payments.add(payment);
  }

  public void addOrderProduct(OrderProduct orderProduct) {
    if (orderProducts == null) {
      orderProducts = new ArrayList<>();
    }

    orderProducts.add(orderProduct);
  }

  public boolean isCancelable() {
    return ObjectUtils.isEmpty(payments) && orderProducts.stream().flatMap(orderProduct -> orderProduct.getRefunds().stream()).toList().isEmpty();
  }
}
