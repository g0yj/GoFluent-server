package com.lms.api.admin.controller.dto.statistics;

import com.lms.api.common.code.UserType;
import com.lms.api.common.controller.dto.PageRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListUserRequest extends PageRequest {

  UserType type; //    A("상담원"),  S("수강생"), T("강사")

  public ListUserRequest(Integer page, Integer limit, Integer pageSize, String order,
      String direction, String search, String keyword, UserType type) {
    super(page, limit, pageSize, order, direction, search, keyword);
    this.type = type;
  }
}
