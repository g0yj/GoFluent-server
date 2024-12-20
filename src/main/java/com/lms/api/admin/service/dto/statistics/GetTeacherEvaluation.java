package com.lms.api.admin.service.dto.statistics;

import com.lms.api.admin.service.dto.Ldf;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetTeacherEvaluation {

  long gradeCount;
  double gradeAvg;
  double total;
  List<Ldf> ldfList;
}
