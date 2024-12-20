package com.lms.api.admin.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lms.api.common.code.YN;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListUserConsultationsResponse {

  List<Consultation> consultations;

  @Getter
  @Builder
  @FieldDefaults(level = AccessLevel.PRIVATE)
  public static class Consultation {

    long id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    LocalDateTime consultationDate;

    String type;
    String details;
    String creatorName;

    YN topFixedYn;
    YN fontBoldYn;
    String backgroundColor;

  }
}
