package com.lms.api.admin.controller.dto.statistics;

import com.lms.api.admin.code.SearchSmsCode.SearchType;
import com.lms.api.admin.service.dto.statistics.SmsTarget;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListSmsWaitingResponse {

  SmsTarget smsTarget;

  SearchType sendType;

  String senderPhone; // 발송 번호
  String senderName; // 발송자
  String content; // 문자 내용
  int count;

}
