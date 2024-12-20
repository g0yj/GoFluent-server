package com.lms.api.client.sms;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ConfigurationProperties(prefix = "lms.client.sms")
public class SmsApiClientProperties {

  // 주소
  String url;
  // 서비스아이디
  String serviceId;
  // 서비스키
  String accessKey;
  // 시크릿키
  String secretKey;
  // 발신번호
  String sender;
}
