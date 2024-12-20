package com.lms.api.common.code;

import java.util.Arrays;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum StudyPurpose {
  COMMON_ENGLISH("10"), // 생활영어
  WORK("20"), //업무
  STUDY_ABROAD("30"), // 유학
  DEVELOPMENT("40"), // 자기개
  EMPLOYMENT("50"), // 취업
  ETC("60") // 기타
  ;

  String code;

  public static StudyPurpose of(String code) {
    return Arrays.stream(values())
        .filter(value -> value.getCode().equals(code))
        .findFirst()
        .orElse(null);
  }
}
