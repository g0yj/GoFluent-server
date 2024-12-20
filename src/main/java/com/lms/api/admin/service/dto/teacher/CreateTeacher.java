package com.lms.api.admin.service.dto.teacher;

import com.lms.api.common.code.Gender;
import com.lms.api.common.code.TeacherType;
import com.lms.api.common.code.WorkTime;
import com.lms.api.common.code.WorkType;
import com.lms.api.common.code.YN;
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
public class CreateTeacher {


  @Builder.Default
  YN isReceiveEmail = YN.N;
  @Builder.Default
  YN isReceiveSms = YN.N;

  String loginId;
  // 회원 관련
  String name;
  String nameEn;
  String password;
  String email;
  Gender gender;
  String cellPhone;

  // 강사 필수 값
  @Builder.Default
  boolean active = true;

  @Builder.Default
  TeacherType teacherType = TeacherType.HT; // 한국인강사, 외국인강사

  @Builder.Default
  int basicSalary = 0;
  @Builder.Default
  int housingCost = 0;
  @Builder.Default
  int managementCost = 0;
  @Builder.Default
  int nationalPension = 0;
  @Builder.Default
  int healthInsurance = 0;
  @Builder.Default
  int careInsurance = 0;
  @Builder.Default
  int employmentInsurance = 0;
  @Builder.Default
  int sort = 0;

  // 강사 관련
  LocalDate workStartDate;
  WorkTime workTime;
  WorkType workType;
  String partnerTeacherId;
  String memo;
  List<MultipartFile> multipartFiles;
  String file;


  String createdBy;


}
