package com.lms.api.admin.controller.dto.product;


import com.lms.api.common.code.YN;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateProductRequest {

  @NotNull
  String name; // 상품명
  @NotNull
  int price; // 가격
  @NotNull
  YN curriculumYN = YN.N; // 강의 여부
  YN shortCourseYN = YN.N; // 30분 여부

}
