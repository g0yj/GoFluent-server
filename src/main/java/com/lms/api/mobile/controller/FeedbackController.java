package com.lms.api.mobile.controller;

import com.lms.api.common.dto.LoginInfo;
import com.lms.api.mobile.controller.dto.feedback.ListFeedbackRequest;
import com.lms.api.mobile.controller.dto.feedback.ListFeedbackResponse;
import com.lms.api.mobile.controller.dto.feedback.UpdateLdfRequest;
import com.lms.api.mobile.service.FeedbackService;
import com.lms.api.mobile.service.dto.feedback.CompleteFeedback;
import com.lms.api.mobile.service.dto.feedback.Ldf;
import com.lms.api.mobile.service.dto.feedback.ListFeedback;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mobile/v1/feedback")
@RequiredArgsConstructor
@Slf4j
public class FeedbackController {
    private final FeedbackService feedbackService;
    private final FeedbackControllerMapper feedbackControllerMapper;


/**
     *  01.피드백 목록  : reservation엔티티사용, 조건> date와 startTime이 지난 정보들 , LDF가 존재하는 것들
     * @request : LoginInfo , 현재날짜+시간
     * @response : date, startTime, endTime, 출석여부 , 강사이름
     * */
    @GetMapping
    public List<ListFeedbackResponse> listFeedback(LoginInfo loginInfo , ListFeedbackRequest request){
        List<ListFeedback> listFeedbacks = feedbackService.listFeedback(loginInfo.getId(), request);
        return feedbackControllerMapper.toListFeedbackResponse(listFeedbacks);
    }

    /**
     * 03. ldf 상세보기(레슨 피드백에 대한 상세 조회)
     * */
    @GetMapping("/{id}")
    public Ldf getLdf(@PathVariable Long id){
        return feedbackService.getLdf(id);
    }

    /** 02. 레슨피드백 평점 등록
     * @request : ldf식별키, grade , evaluation
     * */
    @PutMapping
    public void updateFeedback(@Valid @RequestBody UpdateLdfRequest request){
        feedbackService.updateFeedback(request);
    }

    /**
     * 04. 완료 레슨 피드백 목록
     * */
    @GetMapping("/completion")
    public List<CompleteFeedback> completionFeedback(LoginInfo loginInfo){
        return feedbackService.completionFeedback(loginInfo.getId());
    }

}
