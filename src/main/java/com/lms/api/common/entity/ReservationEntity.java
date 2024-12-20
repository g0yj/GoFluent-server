package com.lms.api.common.entity;

import com.lms.api.common.code.AttendanceStatus;
import com.lms.api.common.code.YN;
import com.lms.api.common.util.DateUtils;
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
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "reservation")
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReservationEntity extends BaseEntity {

  @Id
  @Column(updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  LocalDate date;
  LocalTime startTime;
  LocalTime endTime;

  @Enumerated(EnumType.STRING)
  AttendanceStatus attendanceStatus;

  String todayLesson;
  String report;
  String nextLesson;

  @Column(name = "is_cancel")
  boolean cancel;

  LocalDateTime cancelDate;
  String cancelReason;
  String cancelBy;

  @Transient
  String ldfYN;

  public ReservationEntity(LocalDate date, LocalTime startTime, LocalTime endTime,
      AttendanceStatus attendanceStatus, CourseEntity courseEntity, UserEntity userEntity,
      TeacherEntity teacherEntity) {
    this.date = date;
    this.startTime = startTime;
    this.endTime = endTime;
    this.attendanceStatus = attendanceStatus;
    this.courseEntity = courseEntity;
    this.userEntity = userEntity;
    this.teacherEntity = teacherEntity;
  }

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "courseId", nullable = false)
  @NotFound(action = NotFoundAction.IGNORE)
  CourseEntity courseEntity;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "userId", nullable = false)
  @NotFound(action = NotFoundAction.IGNORE)
  UserEntity userEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "teacherId", nullable = false)
  TeacherEntity teacherEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "productId", nullable = false)
  ProductEntity productEntity;

  public boolean isReservation() {
    return !cancel && attendanceStatus == AttendanceStatus.R;
  }

  // 영업일 기준 2일 전이 아니면 취소 불가
  public boolean isCancelable() {
    return !cancel && !DateUtils.getBusinessDate(2L).isAfter(date);
  }

  public void cancel(String cancelReason, String cancelBy) {
    this.cancel = true;
    this.cancelDate = LocalDateTime.now();
    this.cancelReason = cancelReason;
    this.cancelBy = cancelBy;

    //(수정) 예약 하나 당 레슨횟수 0.5 차감
    if (this.courseEntity.getAssignmentCount() > 0) {
      if (productEntity.getCurriculumYN() == YN.Y && productEntity.getShortCourseYN() == YN.Y) {
        this.courseEntity.setAssignmentCount(this.courseEntity.getAssignmentCount() - 1);
      } else {
        this.courseEntity.setAssignmentCount((float) (this.courseEntity.getAssignmentCount() - 0.5));
      }
    }

    if (this.courseEntity.getReservationEntities().stream().allMatch(ReservationEntity::isCancel)) {
      this.courseEntity.setIsReservation(YN.N);
    }
  }
}

