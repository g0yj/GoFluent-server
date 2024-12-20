package com.lms.api.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "level_test")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LevelTestEntity extends BaseEntity {

  @Id
  @Column(updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  LocalDateTime testStartTime;
  LocalDateTime testEndTime;
  String lbt;
  String rbt;
  String obt;
  String testIp;
  String file;
  String originalFile;
  String note;
  String purpose;
  String studyType;
  String studyTypeEtc;
  String familyBackground;
  String usageType;
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

  //추가
  Long fileSize;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "userId", nullable = false)
  UserEntity userEntity;

}

