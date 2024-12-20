package com.lms.api.mobile.service.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateReservationCgt {
    String userId; // 회원아이디
    List<Long> schedules;
    String createdBy;
}
