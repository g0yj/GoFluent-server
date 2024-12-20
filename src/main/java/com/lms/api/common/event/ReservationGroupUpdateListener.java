package com.lms.api.common.event;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationGroupUpdateListener {

  /*private final ReservationGroupService reservationGroupService;

  @EventListener
  @Async
  public void handleReservationEvent(ReservationEvent event) {
    ReservationEntity reservation = event.getReservation();
    String action = event.getAction();

    if ("UPDATE".equals(action)) {
      reservationGroupService.updateGroupIdForReservation(reservation);
    } else if ("DELETE".equals(action)) {
      reservationGroupService.updateGroupIdForRelatedReservations(reservation);
    }
  }*/
}
