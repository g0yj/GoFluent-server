package com.lms.api.admin.code;

public interface SearchUsersCode {

  enum CourseStatus {
    ALL, // 전체
    ATTENDING, // 수강중
    NOT_ATTENDING, // 비수강중
    WAITING // 대기중
  }

  enum ExpireType {
    ALL, // 전체
    EXPIRED, // 만료됨
    NOT_EXPIRED // 만료안됨
  }

  enum RegisterType {
    ALL, // 전체
    REGISTERED, // 등록회원
    UNREGISTERED // 미등록회원
  }

  enum RemainingType {
    ALL, // 전체
    REMAINING, // 잔여있음
    NOT_REMAINING // 잔여없음
  }

  enum UserStatus {
    ALL, // 전체
    ACTIVE, // 활동
    INACTIVE // 비활동
  }
}
