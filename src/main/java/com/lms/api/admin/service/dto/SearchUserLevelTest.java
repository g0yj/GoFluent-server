package com.lms.api.admin.service.dto;

import com.lms.api.common.service.dto.Search;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchUserLevelTest extends Search {

  String userId;
  LocalDateTime testStartTime;

}
