package com.lms.api.admin.service;

import com.lms.api.client.sms.SmsApiClientProperties;
import com.lms.api.client.sms.SmsApiClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@Profile("!migration & !test")
public class SmsSchedulingService {

  private final SmsApiClientService smsApiClientService;
  private final SmsAdminService smsAdminService;
  private final SmsApiClientProperties smsApiClientProperties;

  @Value("${lms.templates.reservation}")
  private String reservationMessageTemplate;

  /**
   * sms 테이블 상태 업데이트
   */
  @Scheduled(cron = "${lms.scheduling.checkSmsWatting}")
  public void checkSmsStatus() {
    log.info("\n################## checkSmsStatus start ###################");
    smsAdminService.checkUpdateSms();
  }

//  @Scheduled(cron = "${lms.scheduling.checkSmsProccessing}")
//  public void checkSmsProccessingStatus() {
//    log.info("\n################## checkSmsProccessingStatus start ###################");
//    smsAdminService.updateProccessingStatus();
//  }

}


