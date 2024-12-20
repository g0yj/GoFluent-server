package com.lms.api.client.email.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateSendLDFEmailRequest {
  // 이메일 제목
  String from;
  // 발신 이메일
  String to;
  // 이름
  String name;
  // 이메일 제목
  String title;
  // 강의 이름
  String lesson;
  // 강의 시간
  String lessonDate;
  // 강사명
  String teacher;
  // Stress and Pronunciation에 들어갈 내용
  String contentSp;
  // Vocabulary에 들어갈 내용
  String contentV;
  // Sentence Structure & Grammar 에 들어갈 내용
  String contentSg;
  // Comment 에 들어갈 내용
  String contentC;
}

