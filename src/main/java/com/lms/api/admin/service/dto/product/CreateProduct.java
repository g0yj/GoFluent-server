package com.lms.api.admin.service.dto.product;

import com.lms.api.common.code.YN;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
//@Builder
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateProduct {

  String name; // 상품명
  int price; // 가격
  YN curriculumYN  = YN.N;; // 강의 여부
  String quantityUnit; // 수량 단위
  String createdBy;
  YN shortCourseYN = YN.N;; // 30분 여부

}