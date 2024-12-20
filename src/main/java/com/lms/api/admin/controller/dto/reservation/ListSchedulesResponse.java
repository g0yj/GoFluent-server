package com.lms.api.admin.controller.dto.reservation;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lms.api.common.code.AttendanceStatus;
import java.time.LocalTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListSchedulesResponse {

  List<Teacher> teachers;
  List<Schedule> schedules;

  @Getter
  @Builder
  @FieldDefaults(level = AccessLevel.PRIVATE)
  public static class Teacher {
    String id;
    String name;
  }

  @Getter
  @Builder
  @FieldDefaults(level = AccessLevel.PRIVATE)
  public static class Schedule {

    @JsonFormat(pattern = "HH:mm")
    LocalTime time;

    //(추가) 강사별 스케줄 여부를 확인하기 위한 정보
    List<Teacher> teachers;

    List<Reservation> reservations;
  }

  @Getter
  @Builder
  @FieldDefaults(level = AccessLevel.PRIVATE)
  public static class Reservation {

    Long reservationId;
    String teacherId;
    String teacherName;
    String teacherNameEn;
    String userId;
    String userName;
    String userNameEn;
    String email;
    String textbook;
    AttendanceStatus status;
    String statusLabel;
    Long courseId;

    @JsonProperty("isRetakeRequired")
    boolean retakeRequired; // 재등록 임박 여부

    @JsonProperty("isReported")
    boolean reported;
  }
}
