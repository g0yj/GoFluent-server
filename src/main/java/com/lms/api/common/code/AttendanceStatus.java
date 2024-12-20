package com.lms.api.common.code;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum AttendanceStatus {
  Y("출석"), // 출석
  N("결석"), // 결석
  R("예약") // 예약
  ;

  String label;
}
