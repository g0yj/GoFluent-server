package com.lms.api.admin.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lms.api.admin.code.SearchSmsCode;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class CreateSendSmsRequest {

  String senderPhone;

  @NotEmpty
  String content;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
  LocalDateTime reservationDate;

  @NotNull
  List<Recipient> recipients;

  //추가
  SearchSmsCode.SearchType sendType; // S:SMS , L:LMS

  @Getter
  @Builder
  @FieldDefaults(level = AccessLevel.PRIVATE)
  @AllArgsConstructor
  @NoArgsConstructor
  public static class Recipient {
    @JsonProperty("name")
    String name;

    @NotEmpty
    @JsonProperty("phone")
    String phone;

    @JsonProperty("email")
    String email;
  }
}
