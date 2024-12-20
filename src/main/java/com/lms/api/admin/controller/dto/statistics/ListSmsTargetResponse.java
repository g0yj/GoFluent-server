package com.lms.api.admin.controller.dto.statistics;

import com.lms.api.common.code.SmsStatus;
import com.lms.api.common.controller.dto.PageResponseData;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListSmsTargetResponse extends PageResponseData {

  long id;
  String email;
  String recipientPhone;
  String recipientName;
  SmsStatus status;
}
