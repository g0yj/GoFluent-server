package com.lms.api.admin.controller.dto;

import com.lms.api.common.controller.dto.PageRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListTeacherMembersRequest extends PageRequest {

  boolean today; // 전체: false, 오늘예약: true
  String teacherId;

  public ListTeacherMembersRequest(Integer page, Integer limit, Integer pageSize, String order,
      String direction, String search, String keyword, boolean today, String teacherId) {
    super(page, limit, pageSize, order, direction, search, keyword);
    this.today = today;
    this.teacherId = teacherId;
  }
}
