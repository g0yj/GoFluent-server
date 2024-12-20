package com.lms.api.admin.service;

import com.lms.api.admin.service.dto.CreateSendEmail;
import com.lms.api.admin.service.dto.CreateSendLDFEmail;
import com.lms.api.admin.service.dto.SearchEmailUsers;
import com.lms.api.admin.service.dto.User;
import com.lms.api.client.email.EmailSenderService;
import com.lms.api.client.email.dto.CreateSendEmailRequest;
import com.lms.api.client.email.dto.CreateSendLDFEmailRequest;
import com.lms.api.common.code.YN;
import com.lms.api.common.entity.EmailEntity;
import com.lms.api.common.entity.LdfEntity;
import com.lms.api.common.entity.QUserEntity;
import com.lms.api.common.entity.UserEntity;
import com.lms.api.common.exception.LmsErrorCode;
import com.lms.api.common.exception.LmsException;
import com.lms.api.common.repository.EmailRepository;
import com.lms.api.common.repository.LdfRepository;
import com.lms.api.common.repository.UserRepository;
import com.lms.api.common.util.StringUtils;
import com.querydsl.core.types.dsl.BooleanExpression;
import jakarta.mail.MessagingException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EmailAdminService {

  private final UserRepository userRepository;
  private final EmailRepository emailRepository;
  private final EmailSenderService emailSenderService;
  private final EmailAdminServiceMapper emailAdminServiceMapper;
  private final LdfRepository ldfRepository;

  @Value("${lms.client.email.sender}")
  private String sender;

  public List<User> listUsers(SearchEmailUsers searchEmailUsers) {
    QUserEntity qUserEntity = QUserEntity.userEntity;

    BooleanExpression where = qUserEntity.isReceiveEmail.eq(YN.Y);

    if (searchEmailUsers.getType() != null) {
      where = where.and(qUserEntity.type.eq(searchEmailUsers.getType()));
    }

    if (StringUtils.hasAllText(searchEmailUsers.getSearch(), searchEmailUsers.getKeyword())) {
      where =
          switch (searchEmailUsers.getSearch()) {
            case "name" -> where.and(qUserEntity.name.contains(searchEmailUsers.getKeyword()));
            case "loginId" ->
                where.and(qUserEntity.loginId.contains(searchEmailUsers.getKeyword()));
            default -> where;
          };
    }

    Iterable<UserEntity> userEntities =
        userRepository.findAll(
            where, PageRequest.of(0, 100, Sort.Direction.valueOf("ASC"), "name"));

    return emailAdminServiceMapper.toUsers(userEntities);
  }

  @Transactional
  public void createSendEmail(CreateSendEmail createSendEmail) {
    createSendEmail
        .getRecipients()
        .forEach(
            recipient -> {
              CreateSendEmailRequest request =
                  CreateSendEmailRequest.builder()
                      .from(sender)
                      .to(recipient.getEmail())
                      .subject(createSendEmail.getTitle())
                      .content(createSendEmail.getContent())
                      .build();

              try {
                emailSenderService.sendEmail(request);

                EmailEntity emailEntity = new EmailEntity();
                emailEntity.setSenderName(createSendEmail.getSenderName());
                emailEntity.setSenderEmail(sender);
                emailEntity.setTitle(createSendEmail.getTitle());
                emailEntity.setContent(createSendEmail.getContent());
                emailEntity.setRecipientName(recipient.getName());
                emailEntity.setRecipientEmail(recipient.getEmail());
                emailEntity.setCreatedBy(createSendEmail.getCreatedBy());
                emailEntity.setLdfId(null);

                emailRepository.save(emailEntity);
              } catch (MessagingException e) {
                throw new LmsException(LmsErrorCode.EMAIL_ERROR);
              } catch (Exception e) {
                throw new LmsException(LmsErrorCode.INTERNAL_SERVER_ERROR);
              }
            });
  }

  @Transactional
  public void createSendLDFEmail(CreateSendLDFEmail createSendLDFEmail) {
    LdfEntity ldfEntity = ldfRepository.findById(createSendLDFEmail.getLdfId())
        .orElseThrow(() -> new LmsException(LmsErrorCode.LDF_NOT_FOUND)); 

    CreateSendLDFEmailRequest request =
        CreateSendLDFEmailRequest.builder()
            .from(sender)
            .to(createSendLDFEmail.getEmail())
            .name(createSendLDFEmail.getName())
            .lesson(createSendLDFEmail.getLesson())
            .lessonDate(createSendLDFEmail.getLessonDate())
            .teacher(createSendLDFEmail.getTeacher())
            .title(createSendLDFEmail.getTitle())
            .contentSp(createSendLDFEmail.getContentSp())
            .contentV(createSendLDFEmail.getContentV())
            .contentSg(createSendLDFEmail.getContentSg())
            .contentC(createSendLDFEmail.getContentC())
            .build();

    try {
      emailSenderService.sendLDFEmail(request);

      EmailEntity emailEntity = new EmailEntity();
      emailEntity.setLdfId(ldfEntity.getId());
      emailEntity.setSenderName(createSendLDFEmail.getSenderName());
      emailEntity.setSenderEmail(sender);
      emailEntity.setTitle(createSendLDFEmail.getTitle());
      emailEntity.setContent("");
      emailEntity.setRecipientName(createSendLDFEmail.getName());
      emailEntity.setRecipientEmail(createSendLDFEmail.getEmail());
      emailEntity.setCreatedBy(createSendLDFEmail.getCreatedBy());

      emailRepository.save(emailEntity);
    } catch (MessagingException e) {
      throw new LmsException(LmsErrorCode.EMAIL_ERROR);
    } catch (Exception e) {
      throw new LmsException(LmsErrorCode.INTERNAL_SERVER_ERROR);
    }
  }
}
