package com.lms.api.common.dto;

import com.lms.api.common.code.TeacherType;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Getter
@ToString
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Attendance {

  String teacherId;
  TeacherType type;
  String teacherName;
  LocalDate date;
  long reservationCount;
  long attendanceCount;


}
