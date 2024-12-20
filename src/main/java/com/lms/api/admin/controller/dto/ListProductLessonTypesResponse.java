package com.lms.api.admin.controller.dto;

import com.lms.api.admin.controller.dto.common.Code;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListProductLessonTypesResponse {

  List<Code> lessonTypes;
}
