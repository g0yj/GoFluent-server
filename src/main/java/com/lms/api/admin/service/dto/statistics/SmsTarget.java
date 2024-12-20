package com.lms.api.admin.service.dto.statistics;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lms.api.common.code.SmsStatus;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SmsTarget {

  Long id; // 대기내역 sms 식별키
  String messageId;
  String email;
  String recipientPhone;
  String recipientName;
  SmsStatus status;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  LocalDateTime sendDate;


}
