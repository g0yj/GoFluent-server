package com.lms.api.admin.code.levelTest;

import java.util.Arrays;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum TestClarity {
  NONE("0"),
  H("10"), // hard to understand
  A("20"),//average
  E("30"), //easy to understand

  ;

  String code;


  public static TestClarity of(String code) {
    return code == null ? null :
        Arrays.stream(values())
            .filter(value -> value.getCode().equals(code))
            .findFirst()
            .orElse(null);
  }

  public static String to(TestClarity clarity) {
    return clarity == null ? null : clarity.getCode();
  }

}
