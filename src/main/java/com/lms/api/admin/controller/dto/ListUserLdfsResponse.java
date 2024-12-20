package com.lms.api.admin.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lms.api.common.controller.dto.PageResponseData;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListUserLdfsResponse extends PageResponseData {

  Long id; //예약 식별키
  //수강시간
  @JsonFormat(pattern = "yyyy-MM-dd (EEE)", locale = "en-us")
  LocalDate date;
  @JsonFormat(pattern = "HH:mm")
  LocalTime startTime;
  @JsonFormat(pattern = "HH:mm")
  LocalTime endTime;

  String courseName;  // 과정
  String content; // 콘텐츠???
  String teacherName; // 강사
  String attendanceStatus;  //출결 :  (Y: 출석, N: 결석, R: 예약)
  Long ldfId; // ldf 실별키
  String email;// 이메일??? sent

}
