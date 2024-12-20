package com.lms.api.admin.service.dto.teacher;

import com.lms.api.common.code.Language;
import com.lms.api.common.code.TeacherType;
import com.lms.api.common.code.WorkTime;
import com.lms.api.common.code.WorkType;
import java.util.Date;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetTeacherResponse {

  String id;

  TeacherType type; // 구분 :HT,LT

  String partnerTeacherId;

  Date workStartDate; // 근무시작일
  WorkTime workTime;//workTime: 근무시간
  Language language;//언어 :ENGLISH, CHINESE, JAPANESE
  WorkType workType; //근무유형:A,B

  String createdOn;

  boolean active;
  String cellPhone; // 전화번호
}
