package com.lms.api.admin.code.levelTest.code;

public interface SearchUserCoursesCode {

  enum CourseStatus {
    VALID, // 유효한 수강 (환불 빼고 전체)
    ATTENDING, // 수강중
    WAITING, // 수강 대기중
    // 완료
    COMPLETE, // 수강 완료
    REFUND, // 환불
  }
}
