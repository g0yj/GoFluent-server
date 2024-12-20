package com.lms.api.admin.service.dto;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateCommonCode {
  String code;
  String codeGroup;
  String name;
  int sort;
  String useYn;

  String modifiedBy;
}