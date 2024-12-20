package com.lms.api.admin.controller.dto;

import com.lms.api.admin.code.SearchUserCoursesCode.CourseStatus;
import com.lms.api.common.controller.dto.PageRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListUserCoursesRequest extends PageRequest {

  CourseStatus status;

  public ListUserCoursesRequest(Integer page, Integer limit, Integer pageSize, String order,
      String direction, String search, String keyword,
      CourseStatus status) {

    super(page, limit, pageSize, order, direction, search, keyword);
    this.status = status;
  }
}
