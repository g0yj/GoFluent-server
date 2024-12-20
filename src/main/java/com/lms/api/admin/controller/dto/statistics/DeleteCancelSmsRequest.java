package com.lms.api.admin.controller.dto.statistics;

import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeleteCancelSmsRequest {
  List<Long> id; // sms 식별키
}
