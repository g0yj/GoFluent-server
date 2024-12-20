package com.lms.api.client.holiday.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ErrorResponse {

  CmmMsgHeader cmmMsgHeader;

  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
  @ToString
  @FieldDefaults(level = AccessLevel.PRIVATE)
  public static class CmmMsgHeader {
    String errMsg;
    String returnAuthMsg;
    String returnReasonCode;
  }
}
