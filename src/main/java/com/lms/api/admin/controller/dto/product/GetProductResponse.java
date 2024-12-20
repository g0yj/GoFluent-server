package com.lms.api.admin.controller.dto.product;

import com.lms.api.common.code.YN;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetProductResponse {
  String id;
  String name;
  int price;
  YN curriculumYN;
  YN shortCourseYN;
}
