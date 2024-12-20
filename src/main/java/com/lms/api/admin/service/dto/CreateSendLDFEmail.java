package com.lms.api.admin.service.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateSendLDFEmail {
  Long ldfId;
  // 발신자 이름
  String senderName;
  // 제목
  String title;
  // 수신자 이름
  String name;
  // 수신자 이메일
  String email;
  // 강의 이름
  String lesson;
  // 강의 날짜
  String lessonDate;
  // 강사명
  String teacher;
  // Stress and Pronunciation에 들어갈 내용
  String contentSp;
  // Vocabulary에 들어갈 내용
  String contentV;
  // Grammar에 들어갈 내용
  String contentSg;
  // Culture에 들어갈 내용
  String contentC;
  // 생성자
  String createdBy;

}
