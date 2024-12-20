package com.lms.api.admin.service.dto.teacher;

import com.lms.api.common.code.TeacherType;
import com.lms.api.common.code.WorkTime;
import com.lms.api.common.service.dto.Search;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchListTeachers extends Search {

  LocalDate contractDateFrom; //계약기간 검색 시작일
  LocalDate contractDateTo; // 검색 종료일
  boolean active;
  ;
  TeacherType type; // HT, LT
  WorkTime workTime;


}