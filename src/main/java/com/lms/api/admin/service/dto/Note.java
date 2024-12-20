package com.lms.api.admin.service.dto;

import com.lms.api.common.code.CourseHistoryModule;
import com.lms.api.common.code.CourseHistoryType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;


@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Note {

  Long id;
  String userId;
  Long courseId;
  String moduleId;
  CourseHistoryModule module;
  CourseHistoryType type;
  String content;
  String modifiedOn;
  String modifierName;
  String createdOn;
  String creatorName;
  String modifiedBy;

}
