package com.lms.api.common.entity;

import com.lms.api.common.code.CourseHistoryModule;
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
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "course_history")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CourseHistoryEntity extends BaseEntity {

  @Id
  @Column(updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  String moduleId;

  @Enumerated(EnumType.STRING)
  CourseHistoryModule module;

  String type;
  String title;
  String content;

  @ManyToOne
  @JoinColumn(name = "courseId")
  CourseEntity courseEntity;

  public static CourseHistoryEntity addCourseHistoryEntity(CourseEntity courseEntity, CourseHistoryModule module, String type, String content, String createdBy) {
    CourseHistoryEntity courseHistoryEntity = new CourseHistoryEntity();
    courseHistoryEntity.setCourseEntity(courseEntity);
    courseHistoryEntity.setModule(module);
    courseHistoryEntity.setType(type);
    courseHistoryEntity.setContent(content);
    courseHistoryEntity.setCreatedBy(createdBy);
    return courseHistoryEntity;
  }
}
