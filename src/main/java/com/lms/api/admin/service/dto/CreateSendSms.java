package com.lms.api.admin.service.dto;

import com.lms.api.admin.code.SearchSmsCode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateSendSms {
  // 발신자명
  @Setter
  String senderName;
  // 발신번호
  String senderPhone;
  // 내용
  String content;
  // 예약발송일시
  LocalDateTime reservationDate;
  @Setter
  String createdBy;
  // 발송대상목록
  List<Recipient> recipients;
  //SMS 발송 타입
  SearchSmsCode.SearchType sendType;

  public String getFormattedReservationDate() {
    return reservationDate == null ? null
        : reservationDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
  }

  @Getter
  @Builder
  @FieldDefaults(level = AccessLevel.PRIVATE)
  public static class Recipient {
    String email;
    String name;
    String phone;
  }
}
