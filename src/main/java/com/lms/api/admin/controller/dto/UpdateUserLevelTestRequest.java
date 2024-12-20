package com.lms.api.admin.controller.dto;

import com.lms.api.admin.code.levelTest.TestConsonants;
import com.lms.api.admin.code.levelTest.TestRecommendLevel;
import com.lms.api.admin.code.levelTest.TestStudyType;
import com.lms.api.admin.code.levelTest.TestVowels;
import java.time.LocalDateTime;
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
public class UpdateUserLevelTestRequest {

  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
  LocalDateTime testStartTime;
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
  LocalDateTime testEndTime;

  String interviewer;
  String lbt;
  String rbt;
  String obt;
  String testIp;

  MultipartFile file;

  // 선택값이므로 기본값 추가
  Boolean isDeleteFile = false;
  String note;
  String purpose;

  List<TestStudyType> studyType;

  String studyTypeEtc;
  String familyBackground;
  String usageType;
  String occupation;
  String spareTime;
  String travelAbroad;
  String futurePlans;

  List<TestConsonants> consonants;
  List<TestVowels> vowels;

  String clarity;
  String intonation;
  String vocabulary;

  String verbsTense;


  String agreement;
  String prepositions;
  String articles;
  String plurals;
  String others;

  String strongPoint;
  String weakPoint;
  String comprehension;
  String confidence;

  String comments;

  List<TestRecommendLevel> recommendedLevel;

  String recommendedLevelEtc;


}
