package com.lms.api.admin.controller.dto.teacher;

import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeleteCgtRequest {

  List<Long> schedules;

}

