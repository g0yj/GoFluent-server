package com.lms.api.admin.service.dto.teacher;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;


@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListWorkedTeachers {
  String teacherId;
  String name;
}
