package com.lms.api.admin.service;

import com.lms.api.admin.service.dto.CommonCode;
import com.lms.api.admin.service.dto.CommonCodeGroup;
import com.lms.api.admin.service.dto.CreateCommonCode;
import com.lms.api.admin.service.dto.UpdateCommonCode;
import com.lms.api.common.entity.CodePK;
import com.lms.api.common.entity.CommonCodeEntity;
import com.lms.api.common.entity.CommonCodeGroupEntity;
import com.lms.api.common.entity.QCommonCodeEntity;
import com.lms.api.common.exception.LmsErrorCode;
import com.lms.api.common.exception.LmsException;
import com.lms.api.common.repository.CommonCodeGroupRepository;
import com.lms.api.common.repository.CommonCodeRepository;
import com.lms.api.common.util.StringUtils;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommonCodeService {

  private final CommonCodeGroupRepository commonCodeGroupRepository;
  private final CommonCodeRepository commonCodeRepository;
  private final CommonCodeServiceMapper commonCodeServiceMapper;

  /**
   * 코드그룹 목록 조회
   */
  public List<CommonCodeGroup> listCommonCodeGroups() {
    List<CommonCodeGroupEntity> commonCodeGroupEntities = commonCodeGroupRepository.findAll();

    return commonCodeGroupEntities.stream()
        .map(commonCodeGroupEntity -> commonCodeServiceMapper.toCommonCodeGroup(
            commonCodeGroupEntity)
        ).collect(Collectors.toList());
  }

  /**
   * 코드 목록 조회
   */
  public List<CommonCode> listCommonCodes(String codeGroup) {
    QCommonCodeEntity qCommonCodeEntity = QCommonCodeEntity.commonCodeEntity;
    BooleanExpression where = Expressions.TRUE;
    if (StringUtils.hasText(codeGroup)) {
      where = qCommonCodeEntity.codeGroup.eq(codeGroup);
    }
    Iterable<CommonCodeEntity> commonCodeEntities = commonCodeRepository.findAll(where);

    List<CommonCode> collect = StreamSupport.stream(commonCodeEntities.spliterator(), false)
        .map(commonCodeServiceMapper::toCommonCode)
        .collect(Collectors.toList());

    return collect;
  }

  /**
   * 공통코드 등록
   */
  @Transactional
  public void createCommonCode(CreateCommonCode createCommonCode) {

    switch (createCommonCode.getCodeGroup()) {
      case "100":
        createCommonCode.setCodeGroupName("상담구분");
        break;
      case "200":
        createCommonCode.setCodeGroupName("처리상태");
        break;
      case "300":
        createCommonCode.setCodeGroupName("카드종류");
        break;
      case "400":
        createCommonCode.setCodeGroupName("근무시간");
        break;
    }
    CreateCommonCode commonCode = CreateCommonCode.builder()
        .codeGroup(createCommonCode.getCodeGroup())
        .codeGroupName((createCommonCode.getCodeGroupName()))
        .code(createCommonCode.getCode())
        .name(createCommonCode.getName())
        .sort(createCommonCode.getSort())
        .useYn(createCommonCode.getUseYn())
        .createdBy(createCommonCode.getCreatedBy())
        .build();

    CommonCodeEntity commonCodeEntity = commonCodeServiceMapper.toCommonCodeEntity(commonCode);
    // 중복코드 체크
    if (commonCodeRepository.findById(
        new CodePK(createCommonCode.getCodeGroup(), createCommonCode.getCode())).isPresent()) {
      throw new LmsException(LmsErrorCode.COMMONCODE_CONFLICT);
    }
    commonCodeRepository.save(commonCodeEntity);
  }

  /**
   * 공통코드 수정
   */
  @Transactional
  public void updateCommonCode(UpdateCommonCode updateCommonCode) {

    commonCodeRepository.findByCode(updateCommonCode.getCode())
        .ifPresentOrElse(
            commonCodeEntity -> commonCodeServiceMapper.mapCommonCodeEntity(updateCommonCode,
                commonCodeEntity),
            () -> {
              throw new LmsException(LmsErrorCode.COMMONCODE_NOT_FOUND);
            }
        );
  }

  public List<CommonCode> codeList(String codeGroupId) {
    List<CommonCodeEntity> commonCodeEntities = commonCodeRepository.findByCodeGroupAndUseYnOrderBySortAsc(
        codeGroupId, "Y");
    List<CommonCode> codes = commonCodeEntities.stream()
        .map(commonCodeServiceMapper::toCommonCode
        ).collect(Collectors.toList());
    return codes;
  }


}
