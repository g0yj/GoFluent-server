package com.lms.api.admin.service.dto;

import com.lms.api.common.code.SmsStatus;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateSms {

  long id;
  SmsStatus status;
  List<UpdateSmsTarget> targets;

  @Getter
  @Builder
  @ToString
  @FieldDefaults(level = AccessLevel.PRIVATE)
  public static class UpdateSmsTarget {

    long id;
    String messageId;
    SmsStatus status;
  }
}
