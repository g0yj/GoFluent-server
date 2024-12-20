package com.lms.api.admin.controller.dto;

import com.lms.api.common.code.UserType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListSmsUsersRequest {

  UserType type;
  String search;
  String keyword;
}
