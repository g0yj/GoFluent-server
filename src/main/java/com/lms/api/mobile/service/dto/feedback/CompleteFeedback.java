package com.lms.api.mobile.service.dto.feedback;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lms.api.common.code.AttendanceStatus;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompleteFeedback {
  long id; // 예약식별키

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd EEE", locale = "en")
  LocalDate date;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "hh:mm a", locale = "en")
  LocalTime startTime;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "hh:mm a", locale = "en")
  LocalTime endTime;

  AttendanceStatus attendanceStatus;

  String teacherName;

  Long ldfId;
  String lesson;
  String contentSp;
  String contentV;
  String contentSg;
  String contentC;
  Float grade;
  String evaluation;
}
