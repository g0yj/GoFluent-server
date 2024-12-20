package com.lms.api.admin.service.dto.teacher;

import com.lms.api.common.code.Gender;
import com.lms.api.common.code.Language;
import com.lms.api.common.code.TeacherType;
import com.lms.api.common.code.WorkTime;
import com.lms.api.common.code.WorkType;
import java.time.LocalDate;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateTeacher {

  String loginId;
  // 회원 관련
  String name;
  String nameEn;

  @Builder.Default
  String lastNameEn = "";
  String password;
  String email;
  Gender gender;
  String cellPhone;

  // 강사 관련
  TeacherType teacherType; // 한국인강사, 외국인강사
  LocalDate workStartDate;
  WorkTime workTime;
  WorkType workType;
  Language language;
  String partnerTeacherId;
  String memo;
  boolean active;

  Integer sort;

  List<MultipartFile> multipartFiles;
  List<Long> deleteFiles;
  String file;
  String userId;

  String modifiedBy;

}
