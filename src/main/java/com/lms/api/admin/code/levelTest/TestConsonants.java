package com.lms.api.admin.code.levelTest;

import java.util.Arrays;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum TestConsonants {
  R("10", "r"),
  L("20", "l"),
  P("30", "p"),
  F("40", "f"),
  B("50", "b"),
  V("60", "v"),
  S("70","s"),
  Z("80", "z"),
  SH("90", "ʃ"),
  EZH("100", "ʒ"),
  CH("110", "ʧ"),
  G("120", "ʤ"),
  TTH("130", "ɵ"),
  TH("140", "ð")
  ;
  String code;
  String label;


  public static TestConsonants of(String code) {
    return Arrays.stream(values())
        .filter(value -> value.getCode().equals(code))
        .findFirst()
        .orElse(null);
  }

  public static String to(TestConsonants consonants) {
    return consonants == null ? null : consonants.getCode();
  }

}
