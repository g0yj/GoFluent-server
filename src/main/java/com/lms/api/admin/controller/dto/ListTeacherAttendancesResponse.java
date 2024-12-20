package com.lms.api.admin.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListTeacherAttendancesResponse {

  List<Schedule> schedules;
  List<Attendance> totalAttendances;

  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @FieldDefaults(level = AccessLevel.PRIVATE)
  public static class Schedule {

    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate date;

    List<Attendance> attendances;
  }

  @Getter
  @Setter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @FieldDefaults(level = AccessLevel.PRIVATE)
  public static class Attendance {

    @JsonIgnore
    String id;

    String name;

    @JsonIgnore
    String type;

    @JsonIgnore
    Integer sort;

    Long reservationCount;

    Long attendanceCount;

    BigDecimal attendanceRate;

    public String getAttendanceRate() {
      return reservationCount == 0 ? "0.00"
          : String.format("%.2f", attendanceRate);
    }

    @JsonIgnore
    public BigDecimal getAttendanceRateBigDecimal() {
      return attendanceRate;
    }

  }
}
