package com.lms.api.admin.service;

import com.lms.api.admin.service.dto.Teacher;
import com.lms.api.common.entity.TeacherEntity;
import com.lms.api.common.mapper.ServiceMapper;
import com.lms.api.common.mapper.ServiceMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", config = ServiceMapperConfig.class, uses = {ServiceMapper.class})
public interface TeacherServiceMapper {

    @Mapping(target = "user" , source = "userEntity")
    Teacher toTeacher(TeacherEntity teacherEntity);


}
