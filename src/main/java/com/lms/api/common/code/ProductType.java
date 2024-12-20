package com.lms.api.common.code;

import java.util.Arrays;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ProductType {
  CURRICULUM("1", "과정"), // 과정
  CONTENTS("2", "컨텐츠"), // 컨텐츠
  DEVICE("3", "기기"), // 기기
  TEXTBOOK("4", "교재"), // 교재
  PACKAGE("6", "패키지"), // 패키지
  ;

  String code;
  String label;

  public static ProductType of(String code) {
    return Arrays.stream(values())
        .filter(value -> value.getCode().equals(code))
        .findFirst()
        .orElse(null);
  }

  public static String to(ProductType productType) {
    return productType == null ? null : productType.getCode();
  }


  public static String codeByLabel(String label) {
    return Arrays.stream(values())
        .filter(value -> value.getLabel().equals(label))
        .findFirst()
        .map(ProductType::getCode)
        .orElse(null);
  }

}
