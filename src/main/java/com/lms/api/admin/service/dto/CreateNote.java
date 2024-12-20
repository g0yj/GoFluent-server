package com.lms.api.admin.service.dto;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateNote {

  String userId;
  Long courseId;
  String content;
  LocalDateTime createdOn;
  String createdBy;
  String module;
}
