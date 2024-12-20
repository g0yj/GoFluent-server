package com.lms.api.admin.code.levelTest.code.levelTest;

import java.util.Arrays;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum
TestVowels {
  A("iː", "20"),
  B("ɪ", "30"),
  C("ɛ", "40"),
  D("æ", "50"),
  E("ɔː", "60"),
  F("ʋ ", "70"),
  G("uː", "80"),
  H("ʌ", "90"),
  I("ə", "100"),
  J("eɪ", "110"),
  K("oʋ", "120"),
  L("aɪ", "130"),
  M("aʋ", "140"),
  N("ɔɪ", "150"),
  ;

  String label;
  String code;


  public static TestVowels of(String code) {
    return Arrays.stream(values())
        .filter(value -> value.getCode().equals(code))
        .findFirst()
        .orElse(null);
  }

  public static String to(TestVowels testVowels) {
    return testVowels == null ? null : testVowels.getCode();
  }
}
