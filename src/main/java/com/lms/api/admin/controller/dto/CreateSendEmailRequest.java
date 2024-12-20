package com.lms.api.admin.controller.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateSendEmailRequest {

  String senderEmail;

  @NotEmpty
  String title;

  @NotEmpty
  String content;

  @NotNull
  List<Recipient> recipients;

  long ldfId;

  @Getter
  @FieldDefaults(level = AccessLevel.PRIVATE)
  public static class Recipient {

    String name;

    @NotEmpty
    String email;
  }
}
