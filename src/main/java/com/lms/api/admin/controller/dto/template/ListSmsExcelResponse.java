package com.lms.api.admin.controller.dto.template;

import com.lms.api.admin.code.SearchSmsCode;
import com.lms.api.common.code.SmsStatus;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListSmsExcelResponse {

    Long id; // 고유값

    String senderPhone; // 발송자번호

    String createdBy; // 발송자(id)
    String senderName; //발송자(이름)
    String content;
    int total; // 총 발송 대상자 수

    // 발송일
    LocalDate date;
    LocalTime time;

    SearchSmsCode.SearchType sendType;
    SmsStatus status;

    String requestId;

}
