package com.lms.api.admin.service.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateReservations {

  List<Reservation> reservations;
  String userId;
  String modifiedBy;

  @Getter
  @Builder
  @FieldDefaults(level = AccessLevel.PRIVATE)
  public static class Reservation {

    long id;
    Boolean isCancel;
    String cancelReason;
  }
}
