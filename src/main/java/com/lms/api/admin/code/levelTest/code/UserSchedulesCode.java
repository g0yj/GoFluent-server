package com.lms.api.admin.code.levelTest.code;

public interface UserSchedulesCode {

  enum ReservationStatus {
    NONE, // 없음
    USERS, // 회원 예약
    AVAILABLE, // 예약 가능
    FULL, // 예약 불가
  }
}
