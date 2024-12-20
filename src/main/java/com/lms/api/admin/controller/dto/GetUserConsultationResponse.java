package com.lms.api.admin.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lms.api.common.code.MemberConsultationType;
import com.lms.api.common.code.YN;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetUserConsultationResponse {

  long id;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
  LocalDateTime consultationDate;

  MemberConsultationType type;
  YN topFixedYn;
  YN fontBoldYn;
  String backgroundColor;
  String details;
  String createdBy;
}
