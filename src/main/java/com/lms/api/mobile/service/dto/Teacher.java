package com.lms.api.mobile.service.dto;

import com.lms.api.common.code.TeacherType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Teacher {
  String userId;
  TeacherType type;
  User user;

  public String getName() {
    return user == null ? null : user.getName();
  }
}
