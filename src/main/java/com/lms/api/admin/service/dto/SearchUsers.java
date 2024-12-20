package com.lms.api.admin.service.dto;

import com.lms.api.admin.code.SearchUsersCode.CourseStatus;
import com.lms.api.admin.code.SearchUsersCode.ExpireType;
import com.lms.api.admin.code.SearchUsersCode.RegisterType;
import com.lms.api.admin.code.SearchUsersCode.RemainingType;
import com.lms.api.admin.code.SearchUsersCode.UserStatus;
import com.lms.api.common.code.UserType;
import com.lms.api.common.service.dto.Search;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchUsers extends Search {

  LocalDate createDateFrom;
  LocalDate createDateTo;
  RegisterType registerType;
  UserStatus status;
  String teacherId;
  CourseStatus courseStatus;
  ExpireType expireType;
  RemainingType remainingType;

  UserType type;

}
