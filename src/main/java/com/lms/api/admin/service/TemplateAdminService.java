package com.lms.api.admin.service;


import com.lms.api.admin.code.SearchReservationCode;
import com.lms.api.admin.code.SearchSmsCode;
import com.lms.api.admin.code.SearchUsersCode;
import com.lms.api.admin.controller.dto.template.ListReportExcelRequest;
import com.lms.api.admin.controller.dto.template.ListReportExcelResponse;
import com.lms.api.admin.controller.dto.template.ListSmsExcelRequest;
import com.lms.api.admin.controller.dto.template.ListSmsExcelResponse;
import com.lms.api.admin.controller.dto.template.ListUsersExcelRequest;
import com.lms.api.admin.service.dto.User;
import com.lms.api.admin.service.dto.template.CreateTemplate;
import com.lms.api.admin.service.dto.template.MemberRegisterTemplate;
import com.lms.api.admin.service.dto.template.SimpleTemplate;
import com.lms.api.admin.service.dto.template.UpdateTemplate;
import com.lms.api.common.code.AttendanceStatus;
import com.lms.api.common.code.UserType;
import com.lms.api.common.entity.CourseEntity;
import com.lms.api.common.entity.QCourseEntity;
import com.lms.api.common.entity.QOrderEntity;
import com.lms.api.common.entity.QReservationEntity;
import com.lms.api.common.entity.QSmsEntity;
import com.lms.api.common.entity.QUserEntity;
import com.lms.api.common.entity.ReservationEntity;
import com.lms.api.common.entity.SmsEntity;
import com.lms.api.common.entity.TemplateEntity;
import com.lms.api.common.entity.UserEntity;
import com.lms.api.common.exception.LmsErrorCode;
import com.lms.api.common.exception.LmsException;
import com.lms.api.common.repository.ReservationRepository;
import com.lms.api.common.repository.SmsRepository;
import com.lms.api.common.repository.TemplateRepository;
import com.lms.api.common.repository.UserRepository;
import com.lms.api.common.util.StringUtils;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TemplateAdminService {

    private final TemplateRepository templateRepository;
    private final TemplateServiceMapper templateServiceMapper;
    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;
    private final ReservationAdminServiceMapper reservationAdminServiceMapper;
    private final UserAdminServiceMapper userAdminServiceMapper;
    private final SmsRepository smsRepository;
    private final SmsAdminServiceMapper smsAdminServiceMapper;


    /**
     * 단순 템플릿 목록 > 식별키, 템플릿 설명, 템플릿 내용만 확인 가능
     */

    public List<SimpleTemplate> listSimpleTemplate() {

        return templateRepository.findAll().stream()
                .map(templateEntity -> templateServiceMapper.toSimpleTemplate(templateEntity))
                .collect(Collectors.toList());
    }

    /**
     * 템플릿 등록
     */
    @Transactional
    public void createTemplate(CreateTemplate createTemplate) {
        TemplateEntity templateEntity = templateServiceMapper.toTemplateEntity(
                createTemplate);
        templateRepository.save(templateEntity);
    }

    /**
     * 템플릿 상세 조회
     */
    public MemberRegisterTemplate getTemplate(Long id) {

        Optional<TemplateEntity> template = templateRepository.findById(id);
        if (template.isPresent()) {
            return templateServiceMapper.toMemberRegisterTemplate(template.get());
        }

        return MemberRegisterTemplate.builder().build();

//        TemplateEntity templateEntity = templateRepository.findById(id)
//                .filter(templateEntities -> templateEntities.getId().equals(id))
//                .orElseThrow(() -> new LmsException(LmsErrorCode.TEMPLATE_NOT_FOUND));
//        return  templateServiceMapper.toMemberRegisterTemplate(templateEntity);

    }

    /**
     * 템플릿 수정
     */
    @Transactional
    public void updateTemplate(UpdateTemplate updateTemplate) {
        TemplateEntity templateEntity = new TemplateEntity();
        templateEntity.setId(updateTemplate.getId());
        templateEntity.setText(updateTemplate.getText());
        templateEntity.setDescription(updateTemplate.getDescription());
       templateRepository.save(templateEntity);
//        templateRepository.findById(updateTemplate.getId())
//                .ifPresentOrElse(
//                        templateEntity -> templateServiceMapper.mapTemplateEntity(updateTemplate,
//                                templateEntity),
//                        () -> {
//                            throw new LmsException(LmsErrorCode.TEMPLATE_NOT_FOUND);
//                        });
    }

    /**
     * 템플릿 삭제
     */
    @Transactional
    public void deleteTemplate(long id) {
        templateRepository.deleteById(id);
    }


    /**
     * type > 학사보고서 report , 회원 user
     */
    @Transactional
    public List<ListReportExcelResponse> listReportExcel(String loginId, ListReportExcelRequest request) {
        QReservationEntity qReservationEntity = QReservationEntity.reservationEntity;
        QUserEntity qUserEntity = QUserEntity.userEntity;

        BooleanExpression where = Expressions.TRUE;

        if (request.getUserType() == UserType.T) {
            where = where.and(qReservationEntity.teacherEntity.userId.eq(loginId));
        }

        if (request.getDateFrom() != null) {
            where = where.and(qReservationEntity.date.goe(request.getDateFrom()));
        }
        if (request.getDateTo() != null) {
            where = where.and(qReservationEntity.date.loe(request.getDateTo()));
        }

        if (request.getReportCondition() == SearchReservationCode.ReportCondition.ATTENDANCE) {
            where = where.and(qReservationEntity.attendanceStatus.eq(AttendanceStatus.Y));
        }
        ;

        if (request.getReportCondition() == SearchReservationCode.ReportCondition.REPORT) {
            where = where.and(qReservationEntity.report.isNull());
        }

        if (request.getTeacherId() != null && !request.getTeacherId().isEmpty()) {
            where = where.and(qReservationEntity.teacherEntity.userId.eq(request.getTeacherId()));
        }

        if (request.getKeyword() != null && !request.getKeyword().isEmpty()) {
            where = where.and(qReservationEntity.userEntity.name.contains(request.getKeyword()));
        }
        List<ReservationEntity> reservationEntities = (List<ReservationEntity>) reservationRepository.findAll(where, Sort.by(Sort.Direction.ASC, "date"));

        List<ListReportExcelResponse> reportExcel = reservationEntities.stream()
                .map(reservationEntity -> {
                    try {
                        Float assignedLessonCount = Optional.ofNullable(reservationEntity.getCourseEntity())
                                .map(CourseEntity::getAssignmentCount)
                                .orElse(0f);
                        Float lessoncount = Optional.ofNullable(reservationEntity.getCourseEntity())
                                .map(CourseEntity::getLessonCount)
                                .orElse(0f);
                        Float remainingLessonCount = assignedLessonCount - lessoncount;

                        return ListReportExcelResponse.builder()
                                .id(reservationEntity.getId())
                                .date(reservationEntity.getDate())
                                .startTime(reservationEntity.getStartTime())
                                .endTime(reservationEntity.getEndTime())
                                .attendanceStatus(reservationEntity.getAttendanceStatus().getLabel())
                                .report(reservationEntity.getReport())
                                .todayLesson(reservationEntity.getTodayLesson())
                                .nextLesson(reservationEntity.getNextLesson())
                                .userName(reservationEntity.getUserEntity().getName())
                                .cellPhone(reservationEntity.getUserEntity().getCellPhone())
                                .assignedLessonCount(assignedLessonCount)
                                .lessoncount(lessoncount)
                                .remainingLessonCount(remainingLessonCount)
                                .build();
                    } catch (EntityNotFoundException e) {
                        // CourseEntity가 없을 때 예외를 무시하고 계속 진행
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return reportExcel;
    }

    public List<User> listUserExcel(ListUsersExcelRequest request) {
        QUserEntity qUserEntity = QUserEntity.userEntity;
        QOrderEntity qOrderEntity = QOrderEntity.orderEntity;
        QCourseEntity qCourseEntity = QCourseEntity.courseEntity;
        LocalDate now = LocalDate.now();

        // 회원 검색 조건
        BooleanExpression where = Expressions.TRUE;

        // 회원과 관리자(직원) 목록 api 공동으로 사용하기 위한 type
        if (request.getType() != null) {
            if (request.getType() == UserType.S) {
                where = where.and(qUserEntity.type.eq(UserType.S));
            } else if (request.getType() == UserType.A) {
                where = where.and(qUserEntity.type.eq(UserType.A));
            }
        }

        // 회원 과정 검색 조건 (회원의 수강 여부 구분)
        BooleanExpression courseExists = qCourseEntity.userEntity.eq(qUserEntity);
        BooleanExpression courseNotExists = qCourseEntity.userEntity.eq(qUserEntity);

        // 가입일자
        if (request.getCreateDateFrom() != null && request.getCreateDateTo() != null) {
            where = where.and(
                    qUserEntity.createdOn.between(request.getCreateDateFrom().atStartOfDay(),
                            request.getCreateDateTo().atTime(23, 59, 59)));
        }

        // 등록구분 등록회원/미등록회원 - 결제테이블의 결제 금액이 있냐 없냐 유무
        if (request.getRegisterType() == SearchUsersCode.RegisterType.REGISTERED) {
            where = where.and(
                    JPAExpressions.selectFrom(qOrderEntity).where(qOrderEntity.userEntity.eq(qUserEntity)
                            .and(qOrderEntity.paymentAmount.gt(0))).exists());
        } else if (request.getRegisterType() == SearchUsersCode.RegisterType.UNREGISTERED) {
            where = where.and(
                    JPAExpressions.selectFrom(qOrderEntity).where(qOrderEntity.userEntity.eq(qUserEntity)
                            .and(qOrderEntity.paymentAmount.gt(0))).notExists());
        }

        // 상태 활동/비활동
        if (request.getStatus() == SearchUsersCode.UserStatus.ACTIVE) {
            where = where.and(qUserEntity.active.isTrue());
        } else if (request.getStatus() == SearchUsersCode.UserStatus.INACTIVE) {
            where = where.and(qUserEntity.active.isFalse());
        }

        // 담당강사 ID
        if (StringUtils.hasText(request.getTeacherId())) {
            courseExists = courseExists.and(
                    qCourseEntity.teacherEntity.userId.eq(request.getTeacherId()));
        }

        // 수강상태 수강중/비수강중/대기중
        if (request.getCourseStatus() == SearchUsersCode.CourseStatus.ATTENDING) {
            //수강중 : course(수강) 시작일<=현재 , 현재<= 종료일 , 레슨횟수 > 배정횟수
            courseExists = courseExists.and(qCourseEntity.startDate.loe(now))
                    .and(qCourseEntity.endDate.goe(now))
                    .and(qCourseEntity.lessonCount.gt(qCourseEntity.assignmentCount));
            //비수강중 : 현재 <= 종료일 ,
        } else if (request.getCourseStatus() == SearchUsersCode.CourseStatus.NOT_ATTENDING) {
            courseNotExists = courseNotExists.and(qCourseEntity.endDate.goe(now))
                    .and(qCourseEntity.lessonCount.gt(qCourseEntity.assignmentCount));
        } else if (request.getCourseStatus() == SearchUsersCode.CourseStatus.WAITING) {
            courseExists = courseExists.and(qCourseEntity.startDate.gt(now))
                    .and(qCourseEntity.lessonCount.gt(qCourseEntity.assignmentCount));
        }

        // 만료구분 만료됨/만료안됨
        if (request.getExpireType() == SearchUsersCode.ExpireType.EXPIRED) {
            courseNotExists = courseNotExists.and(qCourseEntity.endDate.goe(now));
        } else if (request.getExpireType() == SearchUsersCode.ExpireType.NOT_EXPIRED) {
            courseExists = courseExists.and(qCourseEntity.endDate.goe(now));
        }

        // 잔여구분 잔여있음/잔여없음
        if (request.getRemainingType() == SearchUsersCode.RemainingType.REMAINING) {
            courseExists = courseExists.and(qCourseEntity.lessonCount.gt(qCourseEntity.assignmentCount));
        } else if (request.getRemainingType() == SearchUsersCode.RemainingType.NOT_REMAINING) {
            courseNotExists = courseNotExists.and(
                    qCourseEntity.lessonCount.gt(qCourseEntity.assignmentCount));
        }

        // 조건 필터링
        if (Stream.of(StringUtils.hasText(request.getTeacherId()),
                        request.getCourseStatus() == SearchUsersCode.CourseStatus.ATTENDING,
                        request.getCourseStatus() == SearchUsersCode.CourseStatus.WAITING,
                        request.getExpireType() == SearchUsersCode.ExpireType.NOT_EXPIRED,
                        request.getRemainingType() == SearchUsersCode.RemainingType.REMAINING)
                .anyMatch(Boolean::booleanValue)) {

            where = where.and(JPAExpressions.selectFrom(qCourseEntity).where(courseExists).exists());
        }

        if (Stream.of(request.getCourseStatus() == SearchUsersCode.CourseStatus.NOT_ATTENDING,
                        request.getExpireType() == SearchUsersCode.ExpireType.EXPIRED,
                        request.getRemainingType() == SearchUsersCode.RemainingType.NOT_REMAINING)
                .anyMatch(Boolean::booleanValue)) {

            where = where.and(
                    JPAExpressions.selectFrom(qCourseEntity).where(courseNotExists).notExists());
        }
        if (request.hasSearch()) {
            switch (request.getSearch()) {
                case "ALL":
                    where = where.and(
                            qUserEntity.name.contains(request.getKeyword())
                                    .or(qUserEntity.loginId.contains(request.getKeyword()))
                                    .or(qUserEntity.email.contains(request.getKeyword()))
                                    .or(qUserEntity.company.contains(request.getKeyword()))
                                    .or(qUserEntity.phone.contains(request.getKeyword()))
                                    .or(qUserEntity.cellPhone.contains(request.getKeyword()))
                    );
                    break;
                case "name":
                    where = where.and(qUserEntity.name.contains(request.getKeyword()));
                    break;
                case "loginId":
                    where = where.and(qUserEntity.loginId.contains(request.getKeyword()));
                    break;
                case "email":
                    where = where.and(qUserEntity.email.contains(request.getKeyword()));
                    break;
                case "company":
                    where = where.and(qUserEntity.company.contains(request.getKeyword()));
                    break;
                case "phone":
                    where = where.and(qUserEntity.phone.contains(request.getKeyword()));
                case "cellPhone":
                    where = where.and(qUserEntity.cellPhone.contains(request.getKeyword()));
                    break;
                default:
                    break;
            }
        }

        List<UserEntity> userEntities = (List<UserEntity>) userRepository.findAll(where, Sort.by(Sort.Direction.ASC, "createdOn"));
        return userEntities.stream()
                .map(userEntity -> userAdminServiceMapper.toUser(userEntity))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<ListSmsExcelResponse> listSmsExcel(ListSmsExcelRequest request) {
        QSmsEntity qSmsEntity = QSmsEntity.smsEntity;

        BooleanExpression where = Expressions.TRUE;

        if (request.getSendDateFrom() != null && request.getSendDateTo() != null) {
            where = where.and(qSmsEntity.sendDate.between(
                    request.getSendDateFrom().atStartOfDay(),
                    request.getSendDateTo().atTime(23, 59, 59)));
        }

        if (request.getSendType() != null && request.getSendType() != SearchSmsCode.SearchType.ALL) {
            where = where.and(qSmsEntity.sendType.eq(SearchSmsCode.SearchType.valueOf(request.getSendType().name())));
        }

        if (request.hasSearch()) {
            switch (request.getSearch()) {
                case "ALL":
                    where = where.and(
                            qSmsEntity.content.contains(request.getKeyword())
                                    .or(qSmsEntity.senderName.contains(request.getKeyword()))
                    );
                    break;
                case "content":
                    where = where.and(qSmsEntity.content.contains(request.getKeyword()));
                    break;
                case "senderName":
                    where = where.and(qSmsEntity.senderName.contains(request.getKeyword()));
                    break;
                default:
                    break;
            }
        }

        Iterable<SmsEntity> smsEntities = smsRepository.findAll(where, Sort.by(Sort.Direction.DESC, "createdOn"));

        // 반환할 ListSmsExcelResponse 리스트 생성
        List<ListSmsExcelResponse> responseList = new ArrayList<>();  // 구체적인 구현체 생성

        // SmsEntity 리스트를 ListSmsExcelResponse로 변환
        smsEntities.forEach(smsEntity -> {
            UserEntity userEntity = userRepository.findByNameAndTypeNot(smsEntity.getSenderName(), UserType.S);
            String createdBy = userEntity != null ? userEntity.getId() : null;
            int total = smsEntity.getSmsTargetEntities().size();
            LocalDate date = smsEntity.getSendDate().toLocalDate();
            LocalTime time = smsEntity.getSendDate().toLocalTime();

            ListSmsExcelResponse response = smsAdminServiceMapper.listSmsExcelResponse(
                    smsEntity, createdBy, total, date, time
            );

            responseList.add(response);  // 리스트에 추가
        });

        return responseList;  // 구체적인 리스트 구현체 반환
    }

}

