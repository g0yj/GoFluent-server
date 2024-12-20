package com.lms.api.admin.service.dto.consultation;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateConsultationHistory {

  Long consultationId;
  String details;
  String createdBy;
  String modifiedBy;
  LocalDateTime date;
}
