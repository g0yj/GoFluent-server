package com.lms.api.common.code;

import java.util.Arrays;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum LanguageTest {
  SJPT("SJPT"), // 일본어
  HKC("HKC"), // 한국어
  TOEIC("TOEIC"), // 영어
  TSC("TSC"), // 중국어
  TOEIC_S("TOEIC-S"), // 영어
  OPIC("OPIc"), // 영어
  ETC("기타");

  String code;

  public static LanguageTest of(String code) {
    if ("TOEIC_S".equals(code)) {
      code = "TOEIC-S";
    } else if ("OPIC".equals(code)) {
      code = "OPIc";
    } else if ("ETC".equals(code)) {
      code = "기타";
    }
    String finalCode = code;
    return Arrays.stream(values())
            .filter(value -> value.getCode().equals(finalCode))
            .findFirst()
            .orElse(null);
  }

}
/*  public static LanguageTest of(String code) {
    return Arrays.stream(values())
        .filter(value -> value.getCode().equals(code))
        .findFirst()
        .orElse(null);
  }*/