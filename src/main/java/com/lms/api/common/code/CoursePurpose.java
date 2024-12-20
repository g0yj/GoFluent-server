package com.lms.api.common.code;

import java.util.Arrays;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum CoursePurpose {
  STUDY_ABROAD("10"), // 유학
  TEST("20"), // 시험
  EMPLOYMENT("30"), // 취업
  WORK("40"), //업무
  DEVELOPMENT("50"), // 자기개발
  ;

  String code;

/*
  public static CoursePurpose of(String code) {
    return Arrays.stream(values())
        .filter(value -> value.getCode().equals(code))
        .findFirst()
        .orElse(null);
  }
*/
  //coursePurpose가 null이 아니고 올바르게 형식화되어 있는지 확인
  public static CoursePurpose of(String codeOrName) {
    return Arrays.stream(values())
            .filter(value -> value.getCode().equals(codeOrName) || value.name().equalsIgnoreCase(codeOrName))
            .findFirst()
            .orElse(null);
  }

}
