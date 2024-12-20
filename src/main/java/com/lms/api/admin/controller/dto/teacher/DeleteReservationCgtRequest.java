package com.lms.api.admin.controller.dto.teacher;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeleteReservationCgtRequest {
   // 목록에서 가져올 것들
    @NotNull
    List<Long> schedules;
    @NotNull
    String userId;


}
