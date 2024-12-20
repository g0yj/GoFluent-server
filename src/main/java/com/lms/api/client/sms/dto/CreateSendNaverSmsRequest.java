package com.lms.api.client.sms.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateSendNaverSmsRequest {

  @Default
  SmsType type = SmsType.SMS;

  ContentType contentType;
  String countryCode;
  // 발신번호
  String from;
  // 제목
  String subject;
  // 내용
  String content;
  String reserveTime;
  String reserveTimeZone;
  List<Message> messages;
  List<File> files;

  public String getFrom() {
    return from.replaceAll("-", "");
  }

  @Getter
  @Builder
  @FieldDefaults(level = AccessLevel.PRIVATE)
  @JsonInclude(JsonInclude.Include.NON_NULL)
  public static class Message {

    String to;
    String subject;
    String content;

    public String getTo() {
      return to.replaceAll("-", "");
    }
  }

  @Getter
  @Builder
  @FieldDefaults(level = AccessLevel.PRIVATE)
  @JsonInclude(JsonInclude.Include.NON_NULL)
  public static class File {

    String fileId;
  }

}