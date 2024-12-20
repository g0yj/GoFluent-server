package com.lms.api.admin.service.dto.teacher;

import com.lms.api.admin.service.dto.Teacher;
import com.lms.api.admin.service.dto.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TeacherInfo {

  Teacher id;
  User userId;
  User partnerId;

  public TeacherInfo(Teacher id, User userId, User partnerId) {
    this.id = id;
    this.userId = userId;
    this.partnerId = partnerId;
  }
}
