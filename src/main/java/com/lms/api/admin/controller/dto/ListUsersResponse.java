package com.lms.api.admin.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lms.api.common.controller.dto.PageResponseData;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListUsersResponse extends PageResponseData {

  String id;
  String name;

  @JsonFormat(pattern = "yyyy-MM-dd")
  LocalDate createDate;

  String cellPhone;
  String email;
  String company;

  @JsonFormat(pattern = "yyyy-MM-dd")
  LocalDate expirationDate;

  boolean active;

  // 추가한 부분!!
  Float remainingCount; // 잔여
  Integer notBook; // 미부킹
}
