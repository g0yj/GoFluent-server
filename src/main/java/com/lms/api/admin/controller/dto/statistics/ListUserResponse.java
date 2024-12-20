package com.lms.api.admin.controller.dto.statistics;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListUserResponse {

  String id;
  String loginId;
  String name;
  String type;
  String cellPhone;

}
