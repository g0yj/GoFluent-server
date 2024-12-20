package com.lms.api.admin.controller.dto.statistics;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lms.api.admin.code.SearchSmsCode.SearchType;
import com.lms.api.common.controller.dto.PageResponseData;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListSmsSuccessResponse extends PageResponseData {

  long id;
  String senderName;
  String content;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  LocalDateTime sendDate;

  Long total; // 발송건수
  Long success; // 성공
  Long fail; // 실패
  Long waiting; // 대기

  SearchType sendType;
  String recipientName;
  String recipientPhone; // 수신자 번호

  String senderPhone; // 발신자번호

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  LocalDateTime reservationDate;

}
