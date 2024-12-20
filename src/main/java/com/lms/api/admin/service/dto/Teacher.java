package com.lms.api.admin.service.dto;

import com.lms.api.common.code.TeacherType;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = {"userId"})
public class Teacher {

  String userId;
  TeacherType type;
  boolean active;
  int sort;
  User user;
  List<Reservation> reservations;

  public String getName() {
    return user == null ? null : user.getName();
  }
}
