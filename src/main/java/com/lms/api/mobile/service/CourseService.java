package com.lms.api.mobile.service;

import com.lms.api.common.code.AttendanceStatus;
import com.lms.api.common.entity.CourseEntity;
import com.lms.api.common.entity.ReservationEntity;
import com.lms.api.common.repository.CourseRepository;
import com.lms.api.mobile.service.dto.Course;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CourseService {
  private final CourseRepository courseRepository;
  private final CourseServiceMapper courseServiceMapper;

/*  public List<Course> listCourses(String userId) {
    List<CourseEntity> courseEntities = courseRepository.findAllByUserEntity_Id(userId);

    return courseEntities.stream().map(courseEntity -> courseServiceMapper.toCourse(
            courseEntity,
            courseEntity.getReservationEntities().stream()
                .filter(ReservationEntity::isReservation)
                .toList()))
        .collect(Collectors.toList());
  }*/

  public List<Course> listCourses(String userId) {
    List<CourseEntity> courseEntities = courseRepository.findAllByUserEntity_Id(userId);

    return courseEntities.stream().map(courseEntity -> {
      long totalAttendanceCount = courseEntity.getReservationEntities().stream()
              .filter(reservationEntity -> reservationEntity.getAttendanceStatus() == AttendanceStatus.Y)
              .count();
      float totalReservationCount = courseEntity.getReservationEntities().stream()
              .count();
      float totalNonAttendanceCount = courseEntity.getReservationEntities().stream()
              .filter(reservationEntity -> reservationEntity.getAttendanceStatus() == AttendanceStatus.N)
              .count();
      float totalAttendanceRate = totalReservationCount > 0 ? totalAttendanceCount / totalReservationCount : 0;

      return courseServiceMapper.toCourse(
              courseEntity,
              courseEntity.getReservationEntities().stream()
                      .filter(ReservationEntity::isReservation)
                      .toList(),
              totalAttendanceRate,
              totalAttendanceCount,
              totalReservationCount,
              totalNonAttendanceCount
      );
    }).collect(Collectors.toList());
  }
}
