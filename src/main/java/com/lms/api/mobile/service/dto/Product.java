package com.lms.api.mobile.service.dto;

import com.lms.api.common.code.LanguageCode;
import com.lms.api.common.code.LessonType;
import com.lms.api.common.code.ProductType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {
  String id;
  ProductType type;
  LanguageCode language;
  LessonType lessonType;
  String name;
  int price;
  String quantityUnit;
}
