package com.lms.api.mobile.service.dto;

import com.lms.api.admin.code.SearchUserCoursesCode;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserInfo {
  String id;
  String loginId;
  String name;
  SearchUserCoursesCode.CourseStatus courseStatus; //(수강대기중, 수강완료, 수강중)

}
