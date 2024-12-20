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

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MCreateUserLevelTestRequest {

  LocalDateTime testStartTime;
  LocalDateTime testEndTime;
  String interviewer;
  String lbt;
  String rbt;
  String obt;
  String testIp;

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
  String clarity; // Clarity(10:hard to understand ,20:average ,30:easy to understand)
  String intonation; //Intonation(10:very Korean ,20:a bit Korean,30:acceptable ,40:near native)
  String vocabulary;//Vocabulary(10:very limited ,20:limited ,30:average ,40:above average ,50:extensive)

  String verbsTense; //Verbs tense(10:Never ,20:Seldom ,30:Sometimes,40:Usually,50:Always)
  String agreement; //Agreement(10:Never ,20:Seldom ,30:Sometimes,40:Usually,50:Always)
  String prepositions; //Prepositions(10:Never ,20:Seldom ,30:Sometimes,40:Usually,50:Always)
  String articles;//Articles(10:Never ,20:Seldom ,30:Sometimes,40:Usually,50:Always)
  String plurals; //Plurals(10:Never ,20:Seldom ,30:Sometimes,40:Usually,50:Always)
  String others;//Others(10:Never ,20:Seldom ,30:Sometimes,40:Usually,50:Always)
  String strongPoint;//
  String weakPoint;
  String comprehension;//Comprehension How much does learner understand(10:almost nothing ,20:some parts ,30:most parts ,40:almost everything ,50:everything)
  String confidence;//Confidence(10:completely lacking ,20:lacking ,30:average ,40:above average,50:very confident)
  String comments;
  List<TestRecommendLevel> recommendedLevel;
  String recommendedLevelEtc;

}
