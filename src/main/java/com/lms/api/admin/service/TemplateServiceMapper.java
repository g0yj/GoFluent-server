package com.lms.api.admin.service;

import com.lms.api.admin.service.dto.Course;
import com.lms.api.admin.service.dto.Reservation;
import com.lms.api.admin.service.dto.Teacher;
import com.lms.api.admin.service.dto.User;
import com.lms.api.admin.service.dto.template.CreateTemplate;
import com.lms.api.admin.service.dto.template.MemberRegisterTemplate;
import com.lms.api.admin.service.dto.template.SimpleTemplate;
import com.lms.api.admin.service.dto.template.UpdateTemplate;
import com.lms.api.common.entity.CourseEntity;
import com.lms.api.common.entity.ReservationEntity;
import com.lms.api.common.entity.TeacherEntity;
import com.lms.api.common.entity.TemplateEntity;
import com.lms.api.common.entity.UserEntity;
import com.lms.api.common.mapper.ServiceMapper;
import com.lms.api.common.mapper.ServiceMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", config = ServiceMapperConfig.class, uses = {ServiceMapper.class})
public interface TemplateServiceMapper {

  /** 단순 템플릿 목록 조회 (페이징처리 x) */
  SimpleTemplate toSimpleTemplate(TemplateEntity templateEntity);

  /** 템플릿 조회 */
  MemberRegisterTemplate toMemberRegisterTemplate(TemplateEntity templateEntity);

  /** 템플릿 등록 */
  TemplateEntity toTemplateEntity(CreateTemplate createTemplate);

  /** 템플릿 수정 */
  @Mapping(target = "id", ignore = true)
  void mapTemplateEntity (UpdateTemplate updateTemplate,
      @MappingTarget TemplateEntity templateEntity);


  User toUser(UserEntity userEntity);

  Course toCourse(CourseEntity courseEntity);

  @Mapping(target = "user", source = "userEntity" , ignore = true )
  @Mapping(target = "course", source = "courseEntity")
  Reservation toReservation(ReservationEntity reservationEntity);

  @Mapping(target = "user", source = "userEntity")
  Teacher toTeacher(TeacherEntity teacherEntity);


}
