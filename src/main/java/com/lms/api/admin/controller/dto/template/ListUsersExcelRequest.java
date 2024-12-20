package com.lms.api.admin.controller.dto.template;

import com.lms.api.admin.code.SearchUsersCode.CourseStatus;
import com.lms.api.admin.code.SearchUsersCode.ExpireType;
import com.lms.api.admin.code.SearchUsersCode.RegisterType;
import com.lms.api.admin.code.SearchUsersCode.RemainingType;
import com.lms.api.admin.code.SearchUsersCode.UserStatus;
import com.lms.api.common.code.UserType;
import com.lms.api.common.util.StringUtils;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListUsersExcelRequest  {

  LocalDate createDateFrom;
  LocalDate createDateTo;
  RegisterType registerType; // 등록구분
  UserStatus status; // 상태
  String teacherId;
  CourseStatus courseStatus; // 수강상태
  ExpireType expireType; // 기존에 없던 검색 조건 > 전체,만료,만료안됨
  RemainingType remainingType; // 기존에 없던 검색 조건 > 전체, 잔여있음, 잔여없음

  String keyword;
  String search;

  // 회원에 학생,관리자 구분을 위해 추가
  UserType type;

  public ListUsersExcelRequest(LocalDate createDateFrom, LocalDate createDateTo, RegisterType registerType, UserStatus status, String teacherId, CourseStatus courseStatus, ExpireType expireType, RemainingType remainingType, String keyword, String search, UserType type) {
    this.createDateFrom = createDateFrom;
    this.createDateTo = createDateTo;
    this.registerType = registerType;
    this.status = status;
    this.teacherId = teacherId;
    this.courseStatus = courseStatus;
    this.expireType = expireType;
    this.remainingType = remainingType;
    this.keyword = keyword;
    this.search = search;
    this.type = type;
  }

  public boolean hasSearch() {
    return StringUtils.hasAllText(search, keyword);
  }

}
