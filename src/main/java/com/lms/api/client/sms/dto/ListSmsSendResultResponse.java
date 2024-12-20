package com.lms.api.client.sms.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

/**
 * 메시지 발송 결과 조회 응답
 */
@Getter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListSmsSendResultResponse {

  String statusCode;
  String statusName;
  List<Message> messages;

  public boolean isSuccess() {
    return "200".equals(statusCode) && "success".equals(statusName);
  }

  public boolean isCompleted() {
    return messages.stream().allMatch(message -> message.status == SmsStatus.COMPLETED);
  }

  @Getter
  @ToString
  @FieldDefaults(level = AccessLevel.PRIVATE)
  public static class Message {

    String requestId;
    String requestTime;
    String contentType;
    String content;
    String countryCode;
    String from;
    String to;
    SmsStatus status;
    String statusCode;
    String statusMessage;
    String statusName;
    String completeTime;
    String telcoCode;
    List<File> files;

    public boolean isSuccess() {
//      return status == SmsStatus.COMPLETED && "0".equals(statusCode);
      return "0".equals(statusCode);
    }
  }

  @Getter
  @Setter
  @FieldDefaults(level = AccessLevel.PRIVATE)
  public static class File {

    String name;
    String fileId;
  }

  public enum SmsStatus {
    READY, PROCESSING, COMPLETED
  }
}
