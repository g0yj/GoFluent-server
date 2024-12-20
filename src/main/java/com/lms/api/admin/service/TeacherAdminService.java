package com.lms.api.admin.service;

import com.lms.api.admin.controller.dto.teacher.DeleteReservationCgtRequest;
import com.lms.api.admin.controller.dto.teacher.ListReservationCgtRequest;
import com.lms.api.admin.controller.dto.teacher.ListWorkedTeachersRequest;
import com.lms.api.admin.controller.dto.teacher.UpdateSortRequest;
import com.lms.api.admin.service.dto.CreateSchedules;
import com.lms.api.admin.service.dto.Schedule;
import com.lms.api.admin.service.dto.Teacher;
import com.lms.api.admin.service.dto.TeacherFile;
import com.lms.api.admin.service.dto.teacher.CreateCgt;
import com.lms.api.admin.service.dto.teacher.CreateReservationCgt;
import com.lms.api.admin.service.dto.teacher.CreateTeacher;
import com.lms.api.admin.service.dto.teacher.DeleteCgt;
import com.lms.api.admin.service.dto.teacher.GetReservationCgt;
import com.lms.api.admin.service.dto.teacher.GetTeacher;
import com.lms.api.admin.service.dto.teacher.ListCgt;
import com.lms.api.admin.service.dto.teacher.ListWorkedTeachers;
import com.lms.api.admin.service.dto.teacher.SearchListCgt;
import com.lms.api.admin.service.dto.teacher.SearchListTeachers;
import com.lms.api.admin.service.dto.teacher.SearchTeacherResponse;
import com.lms.api.admin.service.dto.teacher.UpdateTeacher;
import com.lms.api.common.code.AttendanceStatus;
import com.lms.api.common.code.ScheduleType;
import com.lms.api.common.code.TeacherType;
import com.lms.api.common.code.UserType;
import com.lms.api.common.code.WorkTime;
import com.lms.api.common.entity.CalendarEntity;
import com.lms.api.common.entity.QScheduleEntity;
import com.lms.api.common.entity.QTeacherEntity;
import com.lms.api.common.entity.ReservationEntity;
import com.lms.api.common.entity.ScheduleEntity;
import com.lms.api.common.entity.TeacherEntity;
import com.lms.api.common.entity.TeacherFileEntity;
import com.lms.api.common.entity.UserEntity;
import com.lms.api.common.exception.LmsErrorCode;
import com.lms.api.common.exception.LmsException;
import com.lms.api.common.repository.CalendarRepository;
import com.lms.api.common.repository.ReservationRepository;
import com.lms.api.common.repository.ScheduleRepository;
import com.lms.api.common.repository.TeacherRepository;
import com.lms.api.common.repository.UserRepository;
import com.lms.api.common.service.FileService;
import com.lms.api.common.util.LmsUtils;
import com.lms.api.common.util.ObjectUtils;
import com.querydsl.core.types.dsl.BooleanExpression;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeacherAdminService {

  private final TeacherRepository teacherRepository;
  private final ScheduleRepository scheduleRepository;
  private final UserRepository userRepository;
  private final UserAdminServiceMapper userAdminServiceMapper;
  private final FileService fileService;
  private final TeacherAdminServiceMapper teacherAdminServiceMapper;
  private final ReservationRepository reservationRepository;
  private final ScheduleAdminServiceMapper scheduleAdminServiceMapper;
  private final CalendarRepository calendarRepository;

  public List<Teacher> listTeachers() {
    List<TeacherEntity> teacherEntities = teacherRepository.findAllByActiveTrueAndUserEntityIsNotNullOrderBySort();

    return teacherAdminServiceMapper.toTeachers(teacherEntities);
  }

  public List<Schedule> listSchedules(String teacherId, LocalDate startDate, LocalDate endDate) {
    return teacherRepository.findById(teacherId).map(teacherEntity -> {
      List<ScheduleEntity> scheduleEntities = scheduleRepository.findAllByTeacherEntityAndDateBetween(
          teacherEntity, startDate, endDate);
      return teacherAdminServiceMapper.toSchedules(scheduleEntities);
    }).orElseThrow(() -> new LmsException(LmsErrorCode.TEACHER_NOT_FOUND));
  }

  /**
   * 선택한 스케줄은 등록, 선택하지 않은 스케줄은 삭제
   **/
  @Transactional
  public void createSchedules(CreateSchedules createSchedules) {
    teacherRepository.findById(createSchedules.getTeacherId()).ifPresent(teacherEntity -> {
      List<ScheduleEntity> scheduleEntities = scheduleRepository.findAllByTeacherEntityAndDateBetween(
          teacherEntity, createSchedules.getStartDate(), createSchedules.getEndDate());

      List<ScheduleEntity> newScheduleEntities = createSchedules.getSchedules().stream()
          .filter(createSchedule -> scheduleEntities.stream().noneMatch(
              scheduleEntity -> scheduleEntity.getDate().equals(createSchedule.getDate()) &&
                                scheduleEntity.getStartTime()
                                    .equals(createSchedule.getStartTime())))
          .map(createSchedule -> {
            // 스케줄 생성
            ScheduleEntity scheduleEntity = new ScheduleEntity();
            scheduleEntity.setTeacherEntity(teacherEntity);
            scheduleEntity.setDate(createSchedule.getDate());
            scheduleEntity.setStartTime(createSchedule.getStartTime());
            scheduleEntity.setWorkTime(createSchedules.getWorkTime());
            scheduleEntity.setModifiedBy(createSchedules.getModifiedBy());

            return scheduleEntity;
          })
          .toList();

      List<ScheduleEntity> deleteScheduleEntities = scheduleEntities.stream()
          .filter(scheduleEntity -> {
            // 이미 예약된 스케줄이면 예외 발생
            if (scheduleEntity.getReservationCount() > 0) {
              throw new LmsException(LmsErrorCode.SCHEDULE_DELETION_NOT_ALLOWED,
                  scheduleEntity.getDate() + " " + scheduleEntity.getStartTime());
            }
            // 스케줄이 createSchedules에 없으면 삭제 대상
            return createSchedules.getSchedules().stream().noneMatch(
                createSchedule -> scheduleEntity.getDate().equals(createSchedule.getDate()) &&
                                  scheduleEntity.getStartTime()
                                      .equals(createSchedule.getStartTime())
            );
          })
          .toList();

      // 선택되지 않은 모든 스케줄 삭제
      if (ObjectUtils.isNotEmpty(deleteScheduleEntities)) {
        scheduleRepository.deleteAll(deleteScheduleEntities);
      }

      // 선택된 모든 스케줄 등록
      if (ObjectUtils.isNotEmpty(newScheduleEntities)) {
        scheduleRepository.saveAll(newScheduleEntities);
      }
    });
  }

  /**
   * 강사 목록
   */
  public Page<SearchTeacherResponse> toListTeachers(SearchListTeachers searchListTeachers) {
    QTeacherEntity qTeacherEntity = QTeacherEntity.teacherEntity;

    BooleanExpression where = qTeacherEntity.userEntity.loginId.isNotNull();

    // 계약기간 *****************************************************************************************
    if (searchListTeachers.getContractDateFrom() != null
        && searchListTeachers.getContractDateTo() != null) {
      where = where.and(
          qTeacherEntity.contractStartDate.goe(searchListTeachers.getContractDateFrom())
              .and(qTeacherEntity.contractEndDate.loe(searchListTeachers.getContractDateTo())));
    } else if (searchListTeachers.getContractDateFrom() != null) {
      where = where.and(
          qTeacherEntity.contractStartDate.goe(searchListTeachers.getContractDateFrom()));
    } else if (searchListTeachers.getContractDateTo() != null) {
      where = where.and(
          qTeacherEntity.contractStartDate.loe(searchListTeachers.getContractDateTo()));
    }

    // 활동
    if (searchListTeachers.isActive() == true) {
      where = where.and(qTeacherEntity.active.isTrue());
    } else if (searchListTeachers.isActive() == false) {
      where = where.and(qTeacherEntity.active.isFalse());
    }

    // 구분 [HT,LT]
    if (searchListTeachers.getType() != null && searchListTeachers.getType()
        .equals(TeacherType.HT)) {
      where = where.and(qTeacherEntity.type.eq(TeacherType.HT));
    } else if (searchListTeachers.getType() != null && searchListTeachers.getType()
        .equals(TeacherType.LT)) {
      where = where.and(qTeacherEntity.type.eq(TeacherType.LT));
    }
    // 검색
    if (searchListTeachers.hasSearch()) {
      switch (searchListTeachers.getSearch()) {
        case "ALL":
          where = where.and(
              qTeacherEntity.userEntity.name.contains(searchListTeachers.getKeyword())
                  .or(qTeacherEntity.userEntity.loginId.contains(searchListTeachers.getKeyword()))
                  .or(qTeacherEntity.userEntity.email.contains(searchListTeachers.getKeyword()))
          );
        case "name":
          where = where.and(
              qTeacherEntity.userEntity.name.contains(searchListTeachers.getKeyword()));
          break;
        case "loginId":
          where = where.and(
              qTeacherEntity.userEntity.loginId.contains(searchListTeachers.getKeyword()));
          break;
        case "email":
          where = where.and(
              qTeacherEntity.userEntity.email.contains(searchListTeachers.getKeyword()));
          break;
        default:
          break;
      }
    }
    String order = searchListTeachers.getOrder();
    Sort.Direction direction = Sort.Direction.valueOf(
        searchListTeachers.getDirection().toUpperCase());
    Sort sort;
    switch (order) {
      case "name":
        sort = Sort.by(direction, "userEntity.name");
        break;
      case "type":
        sort = Sort.by(direction, "type");
        break;
      case "email":
        sort = Sort.by(direction, "userEntity.email");
        break;
      case "workTime":
        sort = Sort.by(direction, "workTime");
        break;
      default:
        sort = Sort.by(Sort.Order.asc("sort"));
        break;
    }

    PageRequest pageRequest = PageRequest.of(searchListTeachers.getPage() - 1,
        searchListTeachers.getLimit(), sort);

    Page<SearchTeacherResponse> responsePage = teacherRepository.findAll(where, pageRequest)
        .map(teacherAdminServiceMapper::toSearchTeacherResponse);

    return responsePage;

  }

  /**
   * 강사 개별 출력
   */
  public GetTeacher getTeacher(String id) {
    return teacherRepository.findById(id).map(teacherEntity -> {
      if (teacherEntity.getWorkTime() == null) {
        teacherEntity.setWorkTime(WorkTime.ALL);
      }
      List<TeacherFile> teacherFiles = teacherEntity.getTeacherFileEntities().stream()
          .map(teacherFileEntity -> TeacherFile.builder()
              .id(teacherFileEntity.getId())
              .file(teacherFileEntity.getFile())
              .originalFile(teacherFileEntity.getOriginalFile())
              .url(fileService.getUrl(teacherFileEntity.getFile(),
                  teacherFileEntity.getOriginalFile()))
              .build())
          .toList();

      return teacherAdminServiceMapper.toGetTeacher(teacherEntity, teacherFiles);
    }).orElseThrow(() -> new LmsException(LmsErrorCode.TEACHER_NOT_FOUND));
  }

  /**
   * 등록
   */
  @Transactional
  public String createTeacher(CreateTeacher createTeacher) {
    if (userRepository.findByLoginId(createTeacher.getLoginId()).isPresent()) {
      throw new LmsException(LmsErrorCode.LOGIN_SERVER_ERROR);
    }

    if (createTeacher.getWorkTime() == WorkTime.ALL) {
      createTeacher.setWorkTime(null);
    }

    UserEntity userEntity = userAdminServiceMapper.toUserEntity(createTeacher,
        LmsUtils.createUserId(),
        LmsUtils.encryptPassword(createTeacher.getPassword()), UserType.T);

    TeacherEntity teacherEntity = new TeacherEntity();
    teacherEntity.setUserEntity(userEntity);
    teacherEntity.setType(createTeacher.getTeacherType());
    teacherEntity.setBasicSalary(createTeacher.getBasicSalary());
    teacherEntity.setHousingCost(createTeacher.getHousingCost());
    teacherEntity.setManagementCost(createTeacher.getManagementCost());
    teacherEntity.setNationalPension(createTeacher.getNationalPension());
    teacherEntity.setHealthInsurance(createTeacher.getHealthInsurance());
    teacherEntity.setCareInsurance(createTeacher.getCareInsurance());
    teacherEntity.setEmploymentInsurance(createTeacher.getEmploymentInsurance());
    teacherEntity.setSort(createTeacher.getSort());
    teacherEntity.setWorkStartDate(createTeacher.getWorkStartDate());
    teacherEntity.setWorkTime(createTeacher.getWorkTime());
    teacherEntity.setWorkType(createTeacher.getWorkType());
    teacherEntity.setPartnerTeacherId(createTeacher.getPartnerTeacherId());
    teacherEntity.setCellPhone(createTeacher.getCellPhone());
    //teacherEntity.setFile(createTeacher.getFile());
    teacherEntity.setActive(createTeacher.isActive());
    teacherEntity.setCreatedBy(createTeacher.getCreatedBy());

    Map<String, String> files = fileService.upload(createTeacher.getMultipartFiles());

    if (ObjectUtils.isNotEmpty(files)) {

      List<TeacherFileEntity> teacherFileEntities = files.entrySet().stream()
          .map(entry -> {
            TeacherFileEntity teacherFileEntity = new TeacherFileEntity();
            teacherFileEntity.setOriginalFile(entry.getKey());
            teacherFileEntity.setFile(entry.getValue());
            teacherFileEntity.setCreatedBy(createTeacher.getCreatedBy());
            teacherFileEntity.setTeacherEntity(teacherEntity);

            return teacherFileEntity;
          })
          .toList();

      teacherEntity.setTeacherFileEntities(teacherFileEntities);
    }

    teacherRepository.save(teacherEntity);

    return userEntity.getId();
  }

  /**
   * 삭제
   */
  @Transactional
  public void deleteTeacher(String id, String modifiedBy) {
    TeacherEntity teacherEntity = teacherRepository.findById(id)
        .orElseThrow(() -> new LmsException(LmsErrorCode.TEACHER_NOT_FOUND));
    teacherEntity.setActive(false);
    teacherEntity.setModifiedBy(modifiedBy);
    teacherRepository.save(teacherEntity);
  }

  /**
   * 수정
   */
  @Transactional
  public void updateTeacher(UpdateTeacher updateTeacher) {

    TeacherEntity teacherEntity = teacherRepository.findByUserId(updateTeacher.getUserId())
        .orElseThrow(() -> new LmsException(LmsErrorCode.TEACHER_NOT_FOUND));

    if (updateTeacher.getWorkTime() == WorkTime.ALL) {
      updateTeacher.setWorkTime(null);
    }

    if (ObjectUtils.isNotEmpty(updateTeacher.getDeleteFiles())) {
      updateTeacher.getDeleteFiles()
          .forEach(fileId -> teacherEntity.getTeacherFileEntities().stream()
              .filter(teacherFileEntity -> teacherFileEntity.getId().equals(fileId))
              .findFirst()
              .ifPresent(teacherFileEntity -> teacherEntity.getTeacherFileEntities()
                  .remove(teacherFileEntity)));
    }

    Map<String, String> files = fileService.upload(updateTeacher.getMultipartFiles());

    if (ObjectUtils.isNotEmpty(files)) {
      List<TeacherFileEntity> teacherFileEntities = files.entrySet().stream()
          .map(entry -> {
            TeacherFileEntity teacherFileEntity = new TeacherFileEntity();
            teacherFileEntity.setOriginalFile(entry.getKey());
            teacherFileEntity.setFile(entry.getValue());
            teacherFileEntity.setCreatedBy(updateTeacher.getModifiedBy());
            teacherFileEntity.setTeacherEntity(teacherEntity);
            return teacherFileEntity;
          })
          .toList();

      teacherEntity.getTeacherFileEntities().addAll(teacherFileEntities);
    }

    UserEntity userEntity = teacherEntity.getUserEntity();
    userEntity.setName(updateTeacher.getName());
    userEntity.setNameEn(updateTeacher.getNameEn());
    // password 는 nullable 이다.
    userEntity.setPassword(updateTeacher.getPassword() == null ? null
        : LmsUtils.encryptPassword(updateTeacher.getPassword()));
    userEntity.setLoginId(updateTeacher.getLoginId());
    userEntity.setEmail(updateTeacher.getEmail());
    userEntity.setGender(updateTeacher.getGender());

    userRepository.save(userEntity);

    teacherAdminServiceMapper.mapTeacherEntity(updateTeacher, teacherEntity);
  }


  @Transactional
  public void updateSortTeachers(UpdateSortRequest request) {
    TeacherEntity teacherEntity = teacherRepository.findById(request.getUserId())
        .orElseThrow(() -> new LmsException(LmsErrorCode.TEACHER_NOT_FOUND));
    teacherEntity.setSort(request.getSort());
    teacherRepository.save(teacherEntity);
  }

  //  @Transactional
//  public List<LocalTime> listCgtTimes(LocalDate date, String teacherId) {
//    List<ScheduleEntity> schedules = scheduleRepository.findByDateAndTeacherEntity_UserId(date,
//        teacherId);
//    schedules.sort(Comparator.comparing(ScheduleEntity::getStartTime));
//
//    List<LocalTime> availableTimes = new ArrayList<>();
//
//    for (int i = 0; i < schedules.size() - 1; i++) {
//      ScheduleEntity currentSchedule = schedules.get(i);
//      ScheduleEntity nextSchedule = schedules.get(i + 1);
//
//      // 현재 스케줄과 다음 스케줄의 시작 시간 차이 계산
//      Duration duration = Duration.between(currentSchedule.getStartTime(),
//          nextSchedule.getStartTime());
//
//      // 조건에 맞는지 확인
//      if (duration.equals(Duration.ofMinutes(30)) &&
//          currentSchedule.getType() == ScheduleType.COURSE &&
//          nextSchedule.getType() == ScheduleType.COURSE &&
//          currentSchedule.getReservationCount() == 0 &&
//          nextSchedule.getReservationCount() == 0) {
//
//        availableTimes.add(currentSchedule.getStartTime());
//      }
//    }
//
//    return availableTimes;
//  }
  @Transactional
  public List<LocalTime> listCgtTimes(LocalDate date, String teacherId) {
    List<ScheduleEntity> schedules = scheduleRepository.findByDateAndTeacherEntity_UserId(date,
        teacherId);
    schedules.sort(Comparator.comparing(ScheduleEntity::getStartTime));

    List<LocalTime> totalTimes = new ArrayList<>();
    List<LocalTime> availableTimes = new ArrayList<>();
    List<LocalTime> registeredCgtTimes = new ArrayList<>();

    // Collect all registered CGT times
    for (ScheduleEntity schedule : schedules) {
      if (schedule.getType() == ScheduleType.CGT && schedule.getCgtTime() != null) {
        registeredCgtTimes.add(schedule.getStartTime()); // CGT 시작 시간
        registeredCgtTimes.add(schedule.getStartTime().plusMinutes(30));   // CGT 종료 시간
      }
      totalTimes.add(schedule.getStartTime());
    }

    // Add times to availableTimes if they are valid
    for (LocalTime time : totalTimes) {
      if (isAvailableTimes(totalTimes, registeredCgtTimes, time, schedules)) {
        availableTimes.add(time);
      }
    }

    return availableTimes;
  }

  private boolean isAvailableTimes(List<LocalTime> totalTimes, List<LocalTime> registeredTimes,
      LocalTime time, List<ScheduleEntity> schedules) {

    // 이미 등록된 CGT 시간과 일치하면 제외
    if (registeredTimes.contains(time)) {
      return false;
    }

    // time이 등록된 CGT 시간의 범위와 겹치면 제외
    for (int i = 0; i < registeredTimes.size(); i += 2) {
      LocalTime cgtStart = registeredTimes.get(i);
      LocalTime cgtEnd = registeredTimes.get(i + 1);

      // time이 CGT 시간 범위에 속하는지 확인
      if (!time.isBefore(cgtStart) && !time.isAfter(cgtEnd)) {
        return false;
      }
    }

    // time이 마지막 수업 시간보다 늦고 다음 수업이 없으면 제외
    for (int i = 0; i < schedules.size(); i++) {
      ScheduleEntity currentSchedule = schedules.get(i);

      // 현재 수업 시간이 time과 같으면 다음 수업 체크
      if (currentSchedule.getStartTime().equals(time)) {
        if (i + 1 < schedules.size()) {
          ScheduleEntity nextSchedule = schedules.get(i + 1);

          // 다음 수업이 COURSE 타입인지 확인
          if (nextSchedule.getType() == ScheduleType.COURSE) {
            return true;
          }
        }
        return false; // 다음 수업이 없으면 제외
      }
    }

    return true; // 모든 조건을 통과하면 사용 가능
  }

//  @Transactional
//  public void createCgt(CreateCgt createCgt) {
//    TeacherEntity teacherEntity = teacherRepository.findById(createCgt.getTeacherId())
//        .orElseThrow(() -> new LmsException(LmsErrorCode.TEACHER_NOT_FOUND));
//
//    List<ScheduleEntity> schedules = scheduleRepository.findByDateAndTeacherEntity_UserId(
//        createCgt.getDate(), createCgt.getTeacherId());
//
//    LocalTime startTime = createCgt.getStartTime();
//    LocalTime endTime = startTime.plusHours(1);
//    // 한시간 단위로 CGT 수업 생성
//    for (ScheduleEntity schedule : schedules) {
//      if (!schedule.getStartTime().isBefore(startTime) && schedule.getStartTime()
//          .isBefore(endTime)) {
//        schedule.setType(ScheduleType.CGT);
//        schedule.setCgtTime(startTime);
//        schedule.setReservationLimit(createCgt.getReservationLimit());
//        scheduleRepository.save(schedule);
//      }
//    }
//  }

  @Transactional
  public void createCgt(CreateCgt createCgt) {

    TeacherEntity teacherEntity = teacherRepository.findById(createCgt.getTeacherId())
        .orElseThrow(() -> new LmsException(LmsErrorCode.TEACHER_NOT_FOUND));

    List<ScheduleEntity> schedules = scheduleRepository.findByDateAndTeacherEntity_UserId(
        createCgt.getDate(), createCgt.getTeacherId());

    for (ScheduleEntity schedule : schedules) {
      if (schedule.getStartTime().equals(createCgt.getStartTime())) {
        schedule.setCgtTime(schedule.getStartTime());
        schedule.setType(ScheduleType.CGT);
        schedule.setReservationLimit(createCgt.getReservationLimit());
      }
    }
  }

  /**
   * CGT 목록 조회 > 운영자가 예약자 취소와 등록을 해주기 위해 예약자 정보 전달 필요
   */
  @Transactional
  public Page<ListCgt> listCgts(SearchListCgt searchListCgt) {
    QScheduleEntity qScheduleEntity = QScheduleEntity.scheduleEntity;

    BooleanExpression where = qScheduleEntity.type.eq(ScheduleType.CGT);

    if (searchListCgt.getDate() != null) {
      where = where.and(qScheduleEntity.date.eq(searchListCgt.getDate()));
    }

    if (searchListCgt.getTeacherId() != null) {
      where = where.and(qScheduleEntity.teacherEntity.userId.eq(searchListCgt.getTeacherId()));
    }

    Page<ScheduleEntity> scheduleEntities = scheduleRepository.findAll(where,
        searchListCgt.toPageRequest());

    // ListCgt 목록 생성
    List<ListCgt> listCgts = new ArrayList<>();

    // 강사, 날짜 및 cgtTime 별로 그룹핑
    Map<TeacherEntity, Map<LocalDate, Map<LocalTime, List<ScheduleEntity>>>> groupedByTeacherDateAndCgtTime = scheduleEntities.getContent()
        .stream()
        .collect(Collectors.groupingBy(
            ScheduleEntity::getTeacherEntity,
            Collectors.groupingBy(
                ScheduleEntity::getDate,
                Collectors.groupingBy(ScheduleEntity::getCgtTime)
            )
        ));

    int idCounter = 1; // 고유 ID 생성용 카운터

    for (Map.Entry<TeacherEntity, Map<LocalDate, Map<LocalTime, List<ScheduleEntity>>>> teacherEntry : groupedByTeacherDateAndCgtTime.entrySet()) {
      TeacherEntity teacher = teacherEntry.getKey();
      Map<LocalDate, Map<LocalTime, List<ScheduleEntity>>> dateGroups = teacherEntry.getValue();

      for (Map.Entry<LocalDate, Map<LocalTime, List<ScheduleEntity>>> dateEntry : dateGroups.entrySet()) {
        LocalDate date = dateEntry.getKey();
        Map<LocalTime, List<ScheduleEntity>> cgtTimeGroups = dateEntry.getValue();

        for (Map.Entry<LocalTime, List<ScheduleEntity>> cgtTimeEntry : cgtTimeGroups.entrySet()) {
          LocalTime cgtTime = cgtTimeEntry.getKey();
          List<ScheduleEntity> schedules = cgtTimeEntry.getValue();

          // ListCgt 객체 생성
          ListCgt listCgt = new ListCgt();
          listCgt.setId(idCounter++); // 고유 ID 설정
          listCgt.setTeacherId(teacher.getUserId().toString());
          listCgt.setTeacherName(teacher.getUserEntity().getName());
          listCgt.setDate(date);
          listCgt.setStartTime(cgtTime);
          listCgt.setEndTime(cgtTime.plusHours(1));
          listCgt.setCgtTime(cgtTime);
          listCgt.setReservationLimit(schedules.get(0).getReservationLimit());
          listCgt.setReservationCount(schedules.get(0).getReservationCount());

          // schedules 목록 설정
          List<ListCgt.Schedule> scheduleList = schedules.stream().map(scheduleEntity -> {
            ListCgt.Schedule schedule = new ListCgt.Schedule();
            schedule.setId(scheduleEntity.getId());
            schedule.setDate(scheduleEntity.getDate());
            schedule.setStartTime(scheduleEntity.getStartTime());
            schedule.setCgtTime(scheduleEntity.getCgtTime());
            schedule.setType(scheduleEntity.getType());
            return schedule;
          }).collect(Collectors.toList());

          listCgt.setSchedules(scheduleList);

          if (!scheduleList.isEmpty()) {
            listCgt.setStartTime(scheduleList.get(0).getCgtTime());

            // endTime
            listCgt.setEndTime(scheduleList.get(0).getStartTime().plusHours(1));
          }

          listCgts.add(listCgt);
        }
      }
    }

    // 페이징 처리된 결과 반환
    return new PageImpl<>(listCgts, searchListCgt.toPageRequest(),
        scheduleEntities.getTotalElements());
  }

  @Transactional
  public void deleteCgt(DeleteCgt deleteCgt) {
    List<Long> scheduleIds = deleteCgt.getSchedules();
    List<ScheduleEntity> scheduleEntities = scheduleRepository.findAllById(scheduleIds);

    for (ScheduleEntity scheduleEntity : scheduleEntities) {
      scheduleEntity.setType(ScheduleType.COURSE);
      scheduleEntity.setCgtTime(null);
      scheduleEntity.setReservationLimit(1);
      scheduleEntity.setModifiedBy(deleteCgt.getModifiedBy());

      scheduleRepository.save(scheduleEntity);
    }
  }

  /**
   * CGT 예약 > 전달받은 값들을 사용해 ReservationEntity에 등록. 조건 : schedule의 limitCount를 넘기지 않을 것.
   */
  @Transactional
  public void createReservationCgt(CreateReservationCgt createReservationCgt) {
    List<Long> scheduleIds = createReservationCgt.getSchedules();

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
      reservation.setUserEntity(userRepository.findById(createReservationCgt.getUserId())
          .orElseThrow(() -> new LmsException(LmsErrorCode.USER_NOT_FOUND)));
      reservation.setTeacherEntity(scheduleEntity.getTeacherEntity());
      reservation.setCreatedBy(createReservationCgt.getCreatedBy());
      reservation.setCancel(false);
      reservationRepository.save(reservation);

      // 스케줄 인원증가
      scheduleEntity.setReservationCount(scheduleEntity.getReservationCount() + 1);
      scheduleRepository.save(scheduleEntity);
    }

  }

  /**
   * CGT 기예약자 선택 예약 취소 > scheduleEntity count 감소 + reservationEntity 삭제
   */
  @Transactional
  public void deleteReservationCgt(DeleteReservationCgtRequest deleteReservationCgt) {
    List<Long> scheduleIds = deleteReservationCgt.getSchedules();

    List<ScheduleEntity> scheduleEntities = scheduleRepository.findAllById(scheduleIds);

    for (ScheduleEntity scheduleEntity : scheduleEntities) {
      if (!canCancelReservation(scheduleEntity.getDate())) {
        throw new LmsException(LmsErrorCode.RESERVATION_NOT_CANCELABLE);
      }

      scheduleEntity.setReservationCount(scheduleEntity.getReservationCount() - 1);
      scheduleRepository.save(scheduleEntity);

      // 예약 삭제 > 날짜와 시간, teacherId가 동일한 reservationId 찾아서 삭제
      List<Long> ids = reservationRepository.findIdsByDateAndStartTimeAndTeacherEntity_UserIdAndUserEntity_Id(
          scheduleEntity.getDate(), scheduleEntity.getStartTime(),
          scheduleEntity.getTeacherEntity().getUserId(), deleteReservationCgt.getUserId());

      for (Long id : ids) {
        reservationRepository.deleteById(id);
      }
    }
  }

  private boolean canCancelReservation(LocalDate reservationDate) {
    LocalDate now = LocalDate.now();
    LocalDate cancelDeadline = reservationDate.minusDays(2);

    // 공휴일과 주말을 제외한 취소 가능 날짜 계산
    while (isHolidayOrWeekend(cancelDeadline)) {
      cancelDeadline = cancelDeadline.minusDays(1);
    }

    return now.isBefore(cancelDeadline) || now.isEqual(cancelDeadline);
  }

  private boolean isHolidayOrWeekend(LocalDate date) {
    // 주어진 날짜에 해당하는 CalendarEntity를 찾기
    CalendarEntity calendarEntity = calendarRepository.findById(date).orElse(null);
    if (calendarEntity == null) {
      return false; // 날짜가 캘린더에 없을 경우 false 반환
    }
    // 공휴일 또는 주말 여부
    return calendarEntity.isHoliday() || calendarEntity.isWeekend();
  }

