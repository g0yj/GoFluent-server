package com.lms.api.admin.controller.dto.teacher;

import com.lms.api.common.code.Gender;
import com.lms.api.common.code.Language;
import com.lms.api.common.code.TeacherType;
import com.lms.api.common.code.WorkTime;
import com.lms.api.common.code.WorkType;
import jakarta.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateTeacherRequest {

  @NotEmpty
  String name;

  String nameEn;

  String password;
  @NotEmpty
  String loginId;
  @NotEmpty
  String email;
  Gender gender;
  String cellPhone;

  TeacherType teacherType;
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
  LocalDate workStartDate;
  WorkTime workTime;
  WorkType workType;
  String partnerTeacherId;
  Language language;
  String memo;
  Integer sort; // 순번
  boolean active;

  List<MultipartFile> files;
  List<Long> deleteFiles;
}
