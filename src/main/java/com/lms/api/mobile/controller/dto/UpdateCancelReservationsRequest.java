package com.lms.api.mobile.controller.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateCancelReservationsRequest {
    List<Long> ids;
    String cancelReason;
}
