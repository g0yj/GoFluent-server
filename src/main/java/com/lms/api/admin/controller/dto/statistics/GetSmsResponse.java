package com.lms.api.admin.controller.dto.statistics;

import com.lms.api.admin.service.dto.statistics.SmsTarget;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetSmsResponse {

  long id;
  List<SmsTarget> target;
  LocalDateTime reservationDate;
  String content;
  LocalDateTime sendDate;
}
