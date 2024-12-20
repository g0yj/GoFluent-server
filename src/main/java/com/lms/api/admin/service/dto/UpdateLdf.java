package com.lms.api.admin.service.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateLdf {


  String lesson;
  String contentSp;
  String contentV;
  String contentSg;
  String contentC;

  String modifiedBy;

}
