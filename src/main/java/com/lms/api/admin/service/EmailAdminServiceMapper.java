package com.lms.api.admin.service;

import com.lms.api.admin.service.dto.User;
import com.lms.api.common.entity.UserEntity;
import com.lms.api.common.mapper.ServiceMapper;
import com.lms.api.common.mapper.ServiceMapperConfig;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", config = ServiceMapperConfig.class, uses = {ServiceMapper.class})
public interface EmailAdminServiceMapper {

  List<User> toUsers(Iterable<UserEntity> userEntities);
}
