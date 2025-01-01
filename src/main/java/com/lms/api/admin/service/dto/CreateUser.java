package com.lms.api.admin.service.dto;

import com.lms.api.common.code.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateUser {

  String name;
  String loginId;
  String email;

  @Default
  Boolean isReceiveEmail = false;

  String password;
  Gender gender;
  String cellPhone;

  @Default
  Boolean isReceiveSms = false;

  Boolean isOfficeWorker;

  String note;

  boolean active;

  String address;
  String detailedAddress;

  String coursePurpose;
  String createdBy;

  UserType type;

}
