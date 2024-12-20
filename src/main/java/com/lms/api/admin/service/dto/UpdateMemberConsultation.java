package com.lms.api.admin.service.dto;

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
public class UpdateMemberConsultation {

  long id;
  String userId;
  LocalDateTime consultationDate;
  MemberConsultationType type;
  String details;
  String modifiedBy;
  YN topFixedYn;
  YN fontBoldYn;
  String backgroundColor;
}
