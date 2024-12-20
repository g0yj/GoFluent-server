package com.lms.api.admin.code.levelTest;

import java.util.Arrays;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum TestRecommendLevel {
  R2("10"), // Pre
  R3("20"),//300
  R4("30"), // 400
  R5("40"), // 500
  R6("50"),// 600
  R7("60"),// 700
  R8("70"),// 800
  R9("80"),// 900
  ETC("90"),// ETC
  ;

  String code;


  public static TestRecommendLevel of(String code) {
    return Arrays.stream(values())
        .filter(value -> value.getCode().equals(code))
        .findFirst()
        .orElse(null);
  }

  public static String to(TestRecommendLevel testRecommendLevel) {
    return testRecommendLevel == null ? null : testRecommendLevel.getCode();
  }

}
