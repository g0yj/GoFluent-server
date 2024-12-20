package com.lms.api.admin.service;


import com.lms.api.admin.controller.dto.consultation.GetValCellphoneRequest;
import com.lms.api.admin.service.dto.CreateUser;
import com.lms.api.admin.service.dto.consultation.ConsultationHistory;
import com.lms.api.admin.service.dto.consultation.ConsultationInfo;
import com.lms.api.admin.service.dto.consultation.CreateConsultation;
import com.lms.api.admin.service.dto.consultation.CreateConsultationHistory;
import com.lms.api.admin.service.dto.consultation.GetConsultation;
import com.lms.api.admin.service.dto.consultation.ListConsultation;
import com.lms.api.admin.service.dto.consultation.SearchConsultations;
import com.lms.api.admin.service.dto.consultation.SmsList;
import com.lms.api.admin.service.dto.consultation.UpdateConsultation;
import com.lms.api.common.code.CallTime;
import com.lms.api.common.code.ConsultationStatus;
import com.lms.api.common.code.JoinPath;
import com.lms.api.common.code.MemberConsultationType;
import com.lms.api.common.code.SmsStatus;
import com.lms.api.common.code.StudyPurpose;
import com.lms.api.common.code.UserType;
import com.lms.api.common.code.YN;
import com.lms.api.common.entity.ConsultationEntity;
import com.lms.api.common.entity.ConsultationHistoryEntity;
import com.lms.api.common.entity.MemberConsultationEntity;
import com.lms.api.common.entity.QConsultationEntity;
import com.lms.api.common.entity.SmsTargetEntity;
import com.lms.api.common.entity.UserEntity;
import com.lms.api.common.exception.LmsErrorCode;
import com.lms.api.common.exception.LmsException;
import com.lms.api.common.mapper.ControllerMapper;
import com.lms.api.common.mapper.ServiceMapper;
import com.lms.api.common.repository.ConsultationHistoryRepository;
import com.lms.api.common.repository.ConsultationRepository;
import com.lms.api.common.repository.MemberConsultationRepository;
import com.lms.api.common.repository.SmsTargetRepository;
import com.lms.api.common.repository.UserRepository;
import com.lms.api.common.service.FileService;
import com.lms.api.common.util.LmsUtils;
import com.lms.api.common.util.StringUtils;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ConsultationAdminService {

  private final ConsultationRepository consultationRepository;
  private final ConsultationAdminServiceMapper consultationAdminServiceMapper;
  private final UserRepository userRepository;
  private final ConsultationHistoryRepository consultationHistoryRepository;
  private final UserAdminServiceMapper userAdminServiceMapper;
  private final FileService fileService;
  private final ControllerMapper controllerMapper;
  private final SmsTargetRepository smsTargetRepository;
  private final ServiceMapper serviceMapper;
  private final UserAdminService userAdminService;
  private final MemberConsultationRepository memberConsultationRepository;

  public Page<ListConsultation> listConsultations(SearchConsultations searchConsultations) {
    QConsultationEntity qConsultationEntity = QConsultationEntity.consultationEntity;

    BooleanExpression where = Expressions.TRUE;
    // 상담 날짜 필터링
    if (searchConsultations.getCreateDateFrom() != null
        && searchConsultations.getCreateDateTo() != null) {
      LocalDateTime fromDateTime = searchConsultations.getCreateDateFrom().atStartOfDay();
      LocalDateTime toDateTime = searchConsultations.getCreateDateTo().atTime(23, 59, 59);
      where = where.and(qConsultationEntity.createdOn.between(fromDateTime, toDateTime));
    } else if (searchConsultations.getCreateDateFrom() == null
               && searchConsultations.getCreateDateTo() != null) {
      LocalDateTime fromDateTime = LocalDateTime.MIN;
      LocalDateTime toDateTime = searchConsultations.getCreateDateTo().atTime(23, 59, 59);
      where = where.and(qConsultationEntity.createdOn.between(fromDateTime, toDateTime));
    } else if (searchConsultations.getCreateDateFrom() != null
               && searchConsultations.getCreateDateTo() == null) {
      LocalDateTime fromDateTime = searchConsultations.getCreateDateFrom().atStartOfDay();
      LocalDateTime toDateTime = LocalDateTime.now();
      where = where.and(qConsultationEntity.createdOn.between(fromDateTime, toDateTime));
    }

    // 방문 날짜 필터링
    if (searchConsultations.getVisitDateFrom() != null
        && searchConsultations.getVisitDateTo() != null) {
      LocalDateTime visitFromDateTime = searchConsultations.getVisitDateFrom().atStartOfDay();
      LocalDateTime visitToDateTime = searchConsultations.getVisitDateTo().atTime(23, 59, 59);
      where = where.and(qConsultationEntity.visitDate.between(visitFromDateTime, visitToDateTime));
    } else if (searchConsultations.getVisitDateFrom() == null
               && searchConsultations.getVisitDateTo() != null) {
      LocalDateTime visitFromDateTime = LocalDateTime.MIN;
      LocalDateTime visitToDateTime = searchConsultations.getVisitDateTo().atTime(23, 59, 59);
      where = where.and(qConsultationEntity.visitDate.between(visitFromDateTime, visitToDateTime));
    } else if (searchConsultations.getVisitDateFrom() != null
               && searchConsultations.getVisitDateTo() == null) {
      LocalDateTime visitFromDateTime = searchConsultations.getVisitDateFrom().atStartOfDay();
      LocalDateTime visitToDateTime = LocalDateTime.now();
      where = where.and(qConsultationEntity.visitDate.between(visitFromDateTime, visitToDateTime));
    }
    // 상담 유형 필터링
    if (searchConsultations.getType() != null && !searchConsultations.getType().isEmpty()) {
      where = where.and(qConsultationEntity.type.eq(searchConsultations.getType()));
    }

    // 상담 상태 필터링
    if (searchConsultations.getStatus() != null && !searchConsultations.getStatus().isEmpty()) {
      where = where.and(qConsultationEntity.status.eq(searchConsultations.getStatus()));
    }

    // 검색어에 따른 필터링
    if (searchConsultations.hasSearch()) {
      switch (searchConsultations.getSearch()) {
        case "name":
          where = where.and(qConsultationEntity.name.contains(searchConsultations.getKeyword()));
          break;
        case "phone":
          String keyword = searchConsultations.getKeyword();
          if (keyword.length() == 4) {
            where = where.and(qConsultationEntity.cellPhone.substring(
                qConsultationEntity.cellPhone.length().subtract(4),
                qConsultationEntity.cellPhone.length()).eq(keyword));
          } else {
            where = where.and(qConsultationEntity.cellPhone.contains(keyword));
          }
          break;
        case "details":
          where = where.and(qConsultationEntity.details.contains(searchConsultations.getKeyword()));
          break;
        default:
          break;
      }
    }

    // 데이터베이스에서 조건에 맞는 ConsultationEntity 리스트 조회
    Page<ConsultationEntity> consultationEntities = consultationRepository.findAll(where,
        searchConsultations.toPageRequest());

    // 등록한 사람의 정보 가져오기
    List<UserEntity> creator = userRepository.findAllByIdIn(
        consultationEntities.map(ConsultationEntity::getCreatedBy).stream().distinct().toList()
    );

    // SMS 정보 가져오기
    List<SmsTargetEntity> smsTargetEntities = smsTargetRepository.findByRecipientPhoneIn(
        consultationEntities.stream().map(ConsultationEntity::getCellPhone).distinct()
            .collect(Collectors.toList())
    );

    return consultationEntities.map(consultationEntity -> {
      // 상태를 변환
      CallTime callTime = serviceMapper.toCallTime(consultationEntity.getCallTime());
      // Creator의 이름 찾기
      String creatorName = creator.stream()
          .filter(userEntity -> userEntity.getId().equals(consultationEntity.getCreatedBy()))
          .findFirst().map(UserEntity::getName).orElse(null);

      // SMS 정보 찾기
      List<SmsTargetEntity> matchedSmsTargets = smsTargetEntities.stream()
          .filter(smsTargetEntity ->
              smsTargetEntity.getRecipientPhone().equals(consultationEntity.getCellPhone())
          )
          .collect(Collectors.toList());

      // 매핑된 SMS 리스트 만들기
      List<SmsList> smsList = consultationAdminServiceMapper.toSmsList(matchedSmsTargets).stream()
          .filter(sms -> SmsStatus.SUCCESS.equals(sms.getStatus()))
          .sorted((s1, s2) -> s2.getSendDate().compareTo(s1.getSendDate()))
          .collect(Collectors.toList());

      // 최종적으로 매핑된 ListConsultation 객체 반환
      return consultationAdminServiceMapper.toListConsultation(consultationEntity, creatorName,
          smsList, callTime);
    });
  }

  /**
   * 상담 회원 등록
   */
  @Transactional
  public String createUser(long id, CreateUser createUser) {
    return consultationRepository.findById(id).map(consultationEntity -> {
      if (StringUtils.hasNotText(consultationEntity.getEmail())) {
        throw new LmsException(LmsErrorCode.CONSULTATION_EMAIL_NOT_FOUND);
      }

      if (consultationEntity.getIsMember() == YN.Y) {
        throw new LmsException(LmsErrorCode.CONSULTATION_ALREADY_MEMBER);
      }

      if (ConsultationStatus.of(consultationEntity.getStatus()) == ConsultationStatus.REGISTERED) {
        throw new LmsException(LmsErrorCode.CONSULTATION_ALREADY_MEMBER);
      }
      // 이메일이 신규 아이디와 동일할 수 없음. 동일한 경우 에러처리
      if (userRepository.existsByLoginId(consultationEntity.getEmail())) {
        throw new LmsException(LmsErrorCode.CONSULTATION_EMAIL_ALREADY_EXISTS);
      }
      if (consultationEntity.getConsultationDate() == null) {
        consultationEntity.setConsultationDate(consultationEntity.getCreatedOn());
      }

      // history가 존재하지만 oneToMany로 갸져오지 못하여 강제로 이력을 가져와서 추가한다.
      List<ConsultationHistoryEntity> newHistoryEntities = consultationHistoryRepository.findByConsultationEntity_Id(id);
      consultationEntity.getConsultationHistoryEntities().clear();
      consultationEntity.getConsultationHistoryEntities().addAll(newHistoryEntities);

      // 회원 등록
      UserEntity userEntity = userAdminService.createUser(createUser);
      // 회원 상담이력 테이블에 회원 등록 임을 알리는 데이터 추가
      List<MemberConsultationEntity> memberConsultationEntities = new ArrayList<>();
      MemberConsultationEntity memberConsultationEntity = new MemberConsultationEntity();
      memberConsultationEntity.setUserEntity(userEntity);
      memberConsultationEntity.setConsultationDate(consultationEntity.getConsultationDate());
      memberConsultationEntity.setType(MemberConsultationType.ETC.getCode());
      memberConsultationEntity.setDetails(consultationEntity.getDetails());
      memberConsultationEntity.setCreatedBy(consultationEntity.getCreatedBy());
      memberConsultationEntities.add(memberConsultationEntity);

      for (ConsultationHistoryEntity consultationHistoryEntity : consultationEntity.getConsultationHistoryEntities()) {
        MemberConsultationEntity entity = new MemberConsultationEntity();
        entity.setUserEntity(userEntity);
        entity.setConsultationDate(consultationHistoryEntity.getCreatedOn());
        entity.setType(MemberConsultationType.ETC.getCode());
        entity.setDetails(consultationHistoryEntity.getDetails());
        entity.setCreatedBy(consultationHistoryEntity.getCreatedBy());

        memberConsultationEntities.add(entity);
      }

      userEntity.setMemberConsultationEntities(memberConsultationEntities);

      userRepository.save(userEntity);

      consultationEntity.setIsMember(YN.Y);
      consultationEntity.setStatus(ConsultationStatus.REGISTERED.getCode());

      return userEntity.getId();
    }).orElseThrow(() -> new LmsException(LmsErrorCode.CONSULTATION_NOT_FOUND));
  }

  @Transactional
  public String createUser(long id, String createdBy) {
    return consultationRepository.findById(id).map(consultationEntity -> {
      if (StringUtils.hasNotText(consultationEntity.getEmail())) {
        throw new LmsException(LmsErrorCode.CONSULTATION_EMAIL_NOT_FOUND);
      }

      if (consultationEntity.getIsMember() == YN.Y) {
        throw new LmsException(LmsErrorCode.CONSULTATION_ALREADY_MEMBER);
      }

      if (ConsultationStatus.of(consultationEntity.getStatus()) == ConsultationStatus.REGISTERED) {
        throw new LmsException(LmsErrorCode.CONSULTATION_ALREADY_MEMBER);
      }
      // 이메일이 신규 아이디와 동일할 수 없음. 동일한 경우 에러처리
      if (userRepository.existsByLoginId(consultationEntity.getEmail())) {
        throw new LmsException(LmsErrorCode.CONSULTATION_EMAIL_ALREADY_EXISTS);
      }
      if (consultationEntity.getConsultationDate() == null) {
        consultationEntity.setConsultationDate(consultationEntity.getCreatedOn());
      }
      // 회원 등록
      UserEntity userEntity = new UserEntity();
      userEntity.setId(LmsUtils.createUserId());
      userEntity.setLoginId(consultationEntity.getEmail());
      userEntity.setName(consultationEntity.getName());
      userEntity.setType(UserType.S);
      userEntity.setGender(consultationEntity.getGender());
      userEntity.setCellPhone(consultationEntity.getCellPhone());
      userEntity.setIsReceiveSms(YN.N);
      userEntity.setEmail(consultationEntity.getEmail());
      userEntity.setIsReceiveEmail(YN.N);
      userEntity.setActive(true);
      userEntity.setCreatedBy(createdBy);
      // 회원 상담이력 테이블에 회원 등록 임을 알리는 데이터 추가
      List<MemberConsultationEntity> memberConsultationEntities = new ArrayList<>();
      MemberConsultationEntity memberConsultationEntity = new MemberConsultationEntity();
      memberConsultationEntity.setUserEntity(userEntity);
      memberConsultationEntity.setConsultationDate(consultationEntity.getConsultationDate());
      memberConsultationEntity.setType(MemberConsultationType.ETC.getCode());
      memberConsultationEntity.setDetails(consultationEntity.getDetails());
      memberConsultationEntity.setCreatedBy(consultationEntity.getCreatedBy());

      memberConsultationEntities.add(memberConsultationEntity);

      for (ConsultationHistoryEntity consultationHistoryEntity : consultationEntity.getConsultationHistoryEntities()) {
        MemberConsultationEntity entity = new MemberConsultationEntity();
        entity.setUserEntity(userEntity);
        entity.setConsultationDate(consultationHistoryEntity.getCreatedOn());
        entity.setType(MemberConsultationType.ETC.getCode());
        entity.setDetails(consultationHistoryEntity.getDetails());
        entity.setCreatedBy(consultationHistoryEntity.getCreatedBy());

        memberConsultationEntities.add(entity);
      }

      userEntity.setMemberConsultationEntities(memberConsultationEntities);

      userRepository.save(userEntity);

      consultationEntity.setIsMember(YN.Y);
      consultationEntity.setStatus(ConsultationStatus.REGISTERED.getCode());

      return userEntity.getId();
    }).orElseThrow(() -> new LmsException(LmsErrorCode.CONSULTATION_NOT_FOUND));
  }

  /**
   * 상담 등록
   */
  @Transactional
  public void createConsultation(CreateConsultation createConsultation) {

//    long fileSizeInBytes = createConsultation.getMultipartFile().getSize();
//    if (fileSizeInBytes > 209715) {
//      throw new LmsException(LmsErrorCode.FILE_SIZE_EXCEEDED);
//    }

    boolean exists = consultationRepository.existsByCellPhone(createConsultation.getCellPhone());
    if (exists) {
      throw new LmsException(LmsErrorCode.CELLPHONE_NOT_MATCH);
    }

    String file = fileService.upload(createConsultation.getMultipartFile());

    String callTime = serviceMapper.toCallTime(createConsultation.getCallTime());
    String foundPath = serviceMapper.toJoinPath(createConsultation.getFoundPath());

    String studyPurposes = createConsultation.getStudyPurposes();

    ConsultationEntity consultationEntity = consultationAdminServiceMapper.toConsultationEntity(
        createConsultation);
    consultationEntity.setCallTime(callTime);
    consultationEntity.setFoundPath(foundPath);
    consultationEntity.setStudyPurpose(studyPurposes);

    consultationEntity.setFile(file);

    consultationRepository.save(consultationEntity);
  }


  /**
   * 상담(기본) 상세 조회
   */
  public ConsultationInfo getConsultation(long id) {
    ConsultationEntity consultationEntity = consultationRepository.findById(id)
        .orElseThrow(() -> new LmsException(LmsErrorCode.CONSULTATION_NOT_FOUND));

    String modifiedName = userRepository.findById(consultationEntity.getModifiedBy())
        .map(UserEntity::getName)
        .orElse(null);

    String createdName = userRepository.findById(consultationEntity.getCreatedBy())
        .map(UserEntity::getName)
        .orElse(null);

    List<StudyPurpose> studyPurposes = new ArrayList<>();

    if (consultationEntity.getStudyPurpose() != null && !consultationEntity.getStudyPurpose()
        .isEmpty()) {
      String[] studyPurposesArr = (consultationEntity.getStudyPurpose()).split(",");
      studyPurposes = Arrays.stream(studyPurposesArr)
          .map(StudyPurpose::of)
          .collect(Collectors.toList());
    } else {
      String[] studyPurposesArr = new String[1];
      studyPurposesArr[0] = "10";
      studyPurposes = Arrays.stream(studyPurposesArr)
          .map(StudyPurpose::of)
          .collect(Collectors.toList());
    }

    GetConsultation consultation = consultationAdminServiceMapper.toGetConsultation(
        consultationEntity, studyPurposes,
        fileService.getUrl(consultationEntity.getFile(), consultationEntity.getOriginalFile()));

    // String -> JoinPath 변환
    JoinPath joinPath = serviceMapper.toJoinPath(consultationEntity.getFoundPath());
    CallTime callTime = serviceMapper.toCallTime(consultationEntity.getCallTime());

    ConsultationInfo consultationInfo = ConsultationInfo.builder()
        .id(consultation.getId())
        .consultationDate(consultation.getConsultationDate())
        .name(consultation.getName())
        .gender(consultation.getGender())
        .job(consultation.getJob())
        .company(consultation.getCompany())
        .phone(consultation.getPhone())
        .cellPhone(consultation.getCellPhone())
        .foundPathNote(consultation.getFoundPathNote())
        .visitDate(consultation.getVisitDate())
        .details(consultation.getDetails())
        .isMember(consultation.getIsMember())
        .type(consultation.getType())
        .studyPurposes(consultation.getStudyPurposes())
        .etcStudyPurpose(consultation.getEtcStudyPurpose())
        .email(consultation.getEmail())
        .status(consultation.getStatus())
        .file(consultation.getFile())
        .originalFile(consultation.getOriginalFile())
        .fileUrl(consultation.getFileUrl())
        .createdBy(consultation.getCreatedBy())
        .createdOn(consultation.getCreatedOn())
        .modifiedBy(consultation.getModifiedBy())
        .modifiedOn(consultation.getModifiedOn())
        .modifiedName(modifiedName)
        .createdName(createdName)
        .callTime(callTime)
        .foundPath(joinPath)
        .build();

    return consultationInfo;

  }

  /**
   * 상담 수정
   */
  @Transactional
  public void updateConsultation(UpdateConsultation updateConsultation) {

    ConsultationEntity consultationEntity = consultationRepository.findById(
            updateConsultation.getId())
        .orElseThrow(() -> new LmsException(LmsErrorCode.CONSULTATION_NOT_FOUND));

    // (추가) 최대 용량 제한
//    long fileSizeInBytes = updateConsultation.getMultipartFile().getSize();
//    if (fileSizeInBytes > 209715) {
//      throw new LmsException(LmsErrorCode.FILE_SIZE_EXCEEDED);
//    }

//    String foundPath = serviceMapper.toJoinPath(updateConsultation.getFoundPath());
//    String callTime = serviceMapper.toCallTime(updateConsultation.getCallTime());

    // 기존 파일 존재 여부 (있을 시 초기화)
    if (updateConsultation.getIsDeleteFile() != null && updateConsultation.getIsDeleteFile()) {
      consultationEntity.setFile(null);
      consultationEntity.setOriginalFile(null);
    }

    String file = fileService.upload(updateConsultation.getMultipartFile());

    if (file != null) {
      consultationEntity.setFile(file);
      consultationEntity.setOriginalFile(updateConsultation.getOriginalFile());
    }

    consultationAdminServiceMapper.mapConsultationEntity(updateConsultation, consultationEntity);

    // 추가 상담 이력 테이블에 추가
    ConsultationHistoryEntity consultationHistoryEntity = new ConsultationHistoryEntity();
    consultationHistoryEntity.setDetails(updateConsultation.getDetails());
    consultationHistoryEntity.setDate(LocalDateTime.now());
    consultationHistoryEntity.setCreatedBy(updateConsultation.getModifiedBy());
    consultationHistoryEntity.setConsultationEntity(consultationEntity);
    consultationHistoryRepository.save(consultationHistoryEntity);
  }


  @Transactional
  public void deleteConsultation(Long id) {
    consultationRepository.deleteById(id);
    consultationRepository.flush();
  }

  /**
   * 추가 상담 이력 등록
   */
  @Transactional
  public void createConsultationHistory(CreateConsultationHistory consultationHistory) {
    ConsultationEntity consultationEntity = consultationRepository.findById(
            consultationHistory.getConsultationId())
        .orElseThrow(() -> new LmsException(LmsErrorCode.CONSULTATION_NOT_FOUND));

    ConsultationHistoryEntity consultationHistoryEntity = consultationAdminServiceMapper.toConsultationHistoryEntity(
        consultationHistory);

    consultationHistoryRepository.save(consultationHistoryEntity);

  }

  @Transactional
  public void deleteConsultationHistory(Long id) {
    consultationHistoryRepository.deleteById(id);
  }


  /**
   * 추가 상담 이력 리스트 조회
   */
  public List<ConsultationHistory> listConsultationHistories(Long consultationId) {

    if (consultationId == null) {
      throw new LmsException(LmsErrorCode.CONSULTATION_NOT_FOUND);
    }

    List<ConsultationHistoryEntity> consultationHistoryEntities = consultationHistoryRepository.findByConsultationEntity_Id(
        consultationId);

    return consultationHistoryEntities.stream()
        .map(consultationHistoryEntity -> {
          String modifiedName = null;

          if (consultationHistoryEntity.getModifiedBy() != null) {
            modifiedName = userRepository.findById(consultationHistoryEntity.getModifiedBy())
                .map(UserEntity::getName)
                .orElse(null);
          }
          return consultationAdminServiceMapper.toConsultationHistory(consultationHistoryEntity,
              modifiedName);
        })
        .collect(Collectors.toList());
  }

  /**
   * 연락처 중복 체크
   */
  @Transactional
  public void valCellphone(GetValCellphoneRequest request) {
    boolean exists = consultationRepository.existsByCellPhone(request.getCellPhone());
    if (exists) {
      throw new LmsException(LmsErrorCode.CELLPHONE_NOT_MATCH);
    }
  }
}
