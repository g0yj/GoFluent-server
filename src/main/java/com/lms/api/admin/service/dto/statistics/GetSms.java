package com.lms.api.admin.service.dto.statistics;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetSms {

  Long id;
  LocalDateTime sendDate;
  String content;
  List<SmsTarget> target;
  LocalDateTime reservationDate;

}
