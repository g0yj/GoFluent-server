package com.lms.api.admin.code.levelTest.code;

public interface SearchConsultationCode {

  enum SearchType {
    NAME, // 이름
    NUMBER, // 전화번호 뒷번호 4자리
    CONSULTATION // 상담내용
  }

}
