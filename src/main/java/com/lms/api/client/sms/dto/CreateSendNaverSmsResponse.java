package com.lms.api.client.sms.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class CreateSendNaverSmsResponse {

  public static final String SUCCESS_STATUS_CODE = "202";

  String requestId;
  String requestTime;
  String statusCode;
  StatusName statusName;

  public boolean isSuccess() {
    return statusCode.equals(SUCCESS_STATUS_CODE) && statusName == StatusName.success;
  }
}
