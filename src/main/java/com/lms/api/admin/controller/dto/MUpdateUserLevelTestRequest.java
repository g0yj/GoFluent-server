package com.lms.api.admin.controller.dto;

import com.lms.api.admin.code.levelTest.TestClarity;
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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MUpdateUserLevelTestRequest {

  LocalDateTime testStartTime;
  LocalDateTime testEndTime;
  String interviewer;
  String lbt;
  String rbt;
  String obt;
  String testIp;

  String note;
  String purpose;

  List<TestStudyType> studyType; //필수

  String studyTypeEtc;
  String familyBackground;
  String usageType;
  String occupation;
  String spareTime;
  String travelAbroad;
  String futurePlans;

  List<TestConsonants> consonants;
  List<TestVowels> vowels;

  TestClarity clarity;
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


}
