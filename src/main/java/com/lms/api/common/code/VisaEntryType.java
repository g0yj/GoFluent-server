package com.lms.api.common.code;

import java.util.Arrays;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum VisaEntryType {
  SINGLE("single"),
  DOUBLE("double"),
  MULTIPLE("multiple");

  String code;

  public static VisaEntryType of(String code) {
    return Arrays.stream(values())
        .filter(value -> value.getCode().equals(code))
        .findFirst()
        .orElse(null);
  }
}
