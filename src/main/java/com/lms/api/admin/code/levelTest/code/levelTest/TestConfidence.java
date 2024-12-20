package com.lms.api.admin.code.levelTest.code.levelTest;

import java.util.Arrays;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum TestConfidence {
  NONE("0"),
  CL("10"),//completely lacking
  L("20"),//lacking
  A("30"),//average
  AA("40"),//above average
  VC("50")//very confiden
  ;

  String code;


  public static TestConfidence of(String code) {
    return Arrays.stream(values())
        .filter(value -> value.getCode().equals(code))
        .findFirst()
        .orElse(null);
  }

  public static String to(TestConfidence confidence) {
    return confidence == null ? null : confidence.getCode();
  }

}
