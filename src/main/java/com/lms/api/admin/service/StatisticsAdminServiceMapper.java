package com.lms.api.admin.service;

import com.lms.api.admin.service.dto.Ldf;
import com.lms.api.admin.service.dto.User;
import com.lms.api.admin.service.dto.statistics.GetLdf;
import com.lms.api.admin.service.dto.statistics.GetSms;
import com.lms.api.admin.service.dto.statistics.ListLdf;
import com.lms.api.admin.service.dto.statistics.Sms;
import com.lms.api.admin.service.dto.statistics.SmsTarget;
import com.lms.api.common.entity.LdfEntity;
import com.lms.api.common.entity.SmsEntity;
import com.lms.api.common.entity.SmsTargetEntity;
import com.lms.api.common.entity.UserEntity;
import com.lms.api.common.mapper.ServiceMapper;
import com.lms.api.common.mapper.ServiceMapperConfig;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", config = ServiceMapperConfig.class, uses = {ServiceMapper.class})
public interface StatisticsAdminServiceMapper {

  Sms toSms(SmsEntity smsEntity);

  /**
   * 발송 내역 상세 조회
   */
  @Mapping(target = "target", source = "smsTargetEntities")
  GetSms toGetSms(SmsEntity smsEntity);

  /**
   * 대상자 조회 , 대기내역목록
   */
  SmsTarget toSmsTarget(SmsTargetEntity smsTargetEntity);

  /**
   * SMS 발송 대상자 목록
   */
  User toUser(UserEntity userEntity);

  /**
   * 평가 현황
   */
  @Mapping(target = "user", source = "userEntity")
  @Mapping(target = "reservation", source = "reservationEntity")
  Ldf toLdf(LdfEntity ldfEntity);

  ListLdf.Ldf toListLdf(LdfEntity ldfEntity);

  @Mapping(target = "user", source = "userEntity")
  @Mapping(target = "reservation", source = "reservationEntity")
  List<Ldf> toEvaluationLdf(List<LdfEntity> entities);

  /**
   * 강사 별 평가 상세 조회
   */
  @Mapping(target = "ldf", source = "ldfEntity")
  @Mapping(target = "reservation", source = "reservationEntity")
  @Mapping(target = "teacher", source = "reservationEntity.teacherEntity")
  @Mapping(target = "user", source = "userEntity")
  GetLdf toGetLdf(LdfEntity ldfEntity);


}
