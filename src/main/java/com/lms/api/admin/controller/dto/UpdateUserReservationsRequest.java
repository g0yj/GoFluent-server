package com.lms.api.admin.controller.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateUserReservationsRequest {

  @NotEmpty
  List<Reservation> reservations;

  @Getter
  @FieldDefaults(level = AccessLevel.PRIVATE)
  public static class Reservation {

    @Positive
    long id;

    Boolean isCancel;
    String cancelReason;
  }
}
