package com.lms.api.admin.controller.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListTeacherMemberResponse {
  String id; // 회원 식별키
  String name; // 학생명
  String textbook; // 교재

  String email;
  String startTime;
  String endTime;

  String teacherName;
}
