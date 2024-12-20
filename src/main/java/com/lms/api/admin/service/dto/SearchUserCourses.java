package com.lms.api.admin.service.dto;

import com.lms.api.admin.code.SearchUserCoursesCode.CourseStatus;
import com.lms.api.common.service.dto.Search;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchUserCourses extends Search {

  String userId;
  CourseStatus status;
}
