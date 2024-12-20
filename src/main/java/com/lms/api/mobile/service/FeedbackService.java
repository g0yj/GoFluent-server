package com.lms.api.mobile.service;

import com.lms.api.common.entity.LdfEntity;
import com.lms.api.common.entity.ReservationEntity;
import com.lms.api.common.exception.LmsErrorCode;
import com.lms.api.common.exception.LmsException;
import com.lms.api.common.repository.CourseRepository;
import com.lms.api.common.repository.LdfRepository;
import com.lms.api.common.repository.ReservationRepository;
import com.lms.api.common.repository.UserRepository;
import com.lms.api.mobile.controller.dto.feedback.ListFeedbackRequest;
import com.lms.api.mobile.controller.dto.feedback.UpdateLdfRequest;
import com.lms.api.mobile.service.dto.feedback.CompleteFeedback;
import com.lms.api.mobile.service.dto.feedback.Ldf;
import com.lms.api.mobile.service.dto.feedback.ListFeedback;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FeedbackService {
  private final ReservationRepository reservationRepository;
  private final LdfRepository ldfRepository;
  private final UserRepository userRepository;
  private final FeedbackServiceMapper feedbackServiceMapper;
  private final CourseRepository courseRepository;


    @Transactional
    public List<ListFeedback> listFeedback(String id, ListFeedbackRequest listFeedbackRequest) {
        List<ReservationEntity> reservationEntities;

        // courseId가 0이면 courseId 없이 조회, 아니면 courseId를 포함해서 조회
        if (listFeedbackRequest.getCourseId() == 0L) {
            reservationEntities = reservationRepository.findReservationsBeforeDateTimeWithoutCourseId(id);
        } else {
            reservationEntities = reservationRepository.findReservationsBeforeDateTime(listFeedbackRequest.getCourseId(), id);
        }

        return reservationEntities.stream()
                .map(reservationEntity -> {
                    return ListFeedback.builder()
                            .reservation(feedbackServiceMapper.toReservation(reservationEntity))
                            .teacherName(reservationEntity.getTeacherEntity().getUserEntity().getName())
                            .build();
                })
                .collect(Collectors.toList());
    }


  @Transactional
  public Ldf getLdf(Long id){
      LdfEntity qLdfEntity = ldfRepository.findByReservationEntityId(id)
              .orElseThrow(() -> new LmsException(LmsErrorCode.LDF_NOT_FOUND));

      ReservationEntity reservationEntity = reservationRepository.findById(id)
              .orElseThrow(() -> new LmsException(LmsErrorCode.RESERVATION_NOT_FOUND));

      Ldf ldf = feedbackServiceMapper.toLdf(qLdfEntity);
      ldf.setDate(reservationEntity.getDate());
      ldf.setStartTime(reservationEntity.getStartTime());
      ldf.setEndTime(reservationEntity.getEndTime());
      ldf.setTeacherName(reservationEntity.getTeacherEntity().getUserEntity().getName());
      return ldf;
    }

  @Transactional
  public void updateFeedback(UpdateLdfRequest request){
    ldfRepository.findById(request.getId())
        .ifPresentOrElse(
            ldfEntity->
                feedbackServiceMapper.mapLdfEntity(request, ldfEntity ),
            ()-> {
              throw new LmsException(LmsErrorCode.LDF_NOT_FOUND);
            });
  }

    @Transactional
    public List<CompleteFeedback> completionFeedback(String userId) {
        List<ReservationEntity> reservationEntities = reservationRepository.findAllByUserEntityId(userId);
        List<CompleteFeedback> completeFeedbackList = new ArrayList<>();

        for (ReservationEntity reservationEntity : reservationEntities) {
            ldfRepository.findByReservationEntityId(reservationEntity.getId())
                    .ifPresentOrElse(ldfEntity -> {
                        // Check if the reservation entity ID matches the LDF entity's reservation entity ID
                        if (ldfEntity.getReservationEntity().getId().equals(reservationEntity.getId())) {
                            CompleteFeedback completeFeedback = CompleteFeedback.builder()
                                    .id(reservationEntity.getId())
                                    .date(reservationEntity.getDate())
                                    .startTime(reservationEntity.getStartTime())
                                    .endTime(reservationEntity.getEndTime())
                                    .attendanceStatus(reservationEntity.getAttendanceStatus())
                                    .teacherName(reservationEntity.getTeacherEntity().getUserEntity().getName())
                                    .ldfId(ldfEntity.getId())
                                    .lesson(ldfEntity.getLesson())
                                    .contentSp(ldfEntity.getContentSp())
                                    .contentV(ldfEntity.getContentV())
                                    .contentSg(ldfEntity.getContentSg())
                                    .contentC(ldfEntity.getContentC())
                                    .grade(ldfEntity.getGrade())
                                    .evaluation(ldfEntity.getEvaluation())
                                    .build();
                            completeFeedbackList.add(completeFeedback);
                        }
                    }, () -> {
                    });
        }

        return completeFeedbackList;
    }
}
