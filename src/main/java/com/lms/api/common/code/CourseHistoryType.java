package com.lms.api.common.code;

import java.util.Arrays;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum CourseHistoryType {
  ATTENDANCE("출결"),
  ASSIGNMENT("배정"),
  CANCEL_ASSIGNMENT("배정취소"),
  CHANGE("변경"),
  ORDER("주문"),
  CHANGE_PASSWORD("패스워드 변경"),
  ;

  String code;

  public static CourseHistoryType of(String code) {
    return code == null ? null :
        Arrays.stream(values())
            .filter(value -> value.getCode().equals(code))
            .findFirst()
            .orElse(null);
  }

  public static String to(CourseHistoryType courseHistoryType) {
    return courseHistoryType == null ? null : courseHistoryType.getCode();
  }
}
