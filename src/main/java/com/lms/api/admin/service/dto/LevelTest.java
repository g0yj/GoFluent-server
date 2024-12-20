package com.lms.api.admin.service.dto;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LevelTest {

  Long id;

  LocalDateTime testStartTime;
  LocalDateTime testEndTime;

  String lbt;
  String rbt;
  String obt;
  String testIp;
  String file;
  String note;
  String purpose;
  String studyType;
  String studyTypeEtc;
  String familyBackground;
  String usage;
  String occupation;
  String spareTime;
  String travelAbroad;
  String futurePlans;
  String consonants;
  String vowels;
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
  String recommendedLevel;
  String recommendedLevelEtc;
  String interviewer;


  User user;
}
