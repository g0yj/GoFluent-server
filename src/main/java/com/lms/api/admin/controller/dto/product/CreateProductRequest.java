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
public class CreateProductRequest {

  @NotNull
  String name; // 상품명
  @NotNull
  int price; // 가격
  @NotNull
  YN curriculumYN; // 강의 여부
  YN shortCourseYN; // 30분 여부

}
