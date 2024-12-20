package com.lms.api.admin.controller.dto;

import com.lms.api.common.code.LanguageCode;
import com.lms.api.common.code.LessonType;
import com.lms.api.common.code.ProductType;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListProductsRequest {

  @NotNull
  ProductType type;

  LanguageCode language;
  LessonType lessonType;
}
