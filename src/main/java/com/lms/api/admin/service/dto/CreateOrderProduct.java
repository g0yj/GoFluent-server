package com.lms.api.admin.service.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateOrderProduct {

  String orderId;
  String userId;
  String productId;
  int months;
  int quantity;
  String teacherId;
  String assistantTeacherId;
  Boolean isRetake;
  String retakeTeacherId;
  String retakeNote;
  int discountAmount;
  int billingAmount;
  String productOption;
  String createdBy;
}
