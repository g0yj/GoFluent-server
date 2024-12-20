package com.lms.api.admin.controller.dto.consultation;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListConsultationHistoryResponse {

  Long id;
  String details;
  //상담날짜
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
  LocalDateTime date;
  // 상담자
  String modifiedName;

}
