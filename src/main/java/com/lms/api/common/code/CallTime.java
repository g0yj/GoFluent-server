package com.lms.api.common.code;

import java.util.Arrays;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum CallTime {
  ALL("0"),
  TIME_01("10"), // 06:30~08:00
  TIME_02("20"), // 08:00~10:00
  TIME_03("30"), // 10:00~12:00
  TIME_04("40"), // 12:00~14:00
  TIME_05("50"), // 14:00~16:00
  TIME_06("60"), // 16:00~18:00
  TIME_07("70"), // 18:00~20:00
  TIME_08("80"), // 20:00~21:00
  TIME_09("90"), // 기타
  ;

  String code;

  public static CallTime of(String code) {
    return Arrays.stream(values())
        .filter(value -> value.getCode().equals(code))
        .findFirst()
        .orElse(null);
  }

  public static String to(CallTime callTime) {
    return callTime == null ? null : callTime.getCode();
  }
}
