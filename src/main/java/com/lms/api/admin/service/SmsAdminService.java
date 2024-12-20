package com.lms.api.admin.service;

import com.lms.api.admin.controller.dto.CreateSendSmsResponse;
import com.lms.api.admin.service.dto.CreateSendSms;
import com.lms.api.admin.service.dto.SearchSmsUsers;
import com.lms.api.admin.service.dto.User;
import com.lms.api.admin.service.dto.statistics.Sms;
import com.lms.api.client.sms.SmsApiClientProperties;
import com.lms.api.client.sms.SmsApiClientService;
import com.lms.api.client.sms.dto.CreateSendNaverSmsRequest;
import com.lms.api.client.sms.dto.CreateSendNaverSmsResponse;
import com.lms.api.client.sms.dto.ListSmsSendRequestResponse;
import com.lms.api.client.sms.dto.ListSmsSendRequestResponse.Message;
import com.lms.api.client.sms.dto.SmsType;
import com.lms.api.common.code.SmsStatus;
import com.lms.api.common.code.YN;
import com.lms.api.common.entity.QUserEntity;
import com.lms.api.common.entity.SmsEntity;
import com.lms.api.common.entity.SmsTargetEntity;
import com.lms.api.common.entity.UserEntity;
import com.lms.api.common.exception.LmsErrorCode;
import com.lms.api.common.exception.LmsException;
import com.lms.api.common.repository.SmsRepository;
import com.lms.api.common.repository.SmsTargetRepository;
import com.lms.api.common.repository.UserRepository;
import com.lms.api.common.util.MessageUtils;
import com.lms.api.common.util.StringUtils;
import com.querydsl.core.types.dsl.BooleanExpression;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
//@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class SmsAdminService {

  private final UserRepository userRepository;
  private final SmsRepository smsRepository;
  private final SmsApiClientProperties smsApiClientProperties;
  private final SmsApiClientService smsApiClientService;
  private final SmsAdminServiceMapper smsAdminServiceMapper;
  private final SmsTargetRepository smsTargetRepository;

  public List<User> listUsers(SearchSmsUsers searchSmsUsers) {
    QUserEntity qUserEntity = QUserEntity.userEntity;

    BooleanExpression where = qUserEntity.isReceiveSms.eq(YN.Y);

    if (searchSmsUsers.getType() != null) {
      where = where.and(qUserEntity.type.eq(searchSmsUsers.getType()));
    }

    if (StringUtils.hasAllText(searchSmsUsers.getSearch(), searchSmsUsers.getKeyword())) {
      where = switch (searchSmsUsers.getSearch()) {
        case "name" -> where.and(qUserEntity.name.contains(searchSmsUsers.getKeyword()));
        case "id" -> where.and(qUserEntity.id.contains(searchSmsUsers.getKeyword()));
        default -> where;
      };
    }

    Iterable<UserEntity> userEntities = userRepository.findAll(where,
        PageRequest.of(0, 100, Sort.Direction.valueOf("ASC"), "name"));

    return smsAdminServiceMapper.toUsers(userEntities);
  }

  @Transactional
  public CreateSendSmsResponse createSendSms(CreateSendSms createSendSms) {
    LocalDateTime now = LocalDateTime.now();

    // SMS 타입 지정
    SmsType type = MessageUtils.getMessageType(createSendSms.getContent());
    if (type == SmsType.MMS) {
      throw new LmsException(LmsErrorCode.BAD_REQUEST, "MMS(2000바이트 이상)는 지원하지 않습니다.");
    }

    // 현재 시간보다 10분 이상 미래인지 확인
    if (createSendSms.getReservationDate() != null) {
      boolean isMoreThan10MinutesInFuture = createSendSms.getReservationDate()
          .isAfter(now.plusMinutes(11));

      if (!isMoreThan10MinutesInFuture) {
        throw new LmsException(LmsErrorCode.BAD_REQUEST,
            "문자 메시지는 예약 시간을 현재 시각 기준으로 최소 11분 이후로 설정해야 합니다");
      }
    }

    CreateSendNaverSmsResponse response = getCreateSendNaverSmsResponse(
        createSendSms, type);

    if (response != null && response.isSuccess()) {
      SmsEntity smsEntity = new SmsEntity();
      smsEntity.setSenderPhone(smsApiClientProperties.getSender());
      smsEntity.setSenderName(createSendSms.getSenderName());
      smsEntity.setContent(createSendSms.getContent());
      smsEntity.setReservationDate(createSendSms.getReservationDate());
      smsEntity.setStatus(SmsStatus.WAITING);
      smsEntity.setSendDate(now);
      smsEntity.setRequestId(response.getRequestId());
      smsEntity.setSendType(createSendSms.getSendType());
      smsEntity.setCreatedBy(createSendSms.getCreatedBy());
      smsEntity.setModifiedBy(createSendSms.getCreatedBy());

      List<SmsTargetEntity> smsTargetEntities = createSendSms.getRecipients().stream()
          .map(recipient -> {

            SmsTargetEntity smsTargetEntity = new SmsTargetEntity();
            smsTargetEntity.setSmsEntity(smsEntity);
            smsTargetEntity.setRecipientPhone(recipient.getPhone());
            smsTargetEntity.setRecipientName(recipient.getName());
            smsTargetEntity.setEmail(recipient.getEmail());
            smsTargetEntity.setStatus(SmsStatus.WAITING);
            smsTargetEntity.setSendDate(now);
            smsTargetEntity.setCreatedBy(createSendSms.getCreatedBy());
            smsTargetEntity.setModifiedBy(createSendSms.getCreatedBy());

            return smsTargetEntity;
          }).toList();

      smsEntity.setSmsTargetEntities(smsTargetEntities);

      smsRepository.save(smsEntity);
    }

    return new CreateSendSmsResponse(response.getStatusCode(), response.getRequestId());
  }

  @Nullable
  private CreateSendNaverSmsResponse getCreateSendNaverSmsResponse(CreateSendSms createSendSms,
      SmsType type) {
    // sms 전송 요청 생성
    CreateSendNaverSmsRequest request = CreateSendNaverSmsRequest.builder()
        .from(smsApiClientProperties.getSender())
        .content(createSendSms.getContent())
        .messages(createSendSms.getRecipients().stream().map(receiver ->
            CreateSendNaverSmsRequest.Message.builder().to(receiver.getPhone()).build()).toList())
        .reserveTime(createSendSms.getFormattedReservationDate())
        .type(type)
        .build();

    // sms 전송 요청
    CreateSendNaverSmsResponse response = smsApiClientService.send(request)
        .doOnNext(res -> log.info("SMS request accepted for processing: {}", res))
        .doOnError(e -> log.error("Error occurred while sending SMS", e))
        .onErrorResume(e -> Mono.error(new RuntimeException("Failed to send SMS.", e)))
        .block();
    return response;
  }

  @Transactional
  public List<Sms> listWaitingSms() {
    List<SmsEntity> smsEntities = smsRepository.findAllByStatusAndRequestIdIsNotNull(
        SmsStatus.WAITING);

    return smsAdminServiceMapper.toSms(smsEntities);
  }

  public List<Sms> listSuccessSms() {
    List<SmsEntity> smsEntities = smsRepository.findAllByStatusAndRequestIdIsNotNull(
        SmsStatus.SUCCESS);

    return smsAdminServiceMapper.toSms(smsEntities);
  }

  @Transactional(readOnly = false)
  public void checkUpdateSms() {
    // 대기 조회
    List<Sms> smsList = listWaitingSms();
    log.info("## smsList:{}", smsList);

    for (Sms sms : smsList) {
      try {
        // 문자 발송 조회
        ListSmsSendRequestResponse response = smsApiClientService.listSendRequest(
                sms.getRequestId())
            .doOnError(throwable -> log.error("문자 발송 조회 에러", throwable))
            .onErrorMap(throwable -> new RuntimeException(throwable))
            .block();

        if (response.getStatusCode().equals(ListSmsSendRequestResponse.SUCCESS_STATUS_CODE)) {
          updateSms(sms, response);
        } else {
          throw new RuntimeException("listSendRequest response error:" + response.getStatusCode());
        }
      } catch (Exception e) {
        log.error("checkUpdateSmsStatus Error", e);
      }
    }
  }

  @Transactional(readOnly = false)
  public void updateSms(Sms sms, ListSmsSendRequestResponse response) {
    final LocalDateTime now = LocalDateTime.now();
    if (sms.getStatus() == SmsStatus.WAITING && response.getMessages() != null) {
      SmsEntity smsEntity = smsRepository.findById(sms.getId()).get();
      for (SmsTargetEntity smsTarget : smsEntity.getSmsTargetEntities()) {
        // set messageId
        if (smsTarget.getMessageId() == null) {
          String messageId = response.getMessages().stream()
              .filter(message -> smsTarget.getRecipientPhone().replaceAll("-", "")
                  .equals(message.getTo()))
              .findFirst()
              .map(message -> message.getMessageId())
              .orElse(null);
          if (messageId != null) {
            log.debug("## SmsTarget.messageId Update({}->{})", smsTarget.getMessageId(), messageId);
            smsTarget.setMessageId(messageId);
            smsTarget.setModifiedOn(now);
          }
        }
        // set status
        if (smsTarget.getStatus() == SmsStatus.WAITING) {
          Optional<Message> findMessage = response.getMessages().stream()
              .filter(message -> smsTarget.getRecipientPhone().replaceAll("-", "")
                  .equals(message.getTo()))
              .findFirst();
          SmsStatus smsStatus = SmsStatus.WAITING;
          if (findMessage.isPresent() && findMessage.get().isSuccess()) {
            smsStatus = SmsStatus.SUCCESS;
          }
          if (findMessage.isPresent() && findMessage.get().isFailed()) {
            smsStatus = SmsStatus.FAIL;
          }
          if (smsStatus != SmsStatus.WAITING) {
            log.debug("## SmsTarget.status Update({}->{})", smsTarget.getStatus(), smsStatus);
            smsTarget.setStatus(smsStatus);
            smsTarget.setModifiedOn(now);
            smsTargetRepository.saveAndFlush(smsTarget);
          }
        }
      }

      SmsStatus status = response.isSuccess()
          ? SmsStatus.SUCCESS
          : (response.isFail() ? SmsStatus.FAIL : SmsStatus.WAITING);
      if (status != SmsStatus.WAITING) {
        log.debug("## Sms.status Update({}->{})", smsEntity.getStatus(), status);
        smsEntity.setStatus(status);
        smsEntity.setModifiedOn(now);
        smsRepository.saveAndFlush(smsEntity);
      }
    }
  }

  /*@Transactional(readOnly = false)
  public void updateSms(Sms sms, ListSmsSendRequestResponse response) {
    final LocalDateTime now = LocalDateTime.now();
    if (sms.getStatus() == SmsStatus.WAITING && response.getMessages() != null) {
      SmsEntity smsEntity = smsRepository.findById(sms.getId()).get();

      // Iterate over smsTargetEntities
      for (SmsTargetEntity smsTarget : smsEntity.getSmsTargetEntities()) {

        // Update messageId if not set
        if (smsTarget.getMessageId() == null) {
          String messageId = response.getMessages().stream()
              .filter(message -> smsTarget.getRecipientPhone().replaceAll("-", "")
                  .equals(message.getTo()))
              .findFirst()
              .map(message -> message.getMessageId())
              .orElse(null);

          if (messageId != null) {
            log.debug("## SmsTarget.messageId Update({}->{})", smsTarget.getMessageId(), messageId);
            smsTargetRepository.updateMessageIdAndModifiedOn(messageId, now, smsTarget.getId());  // Native Query 사용
          }
        }

        // Update status if it is WAITING
        if (smsTarget.getStatus() == SmsStatus.WAITING) {
          Optional<Message> findMessage = response.getMessages().stream()
              .filter(message -> smsTarget.getRecipientPhone().replaceAll("-", "")
                  .equals(message.getTo()))
              .findFirst();
          SmsStatus smsStatus = SmsStatus.WAITING;
          if (findMessage.isPresent() && findMessage.get().isSuccess()) {
            smsStatus = SmsStatus.SUCCESS;
          }
          if (findMessage.isPresent() && findMessage.get().isFailed()) {
            smsStatus = SmsStatus.FAIL;
          }
          if (smsStatus != SmsStatus.WAITING) {
            log.debug("## SmsTarget.status Update({}->{})", smsTarget.getStatus(), smsStatus);
            smsTargetRepository.updateStatusAndModifiedOn(smsStatus, now, smsTarget.getId());  // Native Query 사용
          }
        }
      }

      log.info("## Update before smsEntity {}", smsEntity);
      SmsStatus status = response.isSuccess()
          ? SmsStatus.SUCCESS
          : (response.isFail() ? SmsStatus.FAIL : SmsStatus.WAITING);
      if (status != SmsStatus.WAITING) {
        smsRepository.updateStatusAndModifiedOn(status, now, smsEntity.getId());  // Native Query 사용
        log.info("## Updating smsEntity {}", smsEntity);
      }
    }
  }*/

}


