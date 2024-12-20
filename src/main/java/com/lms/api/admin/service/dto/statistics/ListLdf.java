package com.lms.api.admin.service.dto.statistics;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListLdf {

  @JsonFormat(pattern = "yyyy-MM-dd")
  LocalDate date;
  @JsonFormat(pattern = "HH:mm")
  LocalTime startTime;
  @JsonFormat(pattern = "HH:mm")
  LocalTime endTime;

  Float grade;
  String evaluation;

  String userId;
  String userName;

  Ldf ldf;

  @Getter
  @Builder
  @FieldDefaults(level = AccessLevel.PRIVATE)
  public static class Ldf {
    long id;
    String lesson;
    String contentSp;
    String contentV;
    String contentSg;
    String contentC;
  }
}
