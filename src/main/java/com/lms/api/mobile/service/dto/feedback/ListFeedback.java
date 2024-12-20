package com.lms.api.mobile.service.dto.feedback;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListFeedback {
  Reservation reservation;
  String teacherName;

}
