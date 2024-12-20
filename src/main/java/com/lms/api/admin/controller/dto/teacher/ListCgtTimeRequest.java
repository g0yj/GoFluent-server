package com.lms.api.admin.controller.dto.teacher;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListCgtTimeRequest {

  @NotNull
  @JsonFormat(pattern = "yyyy-MM-dd")
  LocalDate date;

  @NotNull
  String teacherId;

}
