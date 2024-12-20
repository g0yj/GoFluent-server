package com.lms.api.admin.service.dto;

import com.lms.api.common.code.CourseHistoryModule;
import com.lms.api.common.code.CourseHistoryType;
import com.lms.api.common.service.dto.Search;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchCourseHistories extends Search {

  String userId;
  Long courseId;
  String moduleId;

  @NotNull
  CourseHistoryModule module;

  CourseHistoryType type;
}
