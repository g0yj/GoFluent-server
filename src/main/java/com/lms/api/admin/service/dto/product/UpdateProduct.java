package com.lms.api.admin.service.dto.product;

import com.lms.api.common.code.YN;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateProduct {

  String id;
  String name; // 상품명
  int price; // 가격
  YN curriculumYN; // 강의 여부
  YN shortCourseYN; // 30분 여부
  String quantityUnit;

  String modifiedBy;


}