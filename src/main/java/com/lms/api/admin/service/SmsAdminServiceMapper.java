package com.lms.api.admin.service;

import com.lms.api.admin.controller.dto.template.ListSmsExcelResponse;
import com.lms.api.admin.service.dto.User;
import com.lms.api.admin.service.dto.statistics.Sms;
import com.lms.api.admin.service.dto.statistics.SmsTarget;
import com.lms.api.common.entity.SmsEntity;
import com.lms.api.common.entity.SmsTargetEntity;
import com.lms.api.common.entity.UserEntity;
import com.lms.api.common.mapper.ServiceMapper;
import com.lms.api.common.mapper.ServiceMapperConfig;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", config = ServiceMapperConfig.class, uses = {ServiceMapper.class})
public interface SmsAdminServiceMapper {

  List<User> toUsers(Iterable<UserEntity> userEntities);

  // SmsEntity -> Sms 매핑
  @Mapping(target = "smsTargets", source = "smsTargetEntities")
  Sms smsEntityToSms(SmsEntity smsEntity);


  //List<ListSmsExcelResponse> listSmsExcelResponse(List<SmsEntity> smsEntity, List<String> createdBy, List<Integer> total, List<LocalDate> date, List<LocalTime> time);

  ListSmsExcelResponse listSmsExcelResponse(SmsEntity smsEntity, String createdBy, Integer total, LocalDate date, LocalTime time);

  List<Sms> toSms(List<SmsEntity> smsEntities);

  // 단일 SmsTargetEntity -> SmsTarget 변환
  SmsTarget toSmsTarget(SmsTargetEntity smsTargetEntity);

  // 리스트 형태의 변환
  List<SmsTarget> toSmsTargets(List<SmsTargetEntity> smsTargetEntities);

}
