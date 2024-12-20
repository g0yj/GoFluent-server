package com.lms.api.common.entity;

import com.lms.api.common.code.YN;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Formula;

@Entity
@Table(name = "course")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CourseEntity extends BaseEntity {

  @Id
  @Column(updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  float lessonCount;
  float assignmentCount;
  float attendanceCount;
  LocalDate startDate;
  LocalDate endDate;

  @Enumerated(EnumType.STRING)
  YN isCompletion;

  @Enumerated(EnumType.STRING)
  YN isReservation;

  String countChangeReason;
  String dateChangeReason;

  @Formula("lesson_count - assignment_count")
  private float remainCount;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "userId", nullable = false)
  UserEntity userEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "orderProductId", nullable = false)
  OrderProductEntity orderProductEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "teacherId", nullable = false)
  TeacherEntity teacherEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "assistantTeacherId", nullable = false)
  TeacherEntity assistantTeacherEntity;

  @OneToMany(mappedBy = "courseEntity", cascade = CascadeType.ALL, orphanRemoval = true)
  List<CourseHistoryEntity> courseHistoryEntities = new ArrayList<>();

  @OneToMany(mappedBy = "courseEntity", cascade = CascadeType.ALL, orphanRemoval = true)
  List<ReservationEntity> reservationEntities = new ArrayList<>();

  public float getRemainCount() {
    return lessonCount - assignmentCount;
  }

  public float getReservationCount() {
    return assignmentCount - attendanceCount;
  }
}
