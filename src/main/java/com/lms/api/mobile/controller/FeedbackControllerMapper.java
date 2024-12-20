package com.lms.api.mobile.controller;

import com.lms.api.common.mapper.ControllerMapper;
import com.lms.api.common.mapper.ControllerMapperConfig;
import com.lms.api.mobile.controller.dto.feedback.ListFeedbackResponse;
import com.lms.api.mobile.service.dto.feedback.ListFeedback;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", config = ControllerMapperConfig.class, uses = {
    ControllerMapper.class})
public interface FeedbackControllerMapper {

  List<ListFeedbackResponse> toListFeedbackResponse(List<ListFeedback> listFeedbacks);

  @Mapping(target = "id" , source = "reservation.id")
  @Mapping(target = "date" , source = "reservation.date")
  @Mapping(target = "startTime" , source = "reservation.startTime")
  @Mapping(target = "endTime" , source = "reservation.endTime")
  @Mapping(target = "attendanceStatus" , source = "reservation.attendanceStatus")
  @Mapping(target = "ldfYN" , source = "reservation.ldfYN")
  ListFeedbackResponse toListFeedbackResponse(ListFeedback feedback);
}
