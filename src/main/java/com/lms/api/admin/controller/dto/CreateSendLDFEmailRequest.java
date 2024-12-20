package com.lms.api.admin.controller.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateSendLDFEmailRequest {

  @NotNull
  Long ldfId;
  @NotEmpty
  String title;
  @NotEmpty
  String email;
  @NotEmpty
  String name;
  @NotEmpty
  String lesson;
  @NotEmpty
  String lessonDate;
  @NotEmpty
  String teacher;
  @NotEmpty
  String contentSp;
  @NotEmpty
  String contentV;
  @NotEmpty
  String contentSg;
  @NotEmpty
  String contentC;


}
