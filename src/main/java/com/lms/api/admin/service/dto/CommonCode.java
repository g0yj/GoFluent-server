package com.lms.api.admin.service.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommonCode {

  String codeGroup;
  String codeGroupName;
  String code;
  String name;
  int sort;
  String useYn;
}
