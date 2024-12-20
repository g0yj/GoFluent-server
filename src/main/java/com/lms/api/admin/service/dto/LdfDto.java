package com.lms.api.admin.service.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LdfDto {

  Reservation reservation;
  Ldf ldf; // Ldf 정보
  String email;
}
