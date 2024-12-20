package com.lms.api.admin.controller.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Getter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateSendSmsResponse {

  final String statusCode;

  final String requestId;

  @JsonCreator
  public CreateSendSmsResponse(
      @JsonProperty("statusCode") String statusCode,
      @JsonProperty("requestId") String requestId) {
    this.statusCode = statusCode;
    this.requestId = requestId;
  }
}