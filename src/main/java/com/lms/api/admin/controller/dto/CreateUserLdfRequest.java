package com.lms.api.admin.controller.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;



@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateUserLdfRequest {

  //기본으로 가져올 것
  long reservationId;   //예약정보

  String lesson;
  String contentSp;
  String contentV;
  String contentSg;
  String contentC;



}
