package com.lms.api.common.code;

import java.util.Arrays;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum JoinPath {
  ALL("00"),// 구분
  SIGN("10"), // 간판
  ONLINE("20"), // 온라인검색
  RECOMMEND("30"), // 지인추천
  FAN("40"), // 부채
  LEAFLET("50"), // 3단리플릿
  SUBWAY("60"), // 지하철광고
  ALLIANCE("70"), // 기업제휴
  ETC("900"), // 기타
  ;

  String code;

  public static JoinPath of(String code) {
    return code == null ? null :
        Arrays.stream(values())
            .filter(value -> value.getCode().equals(code))
            .findFirst()
            .orElse(null);
  }

  public static String to(JoinPath joinPath) {
    return joinPath == null ? null : joinPath.getCode();
  }
}
