package com.lms.api.admin.service.dto.statistics;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Evaluation {

  String teacherName;
  String teacherId;
  long gradeCount; // 평가 인원수
  @JsonFormat(shape = JsonFormat.Shape.NUMBER_FLOAT, pattern = "0.0")
  double gradeAvg; // 평점
  @JsonFormat(shape = JsonFormat.Shape.NUMBER_FLOAT, pattern = "0.0")
  double total;
  @JsonFormat(pattern = "yyyy-MM-dd")
  LocalDate date;

  public Evaluation(String teacherName, String teacherId, long gradeCount, double gradeAvg, double total , LocalDate date) {
    this.teacherName = teacherName;
    this.teacherId = teacherId;
    this.gradeCount = gradeCount;
    this.gradeAvg = gradeAvg;
    this.total = gradeAvg * gradeCount;
    this.date = date;
  }

}



