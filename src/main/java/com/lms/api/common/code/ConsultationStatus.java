package com.lms.api.common.code;

import java.util.Arrays;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ConsultationStatus {
  WAITING("3"), // 대기
  // 컨텍금지
  NO_CONTACT("4"),
  UNVISITED("5"),
  RESERVED("6"),
  NOT_REGISTERED("7"),
  REGISTERED("8"),
  NO_SHOW("9"),
  MISSED("10"),
  ;

  String code;

  public static ConsultationStatus of(String code) {
    return Arrays.stream(values())
        .filter(value -> value.getCode().equals(code))
        .findFirst()
        .orElse(null);
  }

  public static String to(ConsultationStatus consultationStatus) {
    return consultationStatus == null ? null : consultationStatus.getCode();
  }

}
