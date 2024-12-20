package com.lms.api.admin.code.levelTest.code;

public interface SearchReservationCode {

  enum ReportCondition {
    ALL,
    ATTENDANCE, //출석여부
    REPORT // report 여부
  }

  enum ReportSort {
    TIME, //시간
    TEACHER // 강사
  }
}
