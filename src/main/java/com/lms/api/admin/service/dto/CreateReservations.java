package com.lms.api.admin.service.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateReservations {

  String userId;
  long courseId;
  List<Long> scheduleIds;
  String createdBy;
}
