package com.lms.api.admin.service.dto;

import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {

  User user;

  LocalDate endDate; // 수업 종료일
  Float remainingCount; // 잔여
  Integer notBook; // 미부킹
  String name;
  String email;
}
