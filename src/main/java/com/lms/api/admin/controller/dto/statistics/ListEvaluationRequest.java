package com.lms.api.admin.controller.dto.statistics;

import com.lms.api.common.controller.dto.PageRequest;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListEvaluationRequest extends PageRequest {

  @DateTimeFormat(pattern = "yyyy-MM-mm")
  LocalDate date;// 수업일. 타입 명시


  public ListEvaluationRequest(Integer page, Integer limit, Integer pageSize, String order,
      String direction, String search, String keyword, LocalDate date) {
    super(page, limit, pageSize, order, direction, search, keyword);
    this.date = date;
  }
}
