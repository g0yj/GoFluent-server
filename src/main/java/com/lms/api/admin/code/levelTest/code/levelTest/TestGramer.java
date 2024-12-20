package com.lms.api.admin.code.levelTest.code.levelTest;

import java.util.Arrays;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum TestGramer {
  NONE("0"),
  NV("10"),
  SD("20"),
  ST("30"),
  U("40"),
  A("50"),
  ;

  String code;


  public static TestGramer of(String code) {
    return Arrays.stream(values())
        .filter(value -> value.getCode().equals(code))
        .findFirst()
        .orElse(null);
  }

  public static String to(TestGramer testGramer) {
    return testGramer == null ? null : testGramer.getCode();
  }

}
