package com.lms.api.admin.service.dto.statistics;

import com.lms.api.admin.service.dto.Ldf;
import com.lms.api.admin.service.dto.Reservation;
import com.lms.api.admin.service.dto.Teacher;
import com.lms.api.admin.service.dto.User;
import com.lms.api.common.service.dto.Search;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetLdf extends Search {

  Ldf ldf;
  Reservation reservation;
  Teacher teacher;
  User user;

  String teacherId;
}
