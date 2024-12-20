package com.lms.api.admin.controller.dto.statistics;

import com.lms.api.admin.code.SearchSmsCode.SearchType;
import com.lms.api.common.controller.dto.PageRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListSmsWaitingRequest extends PageRequest {

  SearchType sendType;

  public ListSmsWaitingRequest(Integer page, Integer limit, Integer pageSize, String order,
      String direction, String search, String keyword, SearchType sendType) {
    super(page, limit, pageSize, order, direction, search, keyword);
    this.sendType = sendType;
  }
}
