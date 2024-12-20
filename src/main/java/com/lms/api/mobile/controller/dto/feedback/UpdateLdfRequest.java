package com.lms.api.mobile.controller.dto.feedback;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateLdfRequest {

  long id; // (*)
  float grade;
  String evaluation;

}

