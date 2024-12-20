package com.lms.api.admin.service.dto;

import com.lms.api.common.code.CourseHistoryModule;
import com.lms.api.common.code.CourseHistoryType;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CourseHistory {

  long id;
  String moduleId;
  CourseHistoryModule module;
  CourseHistoryType type;
  String title;
  String content;
  LocalDateTime modifiedOn;
  String modifierName;
}
