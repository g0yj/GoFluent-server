package com.lms.api.admin.controller.dto.statistics;

import com.lms.api.admin.code.SearchSmsCode.SearchType;
import com.lms.api.common.controller.dto.PageRequest;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListSmsRequest extends PageRequest {

  LocalDate sendDateFrom;
  LocalDate sendDateTo;
  SearchType sendType;

  public ListSmsRequest(Integer page, Integer limit, Integer pageSize, String order,
      String direction,
      String search, String keyword, LocalDate sendDateFrom, LocalDate sendDateTo,
      SearchType sendType) {
    super(page, limit, pageSize, order, direction, search, keyword);
    this.sendDateFrom = sendDateFrom;
    this.sendDateTo = sendDateTo;
    this.sendType = sendType;
  }
}
