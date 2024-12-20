package com.lms.api.admin.controller.dto.teacher;

import com.lms.api.common.code.Gender;
import com.lms.api.common.code.Language;
import com.lms.api.common.code.TeacherType;
import com.lms.api.common.code.WorkTime;
import com.lms.api.common.code.WorkType;
import com.lms.api.common.code.YN;
import java.time.LocalDate;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class CreateTeacherRequest {

  String name;
  String nameEn;

  String password;
  String loginId;
  String email;
  Gender gender;
  String cellPhone;

  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
  LocalDate workStartDate;
  TeacherType teacherType;
  WorkTime workTime;
  WorkType workType;
  Language language;
  String partnerTeacherId;
  String memo;

  List<MultipartFile> files;

  boolean active ;

  //이메일.sms 수신 여부
  YN isReceiveEmail;
  YN isReceiveSms;

}
