package com.lms.api.common.code;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum CourseStatus {
  WAITING("대기"), // 대기
  NORMAL("정상"), // 정상
  CANCEL("취소"), // 취소
  ;

  String label;
}
