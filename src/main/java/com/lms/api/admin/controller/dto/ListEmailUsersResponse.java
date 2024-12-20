package com.lms.api.admin.controller.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListEmailUsersResponse {

  List<User> users;

  @Getter
  @Builder
  @FieldDefaults(level = AccessLevel.PRIVATE)
  public static class User {

    String id;
    String name;
    String nameEn;
    String loginId;
    String email;
  }
}
