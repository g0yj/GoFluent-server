package com.lms.api.admin.code.levelTest;

import java.util.Arrays;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum TestStudyType {
  EC("10"), // English Conversation
  BE("20"),//Business English
  TS("30"), // Toeic Speaking
  I("40"),// Interview
  ETC("50"), //
  ;

  String code;

  public static TestStudyType of(String code) {
    return Arrays.stream(values())
        .filter(value -> value.getCode().equals(code))
        .findFirst()
        .orElse(null);
  }

  public static String to(TestStudyType testStudyType) {
    return testStudyType == null ? null : testStudyType.getCode();
  }


}
