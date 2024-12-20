package com.lms.api.admin.controller.dto.consultation;

import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateConsultationHistoryRequest {

  @NotEmpty
  String details;
}
