package com.lms.api.common.service;

import com.lms.api.client.email.EmailSenderService;
import com.lms.api.client.sms.SmsApiClientService;
import com.lms.api.client.sms.dto.CreateSendNaverSmsRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Component
@Profile("prod")
public class ShutdownListener implements ApplicationListener<ContextClosedEvent> {

  private final EmailSenderService emailService;
  private final SmsApiClientService smsApiClientService;

  @Override
  public void onApplicationEvent(ContextClosedEvent event) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    String now = LocalDateTime.now().format(formatter);
    // 서버 종료 시 실행할 작업
    CreateSendNaverSmsRequest request = CreateSendNaverSmsRequest.builder()
        .from("02-2082-1105")
        .content("랭귀지큐브 API 서버가 다운 되었습니다[" + now + "]")
        .messages(List.of(
            CreateSendNaverSmsRequest.Message.builder()
                .to("010-3320-4796")
                .build(),
            CreateSendNaverSmsRequest.Message.builder()
                .to("010-6548-1777")
                .build()
        ))
        .reserveTime(null)
        .build();

    smsApiClientService.send(request)
        .doOnError(e -> log.error("발송 에러", e))
        .onErrorResume(e -> Mono.error(new RuntimeException("Failed to send SMS.", e)))
        .block();

//    emailService.sendShutdownEmail();
  }
}