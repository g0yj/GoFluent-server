package com.lms.api.admin.service.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;


@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateUserLdf {

  //기본으로 가져올 것
  long reservationId;   // ldf 목록에서 ldf버튼 누를 시 식별키
  String userId;  // 회원id

  String lesson;
  String contentSp;
  String contentV;
  String contentSg;
  String contentC;

  String createdBy;
}
