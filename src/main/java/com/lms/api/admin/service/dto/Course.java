package com.lms.api.admin.service.dto;

import com.lms.api.common.code.CourseStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Course {

  long id;
  float lessonCount;
  float assignmentCount;
  float remainCount;
  LocalDate startDate;
  LocalDate endDate;
  Boolean isCompletion;
  Boolean isReservation;
  String countChangeReason;
  String dateChangeReason;
  LocalDateTime createdOn;
  CourseStatus status;

  User user;
  OrderProduct orderProduct;
  Teacher teacher;
  Teacher assistantTeacher;

  public String getName() {
    return orderProduct.getProduct().getName() + "/" + lessonCount + orderProduct.getProduct()
        .getQuantityUnit();
  }

  public boolean isRetakeRequired() {
    return remainCount <= 5 || endDate.isBefore(LocalDate.now().plusWeeks(2L));
  }
}
