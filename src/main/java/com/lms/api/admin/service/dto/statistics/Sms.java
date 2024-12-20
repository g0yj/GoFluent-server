package com.lms.api.admin.service.dto.statistics;

import com.lms.api.admin.code.SearchSmsCode.SearchType;
import com.lms.api.admin.service.dto.User;
import com.lms.api.common.code.SmsStatus;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Sms {

  long id;
  String senderPhone;
  String senderName;
  String content;
  LocalDateTime reservationDate;
  SearchType sendType;
  SmsStatus status;
  LocalDateTime sendDate;
  String requestId;

  User user;

  List<SmsTarget> smsTargets;
}
