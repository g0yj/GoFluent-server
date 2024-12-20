package com.lms.api.common.code;

import java.util.Arrays;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum MemberConsultationType {
  COURSE_REGISTRATION("10", "수강등록"), // 수강등록
  PROGRESS("20", "진도"), // 진도
  LESSON("30", "레슨"), // 레슨
  RESERVATION("40", "예약"), // 예약
  CLASS_CONTENT("50", "수업내용"), // 수업내용
  TEACHER("60", "강사"), // 강사
  ETC("70", "기타"), // 기타
  ;

  String code;
  String label;

  public static MemberConsultationType of(String code) {
    return Arrays.stream(values())
        .filter(value -> value.getCode().equals(code))
        .findFirst()
        .orElse(null);
  }

  public static String to(MemberConsultationType memberConsultationType) {
    return memberConsultationType == null ? null : memberConsultationType.getCode();
  }
}
