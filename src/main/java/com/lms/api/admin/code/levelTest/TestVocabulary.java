package com.lms.api.admin.code.levelTest;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum TestVocabulary {
  V,//very
  L, // limited
  A,//average
  AA, // above average
  E,// extensive
  ;


}
