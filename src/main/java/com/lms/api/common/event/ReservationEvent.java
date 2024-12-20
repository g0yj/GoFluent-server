package com.lms.api.common.event;

import com.lms.api.common.entity.ReservationEntity;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class ReservationEvent extends ApplicationEvent {
  private final ReservationEntity reservation;
  private final String action;

  public ReservationEvent(Object source, ReservationEntity reservation, String action) {
    super(source);
    this.reservation = reservation;
    this.action = action;
  }
}
