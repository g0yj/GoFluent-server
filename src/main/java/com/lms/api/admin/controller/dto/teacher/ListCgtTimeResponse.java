package com.lms.api.admin.controller.dto.teacher;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListCgtTimeResponse  {
  //check
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
  LocalTime cgtTime;

}
