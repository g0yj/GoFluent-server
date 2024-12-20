package com.lms.api.admin.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lms.api.common.code.MemberConsultationType;
import com.lms.api.common.code.YN;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateUserConsultationRequest {

  @NotNull
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
  LocalDateTime consultationDate;

  MemberConsultationType type;

  @NotEmpty
  String details;

  YN topFixedYn;

  YN fontBoldYn;

  String backgroundColor;
}
