package com.lms.api.admin.service.dto.consultation;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConsultationHistory {

  Long id;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  LocalDateTime date;
  String details;

  Long consultationId;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  String modifiedOn;
  String modifiedBy;
  String createdBy;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  String createdOn;

  //상담자
  String modifiedName;
  String createdName;
}
