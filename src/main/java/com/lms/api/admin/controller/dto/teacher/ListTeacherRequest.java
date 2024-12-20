package com.lms.api.admin.controller.dto.teacher;

import com.lms.api.common.code.TeacherType;
import com.lms.api.common.controller.dto.PageRequest;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListTeacherRequest extends PageRequest {

  LocalDate contractDateFrom;
  LocalDate contractDateTo;
  boolean active;
  TeacherType type; //

  public ListTeacherRequest(Integer page, Integer limit, Integer pageSize, String order,
      String direction, String search, String keyword, LocalDate contractDateFrom,
      LocalDate contractDateTo, boolean active, TeacherType type) {
    super(page, limit, pageSize, order, direction, search, keyword);
    this.contractDateFrom = contractDateFrom;
    this.contractDateTo = contractDateTo;
    this.active = active;
    this.type = type;
  }
}
