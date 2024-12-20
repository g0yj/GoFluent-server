package com.lms.api.mobile.service;

import com.lms.api.common.code.AttendanceStatus;
import com.lms.api.common.code.ScheduleType;
import com.lms.api.common.code.YN;
import com.lms.api.common.entity.CalendarEntity;
import com.lms.api.common.entity.CourseEntity;
import com.lms.api.common.entity.ReservationEntity;
import com.lms.api.common.entity.ScheduleEntity;
import com.lms.api.common.entity.TeacherEntity;
import com.lms.api.common.entity.UserEntity;
import com.lms.api.common.exception.LmsErrorCode;
import com.lms.api.common.exception.LmsException;
import com.lms.api.common.repository.CalendarRepository;
import com.lms.api.common.repository.CourseRepository;
import com.lms.api.common.repository.ReservationRepository;
import com.lms.api.common.repository.ScheduleRepository;
import com.lms.api.common.repository.TeacherRepository;
import com.lms.api.common.repository.UserRepository;
import com.lms.api.common.util.ObjectUtils;
import com.lms.api.mobile.controller.dto.DeleteReservationCgtRequest;
import com.lms.api.mobile.controller.dto.ListCgtRequest;
import com.lms.api.mobile.service.dto.Cgt;
import com.lms.api.mobile.service.dto.CreateReservationCgt;
import com.lms.api.mobile.service.dto.CreateReservationCgtResponse;
import com.lms.api.mobile.service.dto.CreateReservations;
import com.lms.api.mobile.service.dto.Reservation;
import com.lms.api.mobile.service.dto.Schedule;
import com.lms.api.mobile.service.dto.Teacher;
import com.lms.api.mobile.service.dto.UpdateCancelReservations;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ScheduleRepository scheduleRepository;
    private final ReservationServiceMapper reservationServiceMapper;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;
    private final CalendarRepository calendarRepository;

    @Transactional
    public List<Reservation> updateCancelReservations(UpdateCancelReservations updateCancelReservations, String userId) {
        List<ReservationEntity> reservationEntities = reservationRepository.findAllById(updateCancelReservations.getIds());

        // 수정(0917) > 공휴일 포함
        if (updateCancelReservations.getIds().size() != reservationEntities.size()
                || reservationEntities.stream().anyMatch(reservationEntity -> !canCancelReservation(reservationEntity.getDate()))) {
            throw new LmsException(LmsErrorCode.RESERVATION_NOT_CANCELABLE);
        }

/* 배식차장님

        if (updateCancelReservations.getIds().size() != reservationEntities.size()
            || reservationEntities.stream().anyMatch(reservationEntity -> !reservationEntity.isCancelable())) {

            throw new LmsException(LmsErrorCode.RESERVATION_NOT_CANCELABLE);
        }
*/

        return reservationEntities.stream()
            .map(reservationEntity -> {
                reservationEntity.cancel(updateCancelReservations.getCancelReason(), userId);

                // 스케줄 찾아서 예약수 원복
                scheduleRepository.findByTeacherEntityAndDateAndStartTime(reservationEntity.getTeacherEntity(), reservationEntity.getDate(), reservationEntity.getStartTime())
                    .filter(scheduleEntity -> scheduleEntity.getReservationCount() > 0)
                    .ifPresent(scheduleEntity -> scheduleEntity.setReservationCount(scheduleEntity.getReservationCount() - 1));

                return reservationServiceMapper.toReservation(reservationEntity);
            })
            .toList();
    }

    private boolean canCancelReservation(LocalDate reservationDate) {
        LocalDate now = LocalDate.now();
        //LocalDate now = LocalDate.of(2024, 9, 23);
        LocalDate cancelDeadline = reservationDate.minusDays(2);

        // 공휴일과 주말을 제외한 취소 가능 날짜 계산
        while (isHolidayOrWeekend(cancelDeadline)) {
            cancelDeadline = cancelDeadline.minusDays(1);
        }

        return now.isBefore(cancelDeadline) || now.isEqual(cancelDeadline);
    }

    private boolean isHolidayOrWeekend(LocalDate date) {
        CalendarEntity calendarEntity = calendarRepository.findById(date).orElse(null);
        if (calendarEntity == null) {
            return false; // 날짜가 캘린더에 없을 경우 false 반환
        }
        // 공휴일 또는 주말 여부
        return calendarEntity.isHoliday() || calendarEntity.isWeekend();
    }


    public List<LocalTime> listTimes(LocalDate date, String teacherId) {
        List<ScheduleEntity> scheduleEntities = ObjectUtils.isEmpty(teacherId) ?
            scheduleRepository.findAllByDateAndTypeAndTeacherEntity_ActiveTrue(date, ScheduleType.COURSE) :
            scheduleRepository.findAllByDateAndTypeAndTeacherEntity_UserIdAndTeacherEntity_ActiveTrue(date, ScheduleType.COURSE, teacherId);

        return scheduleEntities.stream()
            .filter(ScheduleEntity::isReservationAvailable)
            .map(ScheduleEntity::getStartTime)
            .filter(time -> time.getMinute() == 0 || time.getMinute() == 30)
            .distinct()
            .sorted(LocalTime::compareTo)
            .toList();
    }

    public List<Teacher> listTeachers(LocalDate date, LocalTime time) {
        List<ScheduleEntity> scheduleEntities = time == null ?
            scheduleRepository.findAllByDateAndTypeAndTeacherEntity_ActiveTrue(date, ScheduleType.COURSE) :
            scheduleRepository.findAllByDateAndTypeAndStartTimeAndTeacherEntity_ActiveTrue(date, ScheduleType.COURSE, time);

        return scheduleEntities.stream()
            .filter(ScheduleEntity::isReservationAvailable)
            .map(ScheduleEntity::getTeacherEntity)
            .distinct()
            .sorted(Comparator.comparing(TeacherEntity::getSort))
            .map(reservationServiceMapper::toTeacher)
            .toList();
    }

    public List<Schedule> listRemainSchedules(LocalDate date, LocalTime time) {
        List<ScheduleEntity> scheduleEntities = scheduleRepository.findAllByDateAndTypeAndStartTimeAndTeacherEntity_ActiveTrue(date, ScheduleType.COURSE, time.minusMinutes(30L));
        List<ScheduleEntity> nextScheduleEntities = scheduleRepository.findAllByDateAndTypeAndStartTimeAndTeacherEntity_ActiveTrue(date, ScheduleType.COURSE, time.plusMinutes(30L));

        scheduleEntities.addAll(nextScheduleEntities);

        return scheduleEntities.stream()
                .filter(ScheduleEntity::isReservationAvailable)
                .sorted(Comparator.comparing(ScheduleEntity::getStartTime)
                        .thenComparing((ScheduleEntity scheduleEntity) -> scheduleEntity.getTeacherEntity().getSort()))
                .map(reservationServiceMapper::toSchedule)
                .toList();
    }

    @Transactional
    public List<Schedule> createReservations(CreateReservations createReservations) {
        if (createReservations.getDate().equals(LocalDate.now())) {
            throw new LmsException(LmsErrorCode.RESERVATION_DATE_INVALID);
        }

        UserEntity userEntity = userRepository.findById(createReservations.getUserId())
            .filter(UserEntity::isActive)
            .orElseThrow(() -> new LmsException(LmsErrorCode.USER_NOT_FOUND));

        TeacherEntity teacherEntity = teacherRepository.findById(createReservations.getTeacherId())
            .filter(TeacherEntity::isActive)
            .orElseThrow(() -> new LmsException(LmsErrorCode.TEACHER_NOT_FOUND));

        CourseEntity courseEntity = courseRepository.findById(createReservations.getCourseId())
                .filter(entity -> entity.getUserEntity().getId().equals(createReservations.getUserId()))
                .orElseThrow(() -> new LmsException(LmsErrorCode.COURSE_NOT_FOUND));

        if (courseEntity.getRemainCount() < createReservations.getReservationScheduleSize()) {
            throw new LmsException(LmsErrorCode.LESSON_COUNT_NOT_ENOUGH);
        }

        List<ScheduleEntity> scheduleEntities = new ArrayList<>();
        ScheduleEntity scheduleEntity = scheduleRepository.findByTeacherEntityAndDateAndStartTime(teacherEntity, createReservations.getDate(), createReservations.getTime())
            .orElseThrow(() -> new LmsException(LmsErrorCode.SCHEDULE_NOT_FOUND));

        scheduleEntities.add(scheduleEntity);

        if (createReservations.getRemainScheduleId() != null) {
            ScheduleEntity remainScheduleEntity = scheduleRepository.findById(createReservations.getRemainScheduleId())
                .orElseThrow(() -> new LmsException(LmsErrorCode.SCHEDULE_NOT_FOUND));

            scheduleEntities.add(remainScheduleEntity);
        }

        scheduleEntities.forEach(entity -> entity.setReservationCount(entity.getReservationCount() + 1));

        if (scheduleEntities.stream().anyMatch(entity -> entity.getReservationCount() > 1)) {
            // 위 카운트 업데이트는 롤백됨
            throw new LmsException(LmsErrorCode.SCHEDULE_ALREADY_RESERVED);
        }

        // create reservations
        List<ReservationEntity> reservationEntities = scheduleEntities.stream()
            .map(entity -> {
                ReservationEntity reservationEntity = new ReservationEntity();
                reservationEntity.setDate(entity.getDate());
                reservationEntity.setStartTime(entity.getStartTime());
                reservationEntity.setEndTime(entity.getStartTime().plusMinutes(30L));
                reservationEntity.setAttendanceStatus(AttendanceStatus.R);
                reservationEntity.setCourseEntity(courseEntity);
                reservationEntity.setUserEntity(userEntity);
                reservationEntity.setTeacherEntity(entity.getTeacherEntity());
                reservationEntity.setProductEntity(courseEntity.getOrderProductEntity().getProductEntity());
                reservationEntity.setCreatedBy(createReservations.getUserId());

                return reservationEntity;
            })
            .toList();

        reservationRepository.saveAll(reservationEntities);

        // update course
        courseEntity.setAssignmentCount(courseEntity.getAssignmentCount() + reservationEntities.size());
        courseEntity.setIsReservation(YN.Y);

        return scheduleEntities.stream()
            .map(reservationServiceMapper::toSchedule)
            .toList();
    }

    public List<Reservation> listReservations(String userId, LocalDate startDate, LocalDate endDate) {
        List<ReservationEntity> reservationEntities = reservationRepository.findAllByUserEntity_IdAndDateBetweenAndAttendanceStatusAndCancelIsFalseOrderByDateAscStartTimeAsc(userId, startDate, endDate, AttendanceStatus.R);

        return reservationEntities.stream()
            .map(reservationServiceMapper::toReservation)
            .toList();
    }

    /** main 화면의 예약 목록 조회*/
    public List<Reservation> listMainReservation(String userId) {
        LocalDate today = LocalDate.now();
        LocalDate startDate = today.withDayOfMonth(1);
        LocalDate endDate = today.plusMonths(2);
        List<ReservationEntity> reservationEntities = reservationRepository.findAllByUserEntity_IdAndDateBetweenAndAttendanceStatusAndCancelIsFalseOrderByDateAscStartTimeAsc(userId, startDate, endDate, AttendanceStatus.R);

        return reservationEntities.stream()
                .map(reservationEntity -> {
                    Reservation reservation = reservationServiceMapper.toReservation(reservationEntity);
                    if (reservationEntity.getCourseEntity() == null) {
                        ScheduleType scheduleType = ScheduleType.valueOf("CGT");
                        Optional<ScheduleEntity> scheduleEntityOpt = scheduleRepository.findAllByDateAndStartTimeAndTypeAndTeacherEntity_UserId(
                                        reservationEntity.getDate(), reservationEntity.getStartTime(), scheduleType, reservationEntity.getTeacherEntity().getUserId()).stream()
                                .filter(scheduleEntity -> scheduleEntity.getDate().equals(reservationEntity.getDate()) &&
                                        scheduleEntity.getStartTime().equals(reservationEntity.getStartTime()))
                                .findFirst();
                        reservation.setSchedule(scheduleEntityOpt.map(ScheduleEntity::getId).orElse(null));
                    }
                    return reservation;
                })
                .toList();
    }


    /**CGT 목록 */
    public List<Cgt> listCgt(String userId, ListCgtRequest request) {
        LocalDate date = request.getDate();
        LocalDate startDate = date.withDayOfMonth(1);
        LocalDate endDate = date.withDayOfMonth(date.lengthOfMonth());

        // 주어진 기간 내에 있는 모든 CGT 타입의 스케줄을 조회
        List<ScheduleEntity> scheduleEntities = scheduleRepository.findAllByTypeAndDateBetween(ScheduleType.CGT, startDate, endDate);

        Map<List<Object>, List<ScheduleEntity>> groupedByKey = scheduleEntities.stream()
                .collect(Collectors.groupingBy(scheduleEntity -> Arrays.asList(
                        scheduleEntity.getCgtTime(),
                        scheduleEntity.getTeacherEntity().getUserId(),
                        scheduleEntity.getDate()
                )));

        // cgtTime으로 그룹핑
        return groupedByKey.entrySet().stream()
                .map(entry -> {
                    List<Object> key = entry.getKey();
                    LocalTime cgtTime = (LocalTime) key.get(0);
                    String teacherId = (String) key.get(1);
                    LocalDate lessonDate = (LocalDate) key.get(2);
                    List<ScheduleEntity> groupedSchedules = entry.getValue();

                    Cgt cgt = Cgt.builder()
                            .schedules(groupedSchedules.stream()
                                    .map(scheduleEntity -> {
                                        // Check for corresponding reservation entity
                                        Optional<ReservationEntity> reservationEntityOpt = reservationRepository.findByDateAndStartTimeAndUserEntity_Id(
                                                scheduleEntity.getDate(), scheduleEntity.getStartTime(), userId);
                                        long reservationId = reservationEntityOpt.map(ReservationEntity::getId).orElse(0L);

                                        return Cgt.Schedule.builder()
                                                .id(scheduleEntity.getId())
                                                .teacherId(scheduleEntity.getTeacherEntity().getUserId())
                                                .teacherName(scheduleEntity.getTeacherEntity().getUserEntity().getName())
                                                .date(scheduleEntity.getDate())
                                                .cgtTime(cgtTime)
                                                .startTime(scheduleEntity.getStartTime())
                                                .reservationId(reservationId)
                                                .reservationCount(scheduleEntity.getReservationCount())
                                                .reservationLimit(scheduleEntity.getReservationLimit())
                                                .build();
                                    })
                                    .sorted(Comparator.comparing(Cgt.Schedule::getDate)
                                            .thenComparing(Cgt.Schedule::getCgtTime))
                                    .collect(Collectors.toList()))
                            .build();
                    return cgt;
                })
                .sorted(Comparator.comparing((Cgt cgt) -> cgt.getSchedules().stream()
                                .findFirst()
                                .map(Cgt.Schedule::getDate)
                                .orElse(LocalDate.MAX))
                        .thenComparing(cgt -> cgt.getSchedules().stream()
                                .findFirst()
                                .map(Cgt.Schedule::getCgtTime)
                                .orElse(LocalTime.MAX)))
                .collect(Collectors.toList());
    }
    /**
     * 스케쥴 식별키를 받음. reservation에 저장
     * 조건 : 제한 인원이 넘으면 등록 불가 + 당일예약 불가도 로직 수정 필요
     * */
    @Transactional
    public CreateReservationCgtResponse createReservationCgt(CreateReservationCgt createCgt) {
        List<Long> scheduleIds = createCgt.getSchedules();

        CreateReservationCgtResponse response = null;

        for (Long scheduleId : scheduleIds) {
            ScheduleEntity scheduleEntity = scheduleRepository.findById(scheduleId)
                    .orElseThrow(() -> new LmsException(LmsErrorCode.SCHEDULE_NOT_FOUND));

            if (scheduleEntity.getReservationCount() >= scheduleEntity.getReservationLimit()) {
                throw new LmsException(LmsErrorCode.BAD_REQUEST, "예약 가능한 인원을 초과하였습니다.");
            }

            ReservationEntity reservation = new ReservationEntity();
            reservation.setAttendanceStatus(AttendanceStatus.R);
            reservation.setDate(scheduleEntity.getDate());
            reservation.setStartTime(scheduleEntity.getStartTime());
            reservation.setEndTime(scheduleEntity.getStartTime().plusMinutes(30));
            reservation.setUserEntity(userRepository.findById(createCgt.getUserId())
                    .orElseThrow(() -> new LmsException(LmsErrorCode.USER_NOT_FOUND)));
            reservation.setTeacherEntity(scheduleEntity.getTeacherEntity());
            reservation.setCreatedBy(createCgt.getCreatedBy());
            reservation.setCancel(false);
            reservationRepository.save(reservation);

            // 스케줄 인원증가
            scheduleEntity.setReservationCount(scheduleEntity.getReservationCount() + 1);
            scheduleRepository.save(scheduleEntity);

            response = CreateReservationCgtResponse.builder()
                    .teacherName(scheduleEntity.getTeacherEntity().getUserEntity().getName())
                    .date(scheduleEntity.getDate())
                    .time(scheduleEntity.getStartTime())
                    .message("예약에 성공했습니다")
                    .build();
        }
        return response;
    }

    /** 취소 관련 로직 추가해야함(이틀전 조건 포함)!!!!!!! */
    @Transactional
    public void deleteReservationCgt(String userId, DeleteReservationCgtRequest deleteReservationCgt) {
        List<Long> scheduleIds = deleteReservationCgt.getSchedules();
        List<ScheduleEntity> scheduleEntities = scheduleRepository.findAllById(scheduleIds);

        for (ScheduleEntity scheduleEntity : scheduleEntities) {
            scheduleEntity.setReservationCount(scheduleEntity.getReservationCount() - 1);
            scheduleRepository.save(scheduleEntity);

            // 예약 삭제 > 날짜와 시간, teacherId가 동일한 reservationId 찾아서 삭제
            List<Long> ids = reservationRepository.findIdsByDateAndStartTimeAndTeacherEntity_UserId(
                    scheduleEntity.getDate(), scheduleEntity.getStartTime(), scheduleEntity.getTeacherEntity().getUserId());
            for (Long id : ids) {
                Optional<ReservationEntity> reservationEntityOpt = reservationRepository.findById(id);
                ReservationEntity reservationEntity = reservationEntityOpt.orElseThrow(() -> new LmsException(LmsErrorCode.RESERVATION_NOT_FOUND));
                if (!canCancelReservation(reservationEntity.getDate())) {
                    throw new LmsException(LmsErrorCode.RESERVATION_NOT_CANCELABLE);
                }
                reservationRepository.deleteById(id);
            }
        }
    }
}

/** 취소 관련 로직 추가해야함(이틀전 조건)!!!!!!!
@Transactional
public void deleteReservationCgt(String userId, DeleteReservationCgtRequest deleteReservationCgt) {
    List<Long> scheduleIds = deleteReservationCgt.getSchedules();
    List<ScheduleEntity> scheduleEntities = scheduleRepository.findAllById(scheduleIds);

    for (ScheduleEntity scheduleEntity : scheduleEntities) {
        scheduleEntity.setReservationCount(scheduleEntity.getReservationCount() - 1);
        scheduleRepository.save(scheduleEntity);

        // 예약 삭제 > 날짜와 시간, teacherId가 동일한 reservationId 찾아서 삭제
        List<Long> ids = reservationRepository.findIdsByDateAndStartTimeAndTeacherEntity_UserId(
                scheduleEntity.getDate(), scheduleEntity.getStartTime(), scheduleEntity.getTeacherEntity().getUserId());

        for (Long id : ids) {
            reservationRepository.deleteById(id);
        }
    }
}
 */