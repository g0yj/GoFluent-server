package com.lms.api.client.email.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateSendEmailRequest {

  String from;
  String to;
  String subject;
  String content;
}
