package com.lms.api.admin.controller.dto;

import com.lms.api.common.code.CourseStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateUserCourseRequest {

  @PositiveOrZero
  float lessonCount;

  String countChangeReason;

  @NotNull
  LocalDate startDate;

  @NotNull
  LocalDate endDate;

  String dateChangeReason;

  @NotNull
  String teacherId;

  String assistantTeacherId;

  CourseStatus status;

}
