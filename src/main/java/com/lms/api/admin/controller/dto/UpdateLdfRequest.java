package com.lms.api.admin.controller.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateLdfRequest {

  String lesson;
  String contentSp;
  String contentV;
  String contentSg;
  String contentC;

}
