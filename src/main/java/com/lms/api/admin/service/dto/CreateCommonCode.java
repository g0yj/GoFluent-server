package com.lms.api.admin.service.dto;


import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateCommonCode {

  String codeGroup;
  String codeGroupName;

  @NotNull
  String code;
  @NotNull
  String name;
  @NotNull
  int sort;

  @NotNull
  String useYn;

  String createdBy;
}