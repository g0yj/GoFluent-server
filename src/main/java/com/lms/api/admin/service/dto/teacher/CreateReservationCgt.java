package com.lms.api.admin.service.dto.teacher;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateReservationCgt {

  List<Long> schedules;
  String teacherId;
  String userId;
  String createdBy;
}
