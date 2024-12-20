package com.lms.api.admin.service.dto.statistics;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SuccessSms {

  Sms sms;

  Long total; // 발송건수
  Long success; // 성공
  Long fail; // 실패
  Long waiting; // 대기


}
