package com.lms.api.common.code;

import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum LanguageCode {
  EN("영어"), // 영어
  CN("중국어"), // 중국어
  JP("일본어"), // 일본어
  KR("한국어"), // 한국어
  ETC("기타") // 기타
  ;

  String label;

  public static List<LanguageCode> getProductLanguages() {
    return List.of(EN, CN, JP, KR);
  }
}
