package com.lms.api.admin.service.dto;

import com.lms.api.common.code.CourseStatus;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateCourse {

  long id;
  String userId;
  float lessonCount;
  String countChangeReason;
  LocalDate startDate;
  LocalDate endDate;
  String dateChangeReason;
  String teacherId;
  String assistantTeacherId;
  CourseStatus status;
  String modifiedBy;
}
