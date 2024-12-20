package com.lms.api.mobile.service;

import com.lms.api.admin.code.SearchUserCoursesCode;
import com.lms.api.common.entity.QCourseEntity;
import com.lms.api.common.entity.UserEntity;
import com.lms.api.common.exception.LmsErrorCode;
import com.lms.api.common.exception.LmsException;
import com.lms.api.common.repository.CourseRepository;
import com.lms.api.common.repository.UserRepository;
import com.lms.api.common.util.LmsUtils;
import com.lms.api.mobile.controller.dto.UpdatePasswordResponse;
import com.lms.api.mobile.service.dto.UpdatePassword;
import com.lms.api.mobile.service.dto.UserInfo;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserStudentService {
  private final UserRepository userRepository;
  private final CourseRepository courseRepository;


  /** 비밀번호 찾기
   * 조건:
   * - 아이디와 동일하지 않기,
   * - 새 비번과 비밀번호 확인이 일치해야됨
   * */
  @Transactional
  public UpdatePasswordResponse updatePassword(UpdatePassword password){

    UserEntity userEntity = userRepository.findById(password.getId())
        .orElseThrow(() -> new LmsException(LmsErrorCode.USER_NOT_FOUND));

    if(password.getNewPassword()!= null){
        password.validatePasswordPattern();
    }
    if(!LmsUtils.checkPassword(password.getPassword(),userEntity.getPassword())){
      throw new LmsException(LmsErrorCode.CHANGEPW1_SERVER_ERROR);
    }

    if(userEntity.getLoginId().equals(password.getNewPassword())){
        throw new LmsException(LmsErrorCode.CHANGEPW2_SERVER_ERROR);
    }

    if(!password.getNewPassword().equals(password.getCheckPassword())){
      throw new LmsException(LmsErrorCode.CHANGEPW3_SERVER_ERROR);
    }

    userEntity.setPassword(LmsUtils.encryptPassword(password.getNewPassword()));
    userRepository.save(userEntity);

    return UpdatePasswordResponse.builder()
            .message("비밀번호가 변경되었습니다.")
            .build();
  }

  /** 내정보보기  */
  @Transactional
  public UserInfo getMyInfo(String userId) {
    QCourseEntity qCourseEntity = QCourseEntity.courseEntity;

    UserEntity userEntity = userRepository.findById(userId)
            .orElseThrow(() -> new LmsException(LmsErrorCode.USER_NOT_FOUND));

    LocalDate now = LocalDate.now();

    // 수강 신청 안한 경우
    boolean courseExistsForUser = courseRepository.exists(qCourseEntity.userEntity.id.eq(userId));
    if (!courseExistsForUser) {
      return UserInfo.builder()
              .id(userEntity.getId())
              .loginId(userEntity.getLoginId())
              .name(userEntity.getName())
              .courseStatus(null)
              .build();
    }

    // 수강 중: course 종료일 이후, 레슨 횟수 > 배정 횟수, 예약이 존재하는 경우
    boolean isAttending = courseRepository.exists(
            qCourseEntity.userEntity.id.eq(userId)
                    .and(qCourseEntity.endDate.after(now))
                    .and(qCourseEntity.lessonCount.gt(qCourseEntity.assignmentCount))
                    .and(qCourseEntity.reservationEntities.isNotEmpty())
    );

    if (isAttending) {
      return UserInfo.builder()
              .id(userEntity.getId())
              .loginId(userEntity.getLoginId())
              .name(userEntity.getName())
              .courseStatus(SearchUserCoursesCode.CourseStatus.ATTENDING)
              .build();
    }

    // 수강 완료: course 종료일이 현재보다 이전이거나, 레슨 횟수와 배정 횟수가 동일한 경우
    boolean isCompleted = courseRepository.exists(
            qCourseEntity.userEntity.id.eq(userId)
                    .and(qCourseEntity.endDate.before(now))
                    .or(qCourseEntity.lessonCount.eq(qCourseEntity.assignmentCount))
    );

    if (isCompleted) {
      return UserInfo.builder()
              .id(userEntity.getId())
              .loginId(userEntity.getLoginId())
              .name(userEntity.getName())
              .courseStatus(SearchUserCoursesCode.CourseStatus.COMPLETE)
              .build();
    }

    // 대기 중: 예약이 없는 경우
    boolean isWaiting = courseRepository.exists(
            qCourseEntity.userEntity.id.eq(userId)
                    .and(qCourseEntity.reservationEntities.isEmpty())
    );

    if (isWaiting) {
      return UserInfo.builder()
              .id(userEntity.getId())
              .loginId(userEntity.getLoginId())
              .name(userEntity.getName())
              .courseStatus(SearchUserCoursesCode.CourseStatus.WAITING)
              .build();
    }

    // CourseEntity가 존재하지만 어떤 상태에도 맞지 않는 경우
    return UserInfo.builder()
            .id(userEntity.getId())
            .loginId(userEntity.getLoginId())
            .name(userEntity.getName())
            .build();
  }

}
