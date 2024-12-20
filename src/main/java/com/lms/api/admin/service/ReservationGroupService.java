package com.lms.api.admin.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationGroupService {

  /*private final ReservationRepository reservationRepository;

  *//**
   * 단일 예약에 대해 group_id 갱신 로직 실행
   *//*
  @Async
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void updateGroupIdForReservation(ReservationEntity reservation) {
    updateGroupId(reservation);
  }

  *//**
   * 삭제된 예약과 관련된 예약 목록에 대해 group_id 갱신 로직 실행
   *//*
  @Async
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void updateGroupIdForRelatedReservations(ReservationEntity deletedReservation) {
    // 삭제된 예약과 관련된 예약들을 조회
    List<ReservationEntity> affectedReservations = reservationRepository.findByCourseIdAndUserIdAndTeacherIdAndDate(
        deletedReservation.getCourseEntity().getId(),
        deletedReservation.getUserEntity().getId(),
        deletedReservation.getTeacherEntity().getUserId(),
        deletedReservation.getDate());

    log.debug("## affectedReservations: {}", affectedReservations);

    // 관련된 예약들에 대해 group_id 갱신
    for (ReservationEntity affectedReservation : affectedReservations) {
      updateGroupId(affectedReservation);
    }
  }

  *//**
   * 예약의 group_id를 갱신하는 로직
   *//*
  private void updateGroupId(ReservationEntity reservation) {
    // 현재 예약과 같은 course_id, user_id, teacher_id, date를 가진 기존 예약들을 조회
    List<ReservationEntity> existingReservations = reservationRepository.findByCourseIdAndUserIdAndTeacherIdAndDate(
        reservation.getCourseEntity().getId(),
        reservation.getUserEntity().getId(),
        reservation.getTeacherEntity().getUserId(),
        reservation.getDate());

    log.debug("## existingReservations: {}", existingReservations);

    // 이전 예약과의 연속성을 확인하여 group_id를 설정
    Optional<ReservationEntity> previousReservation = existingReservations.stream()
        .filter(existing -> existing.getEndTime().equals(reservation.getStartTime()))
        .findFirst();

    if (previousReservation.isPresent()) {
      // 이전 예약이 있으면 같은 group_id를 설정
      reservation.setGroupId(previousReservation.get().getGroupId());
    } else {
      // 없으면 새로운 group_id를 할당
      reservation.setGroupId(reservationRepository.findMaxGroupId() + 1);
    }

    // 갱신된 group_id를 다시 저장
    reservationRepository.save(reservation);
  }*/
}