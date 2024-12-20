package com.lms.api.admin.service.dto.statistics;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchSmsResponse {

  long id;


  String userId;
  String recipientPhone;
  String recipientName;
  String email;
  String status;
  LocalDateTime sendDate;

  SmsTarget smsTarget;


}
