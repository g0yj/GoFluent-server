package com.lms.api.admin.controller.dto.template;

import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateTemplateRequest {

  @NotEmpty
  String text; // 템플릿

  @NotEmpty
  String description; // 설명

}