/*  @Transactional
  public void deleteReservationCgt(DeleteReservationCgtRequest deleteReservationCgt) {
    List<Long> scheduleIds = deleteReservationCgt.getSchedules();
    List<ScheduleEntity> scheduleEntities = scheduleRepository.findAllById(scheduleIds);

    for (ScheduleEntity scheduleEntity : scheduleEntities) {
      scheduleEntity.setReservationCount(scheduleEntity.getReservationCount() - 1);
      scheduleRepository.save(scheduleEntity);

      // 예약 삭제 > 날짜와 시간, teacherId가 동일한 reservationId 찾아서 삭제
      List<Long> ids = reservationRepository.findIdsByDateAndStartTimeAndTeacherEntity_UserIdAndUserEntity_Id(
              scheduleEntity.getDate(), scheduleEntity.getStartTime(), scheduleEntity.getTeacherEntity().getUserId(), deleteReservationCgt.getUserId());

      for (Long id : ids) {
        reservationRepository.deleteById(id);
      }
    }
  }
  */


  /**
   * scheduleId에 해당하는 date와 cgtTime + teacherId로 예약자 찾기
   */

  @Transactional
  public List<GetReservationCgt> listReservationCgt(
      ListReservationCgtRequest listReservationCgtRequest) {
    LocalDate date = listReservationCgtRequest.getDate();
    LocalTime startTime = listReservationCgtRequest.getCgtTime();
    String teacherId = listReservationCgtRequest.getTeacherId();

    List<GetReservationCgt> getReservationCgts = new ArrayList<>();

    // 해당 날짜, 시간, 강사 ID에 해당하는 예약 조회
    List<ReservationEntity> reservationEntities = reservationRepository.findByDateAndStartTimeAndTeacherEntity_UserId(
        date, startTime, teacherId);

    for (ReservationEntity reservationEntity : reservationEntities) {
      // GetReservationCgt 객체 생성
      GetReservationCgt getReservationCgt = GetReservationCgt.builder()
          .user(teacherAdminServiceMapper.toUser(reservationEntity.getUserEntity()))
          .date(reservationEntity.getDate())
          .startTime(reservationEntity.getStartTime())
          .endTime(reservationEntity.getEndTime())
          .teacherId(teacherId)
          .schedules(listReservationCgtRequest.getSchedules())
          .build();

      getReservationCgts.add(getReservationCgt);
    }

    return getReservationCgts;
  }

  /**
   * 전달 받은 날짜에 해당하는 스케줄을 조회하고 날짜 + 강사별로 그룹핑
   */

  @Transactional
  public List<ListWorkedTeachers> listWorkedTeachers(ListWorkedTeachersRequest request) {
    List<Object[]> schedules = scheduleRepository.findTeacherIdsByDateBetweenGroupedByStartTime(
        request.getDateFrom(), request.getDateTo());
    return schedules.stream()
        .map(schedule -> {
          ListWorkedTeachers teacher = ListWorkedTeachers.builder()
              .teacherId(schedule[0].toString())
              .name(schedule[1].toString())
              .build();
          return teacher;
        })
        .collect(Collectors.toList());
  }
}



