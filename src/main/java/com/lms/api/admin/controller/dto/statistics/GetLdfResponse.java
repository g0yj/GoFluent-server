package com.lms.api.admin.controller.dto.statistics;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetLdfResponse {

  long id;
  String lesson;
  String contentSp;
  String contentV;
  String contentSg;
  String contentC;
}