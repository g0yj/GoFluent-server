package com.lms.api.admin.controller.dto.common;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CodeList {

  //코드식별키
  String code;
  //코드명
  String name;



}
