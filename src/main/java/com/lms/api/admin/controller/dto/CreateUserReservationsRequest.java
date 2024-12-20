package com.lms.api.admin.controller.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateUserReservationsRequest {

  // 예약할 과정 식별키
  @Positive
  long courseId;

  // 예약할 담임강사 또는 부담임강사 스케줄 식별키 목록
  @NotEmpty
  List<Long> scheduleIds;
}
