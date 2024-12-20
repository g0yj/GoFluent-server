package com.lms.api.common.entity;

import com.lms.api.common.code.ScheduleType;
import com.lms.api.common.code.WorkTime;
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
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "schedule")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ScheduleEntity extends BaseEntity {

  @Id
  @Column(updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  LocalDate date;
  LocalTime startTime;
  LocalTime cgtTime;

  @Enumerated(EnumType.STRING)
  WorkTime workTime;

  @Enumerated(EnumType.STRING)
  ScheduleType type = ScheduleType.COURSE;

  int reservationLimit = 1;
  int reservationCount;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "teacherId", nullable = false)
  TeacherEntity teacherEntity;

  public boolean isReservationAvailable() {
    return reservationCount < reservationLimit;
  }
}
