package com.lms.api.admin.controller.dto.teacher;

import com.lms.api.common.controller.dto.PageRequest;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListCgtRequest extends PageRequest {

  LocalDate date;
  String teacherId;

  public ListCgtRequest(Integer page, Integer limit, Integer pageSize, String order,
      String direction,
      String search, String keyword, LocalDate date, String teacherId) {
    super(page, limit, pageSize, order, direction, search, keyword);
    this.date = date;
    this.teacherId = teacherId;
  }
}
