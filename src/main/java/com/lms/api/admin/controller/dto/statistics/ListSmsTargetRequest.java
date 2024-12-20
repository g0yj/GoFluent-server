package com.lms.api.admin.controller.dto.statistics;

import com.lms.api.common.controller.dto.PageRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListSmsTargetRequest extends PageRequest {

  public ListSmsTargetRequest(Integer page, Integer limit, Integer pageSize, String order,
      String direction, String search, String keyword) {
    super(page, limit, pageSize, order, direction, search, keyword);
  }
}
