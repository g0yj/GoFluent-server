package com.lms.api.admin.service;

import com.lms.api.admin.service.dto.Schedule;
import com.lms.api.admin.service.dto.Teacher;
import com.lms.api.admin.service.dto.TeacherFile;
import com.lms.api.admin.service.dto.User;
import com.lms.api.admin.service.dto.teacher.CreateTeacher;
import com.lms.api.admin.service.dto.teacher.GetTeacher;
import com.lms.api.admin.service.dto.teacher.MUpdateTeacher;
import com.lms.api.admin.service.dto.teacher.SearchTeacherResponse;
import com.lms.api.admin.service.dto.teacher.UpdateTeacher;
import com.lms.api.common.code.WorkTime;
import com.lms.api.common.entity.ScheduleEntity;
import com.lms.api.common.entity.TeacherEntity;
import com.lms.api.common.entity.UserEntity;
import com.lms.api.common.mapper.ServiceMapper;
import com.lms.api.common.mapper.ServiceMapperConfig;
import java.util.List;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", config = ServiceMapperConfig.class, uses = {ServiceMapper.class})
public interface TeacherAdminServiceMapper {

  List<Teacher> toTeachers(List<TeacherEntity> teacherEntities);

  @Mapping(target = "user", source = "userEntity")
  Teacher toTeacher(TeacherEntity teacherEntity);

  List<Schedule> toSchedules(List<ScheduleEntity> scheduleEntities);

  @Mapping(target = "user", source = "userEntity")
  @Mapping(target = "email", source = "teacherEntity.userEntity.email")
  @Mapping(target = "sort", source = "teacherEntity.sort")
  SearchTeacherResponse toSearchTeacherResponse(TeacherEntity teacherEntity);

  GetTeacher toGetTeacher(TeacherEntity teacherEntity, List<TeacherFile> teacherFiles);

  /**
   * 등록
   */
  TeacherEntity toTeacherEntity(CreateTeacher createTeacher);


  /**
   * 수정
   */
  @Mapping(target = "userId", ignore = true)
  @Mapping(target = "type", source = "teacherType")
  void mapTeacherEntity(UpdateTeacher updateTeacher, @MappingTarget TeacherEntity teacherEntity);


  @Mapping(target = "id", ignore = true)
  void mapUserEntity(MUpdateTeacher updateTeacher, @MappingTarget UserEntity userEntity);

  @AfterMapping
  default void mapUserToTeacher(MUpdateTeacher updateTeacher,
      @MappingTarget TeacherEntity teacherEntity) {
    if (teacherEntity.getUserEntity() != null) {
      mapUserEntity(updateTeacher, teacherEntity.getUserEntity());
    }
  }

  /** 삭제*/
  @Mapping(target = "userId", ignore = true)
  void mapTeacherEntity(String id, @MappingTarget TeacherEntity teacherEntity);

  User toUser(UserEntity userEntity);

}
