package com.lms.api.admin.controller.dto;

import com.lms.api.common.controller.dto.PageRequest;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListUserLdfsRequest extends PageRequest {

  LocalDate date;

  public ListUserLdfsRequest(Integer page, Integer limit, Integer pageSize, String order,
      String direction, String search, String keyword, LocalDate date) {
    super(page, limit, pageSize, order, direction, search, keyword);
    this.date = date;
  }
}
