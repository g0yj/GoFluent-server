package com.lms.api.admin.controller.dto.consultation;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lms.api.admin.service.dto.consultation.SmsList;
import com.lms.api.common.code.CallTime;
import com.lms.api.common.code.ConsultationType;
import com.lms.api.common.controller.dto.PageResponseData;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListConsultationResponse extends PageResponseData {

  Long id;
  ConsultationType type; // 상담구분 ( P,V...)
  String status; //처리상태 WAITING // 대기: NO_CONTACT, UNVISITED,RESERVED,NOT_REGISTERED,REGISTERED,NO_SHOW, MISSED,
  String name; // 성명
  String cellPhone; // 전화번호
  String company; // 직장/학교
  String job;
  CallTime callTime; // 통화 가능 시간
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
  LocalDateTime visitDate; // 방문예약시간

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
  LocalDateTime createdOn; // 등록일시
  String creatorName; // 상담직원
  String details; // 상담내용

  List<SmsList> smsList;
}
