package com.lms.api.admin.service.dto.template;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateTemplate {

  String text; // 템플릿
  String description; // 설명

}
