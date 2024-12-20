package com.lms.api.admin.code.levelTest;

import java.util.Arrays;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum TestComprehension {
  NONE("0"),
  AN("10"),//almost nothing
  SP("20"),//some parts
  MP("30"),//most parts
  AE("40"),//almost everything
  E("50")//everything
  ;

  String code;


  public static TestComprehension of(String code) {
    return Arrays.stream(values())
        .filter(value -> value.getCode().equals(code))
        .findFirst()
        .orElse(null);
  }


  public static String to(TestComprehension comprehension) {
    return comprehension == null ? null : comprehension.getCode();
  }

}
