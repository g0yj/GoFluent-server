package com.lms.api.common.code;

import java.util.Arrays;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum CardCompany {
  KB("2090072", "KB"),
  NH("2090170", "농협NH"),
  LOTTE("2090076", "롯데"),
  BC("2090073", "비씨"),
  SAMSUNG("2090078", "삼성"),
  SHINHAN("2090075", "신한"),
  KEB("2090079", "외환"),
  HYUNDAI("2090074", "현대"),
  HANA("2090011", "하나"),
  CITI("2090080", "씨티"),
  ZEROPAY("3090080", "제로페이"),
  ;

  String code;
  String label;

  public static CardCompany of(String code) {
    return code == null ? null :
        Arrays.stream(values())
            .filter(value -> value.getCode().equals(code))
            .findFirst()
            .orElse(null);
  }
}
