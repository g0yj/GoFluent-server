package com.lms.api.admin.controller.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateUserOrderProductRequest {


  String orderId;

  @NotEmpty
  String productId;
  @NotNull
  int months;
  @NotNull
  int quantity;
  String teacherId;
  String assistantTeacherId;
  Boolean isRetake; // 재등록여부 -> 추후 DB에는 Y,N로 저장
  String retakeTeacherId; //재등록 강사
  String retakeNote; // 재등록 비고
  int discountAmount; // 할인금액
  int billingAmount; // 실청구금액
  String productOption;
}
