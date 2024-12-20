package com.lms.api.admin.service.dto;

import com.lms.api.common.service.dto.Search;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchUserLdfs extends Search {

  String userId;
  LocalDate date;

}
