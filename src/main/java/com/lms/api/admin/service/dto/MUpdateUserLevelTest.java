package com.lms.api.admin.service.dto;

import com.lms.api.admin.code.levelTest.TestComprehension;
import com.lms.api.admin.code.levelTest.TestConfidence;
import com.lms.api.admin.code.levelTest.TestConsonants;
import com.lms.api.admin.code.levelTest.TestGramer;
import com.lms.api.admin.code.levelTest.TestIntonation;
import com.lms.api.admin.code.levelTest.TestRecommendLevel;
import com.lms.api.admin.code.levelTest.TestStudyType;
import com.lms.api.admin.code.levelTest.TestVocabulary;
import com.lms.api.admin.code.levelTest.TestVowels;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MUpdateUserLevelTest {

  LocalDateTime testStartTime;
  LocalDateTime testEndTime;
  String interviewer;
  String lbt;
  String rbt;
  String obt;
  String testIp;
  Boolean isDeleteFile;
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
  TestIntonation intonation;
  TestVocabulary vocabulary;
  TestGramer verbsTense;
  TestGramer agreement;
  TestGramer prepositions;
  TestGramer articles;
  TestGramer plurals;
  TestGramer others;

  String strongPoint;
  String weakPoint;
  TestComprehension comprehension;
  TestConfidence confidence;
  String comments;

  List<TestRecommendLevel> recommendedLevel;
  String recommendedLevelEtc;


  String studyTypeList;
  String consonantsList;
  String vowelsList;
  String recommendedLevelList;

  Long id; // 레벨 테스트 식별키
  String userId; // 회원 식별키
  String modifiedBy;


}