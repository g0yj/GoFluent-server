package com.lms.api.admin.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lms.api.common.controller.dto.PageResponseData;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListUserCourseHistoriesResponse extends PageResponseData {

  long id;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  LocalDateTime modifiedOn;

  String modifierName;
  String type;
  String content;
}
