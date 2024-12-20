package com.lms.api.admin.service.dto.statistics;

import com.lms.api.common.code.UserType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListUser {

  String id;
  String loginId;
  String name;
  String nameEn;
  String lastNameEn;
  UserType type;
  String cellPhone;
}
