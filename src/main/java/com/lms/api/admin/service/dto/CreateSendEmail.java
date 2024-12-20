package com.lms.api.admin.service.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateSendEmail {

  String senderName;
  String senderEmail;
  String title;
  String content;
  String createdBy;
  List<Recipient> recipients;

  //추가
  long ldfId;

  @Getter
  @Builder
  @FieldDefaults(level = AccessLevel.PRIVATE)
  public static class Recipient {

    String name;
    String email;
  }
}
