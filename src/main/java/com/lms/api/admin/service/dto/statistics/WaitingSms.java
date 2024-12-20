package com.lms.api.admin.service.dto.statistics;

import com.lms.api.admin.code.SearchSmsCode.SearchType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WaitingSms {

  SmsTarget smsTarget;

  SearchType sendType;
  String senderPhone; // 발송 번호
  String senderName; // 발송자
  String content; // 문자 내용
  int count; // 발송건수

}
