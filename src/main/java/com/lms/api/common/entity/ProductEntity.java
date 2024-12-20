package com.lms.api.common.entity;

import com.lms.api.common.code.LanguageCode;
import com.lms.api.common.code.LessonType;
import com.lms.api.common.code.YN;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "product")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductEntity extends BaseEntity{

  @Id
  @Column(updatable = false)
  String id;

  String type;

  @Enumerated(EnumType.STRING)
  LanguageCode language;

  @Enumerated(EnumType.STRING)
  LessonType lessonType;

  // 추가
  @Column(name = "curriculum_yn")
  @Enumerated(EnumType.STRING)
  YN curriculumYN;

  @Column(name = "short_course_yn")
  @Enumerated(EnumType.STRING)
  YN shortCourseYN;

  String name;
  int price;
  String quantityUnit;
  String options;
  int sort;

  @OneToMany(mappedBy = "productEntity", cascade = CascadeType.ALL, orphanRemoval = true)
  List<OrderProductEntity> orderProductEntities = new ArrayList<>();

  @OneToMany(mappedBy = "productEntity", cascade = CascadeType.ALL, orphanRemoval = true)
  List<ReservationEntity> reservationEntities = new ArrayList<>();
}
