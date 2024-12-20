package com.lms.api.admin.controller.dto.statistics;

import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeleteSmsRequest {
  List<Long> smsId; // sms 식별키
}
