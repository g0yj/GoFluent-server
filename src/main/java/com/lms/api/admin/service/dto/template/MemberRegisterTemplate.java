package com.lms.api.admin.service.dto.template;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MemberRegisterTemplate {

  Long id;
  String text; // 템플릿
  String description; // 설명
  String modifiedBy; //
  String modifiedName; // 작성자

}
