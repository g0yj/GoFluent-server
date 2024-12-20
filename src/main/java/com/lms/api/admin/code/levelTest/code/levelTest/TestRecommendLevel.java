package com.lms.api.admin.code.levelTest.code.levelTest;

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
  R3("30"),//300
  R4("40"), // 400
  R5("50"), // 500
  R6("60"),// 600
  R7("70"),// 700
  R8("80"),// 800
  R9("90"),// 900
  ETC("20"),// ETC
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
