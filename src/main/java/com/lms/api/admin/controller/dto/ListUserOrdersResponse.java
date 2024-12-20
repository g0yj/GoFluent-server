package com.lms.api.admin.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListUserOrdersResponse {

  List<Order> order;

  @Getter
  @Builder
  @FieldDefaults(level = AccessLevel.PRIVATE)
  public static class Order {

    String id;
    int supplyAmount;
    int discountAmount;
    int billingAmount;
    int paymentAmount;
    int receivableAmount;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    LocalDateTime createdOn;

    String creatorName;
    String orderProductName;
  }
}
