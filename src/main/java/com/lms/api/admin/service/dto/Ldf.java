package com.lms.api.admin.service.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Ldf {

  long id;
  String lesson;
  String contentSp;
  String contentV;
  String contentSg;
  String contentC;
  float grade;
  String evaluation;
  long emailId;


  User user;
  Reservation reservation;
}
