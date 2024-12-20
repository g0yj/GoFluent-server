package com.lms.api.admin.service;

import com.lms.api.admin.service.dto.Schedule;
import com.lms.api.admin.service.dto.Teacher;
import com.lms.api.common.entity.ReservationEntity;
import com.lms.api.common.entity.ScheduleEntity;
import com.lms.api.common.repository.ReservationRepository;
import com.lms.api.common.repository.ScheduleRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ScheduleAdminService {

  private final ScheduleRepository scheduleRepository;
  private final ReservationRepository reservationRepository;
  private final ScheduleAdminServiceMapper scheduleAdminServiceMapper;

  /**
   * */
  public List<Schedule> listSchedules(LocalDate startDate, LocalDate endDate) {
    // 강사와 강사에 대한 스케줄 날짜, 시간 조회
    List<ScheduleEntity> scheduleEntities = scheduleRepository.findAllByDateBetween(startDate, endDate);
    // 스케줄이 없는 날짜나 시간도 클라이언트에게 보여줘야함. 예약이 있는지를 체크해서 전달
    List<ReservationEntity> reservationEntities = reservationRepository.findAllByDateBetweenAndCancelIsFalse(startDate, endDate);
    //강사ID에 따른 예약 정보들
    Map<String, List<ReservationEntity>> reservationEntitiesByTeacherId = reservationEntities.stream()
        .collect(Collectors.groupingBy(reservationEntity -> reservationEntity.getTeacherEntity().getUserId()));

    return scheduleEntities.stream().map(scheduleEntity -> {
      List<ReservationEntity> reservations = reservationEntitiesByTeacherId.get(scheduleEntity.getTeacherEntity().getUserId());
      Teacher teacher = scheduleAdminServiceMapper.toTeacher(scheduleEntity.getTeacherEntity(), reservations == null ? List.of() : reservations);

      return scheduleAdminServiceMapper.toSchedule(scheduleEntity, teacher);
    }).toList();
  }
}
