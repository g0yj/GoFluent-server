package com.lms.api.admin.controller.dto.statistics;

import com.lms.api.admin.service.dto.Ldf;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetEvaluationResponse {

  long gradeCount;
  double gradeAvg;
  double total;
  List<Ldf> ldfList;

}
