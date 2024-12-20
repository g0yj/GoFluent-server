package com.lms.api.admin.controller.dto.statistics;

import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListEvaluationResponse {

  String teacherName;
  String teacherId;
  long gradeCount; // 평가 인원수
  double gradeAvg; // 평점
  double total;
  LocalDate date;
}
