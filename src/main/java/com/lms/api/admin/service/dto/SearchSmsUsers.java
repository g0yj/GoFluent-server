package com.lms.api.admin.service.dto;

import com.lms.api.common.code.UserType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchSmsUsers {

  UserType type;
  String search;
  String keyword;
}
