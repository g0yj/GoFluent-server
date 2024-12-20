package com.lms.api.admin.code.levelTest;

import java.util.Arrays;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum TestIntonation {
  NONE("0"),
  VK("10"), // very Korean
  K("20"),//a bit Korean
  A("30"), // acceptable
  N("40"),// near native
  ;

  String code;


  public static TestIntonation of(String code) {
    return Arrays.stream(values())
        .filter(value -> value.getCode().equals(code))
        .findFirst()
        .orElse(null);
  }

  public static String to(TestIntonation intonation) {
    return intonation == null ? null : intonation.getCode();
  }

}
