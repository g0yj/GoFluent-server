package com.lms.api.admin.service.dto.consultation;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lms.api.common.code.SmsStatus;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SmsList {
  String content; // sms엔티티에서 가져옴. 서비스 매퍼 필요
  String smsId;
  String recipientPhone ; // 받는 사람 번호
  String recipientName;// 받는 사람 이름
  String senderPhone; // 보낸 사람 번호
  String senderName; // 보낸 사람 이름
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  LocalDateTime sendDate; // 발송일
  SmsStatus status; // 발송상태( WAITING, SUCCESS, FAIL) SUCCESS 인것만 필요.


}
