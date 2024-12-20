package com.lms.api.common.event;

import com.lms.api.common.entity.ReservationEntity;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;

@Slf4j
public class ReservationEntityListener {

  private final ApplicationEventPublisher eventPublisher;

  public ReservationEntityListener(ApplicationEventPublisher eventPublisher) {
    this.eventPublisher = eventPublisher;
  }

  @PostPersist
  @PostUpdate
  public void onReservationSaveOrUpdate(ReservationEntity reservation) {
    log.debug("## onReservationSaveOrUpdate start");
    eventPublisher.publishEvent(new ReservationEvent(this, reservation, "UPDATE"));
  }

  @PostRemove
  public void onReservationDelete(ReservationEntity reservation) {
    log.debug("## onReservationDelete start");
    eventPublisher.publishEvent(new ReservationEvent(this, reservation, "DELETE"));
  }

}
