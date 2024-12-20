package com.lms.api.admin.service;

import com.lms.api.admin.service.dto.CommonCode;
import com.lms.api.admin.service.dto.CommonCodeGroup;
import com.lms.api.admin.service.dto.CreateCommonCode;
import com.lms.api.admin.service.dto.UpdateCommonCode;
import com.lms.api.common.entity.CommonCodeEntity;
import com.lms.api.common.entity.CommonCodeGroupEntity;
import com.lms.api.common.mapper.ServiceMapper;
import com.lms.api.common.mapper.ServiceMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", config = ServiceMapperConfig.class, uses = {ServiceMapper.class})
public interface CommonCodeServiceMapper {

  CommonCodeGroup toCommonCodeGroup(CommonCodeGroupEntity commonCodeGroupEntity);

  CommonCode toCommonCode(CommonCodeEntity commonCodeEntity);

  @Mapping(target = "createdBy", source = "createdBy")
  @Mapping(target = "useYn", source = "createCommonCode.useYn")
  CommonCodeEntity toCommonCodeEntity(CreateCommonCode createCommonCode);

  @Mapping(target = "code", ignore = true)
  @Mapping(target = "codeGroup", ignore = true)
  void mapCommonCodeEntity(UpdateCommonCode updateCommonCode, @MappingTarget CommonCodeEntity commonCodeEntity);
}
