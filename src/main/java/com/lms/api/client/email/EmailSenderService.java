package com.lms.api.client.email;

import com.lms.api.client.email.dto.CreateSendEmailRequest;
import com.lms.api.client.email.dto.CreateSendLDFEmailRequest;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailSenderService {

  private final JavaMailSender javaMailSender;
  private final TemplateEngine templateEngine;

  public void sendEmail(CreateSendEmailRequest request) throws MessagingException {
    MimeMessage mimeMessage = javaMailSender.createMimeMessage();

    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
    helper.setFrom(request.getFrom());
    helper.setTo(request.getTo());
    helper.setSubject(request.getSubject());
    helper.setText(request.getContent(), true);

    Context context = new Context();
    context.setVariable("subject", request.getSubject());
    context.setVariable("content", request.getContent());

    String htmlContent = templateEngine.process("basic_email", context);

    helper.setTo(request.getTo());
    helper.setSubject(request.getSubject());
    helper.setText(htmlContent, true);

    // 로고 이미지 첨부
    ClassPathResource logo = new ClassPathResource("/static/images/logo.png");
    helper.addInline("logo", logo);

    javaMailSender.send(mimeMessage);
  }

  public void sendLDFEmail(CreateSendLDFEmailRequest request) throws MessagingException {
    MimeMessage mimeMessage = javaMailSender.createMimeMessage();

    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
    helper.setFrom(request.getFrom());
    helper.setTo(request.getTo());
    helper.setSubject(request.getTitle());
    
    Context context = new Context();
    context.setVariable("name", request.getName());
    context.setVariable("lesson", request.getLesson());
    context.setVariable("date", request.getLessonDate());
    context.setVariable("teacher", request.getTeacher());
    context.setVariable("subject", request.getTitle());
    context.setVariable("contentSp", request.getContentSp());
    context.setVariable("contentV", request.getContentV());
    context.setVariable("contentSg", request.getContentSg());
    context.setVariable("contentC", request.getContentC());
    
    String htmlContent = templateEngine.process("ldf_email", context);
    
    helper.setTo(request.getTo());
    helper.setSubject(request.getTitle());
    helper.setText(htmlContent, true);

    // 로고 이미지 첨부
    ClassPathResource logo = new ClassPathResource("/static/images/logo.png");
    helper.addInline("logo", logo);

    javaMailSender.send(mimeMessage);
  }

  public void sendShutdownEmail() {
    SimpleMailMessage message = new SimpleMailMessage();
    String[] recipients = {"ilovecorea@gmail.com", "kowoon@kowoontech.com", "strikeshot0502@gmail.com"};
    message.setTo(recipients);
    message.setSubject("서버 종료 알림");
    message.setText("랭귀지큐브 API 서버가 다운 되었습니다!");

    javaMailSender.send(message);
  }
}
