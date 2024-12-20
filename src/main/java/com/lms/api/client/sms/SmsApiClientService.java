package com.lms.api.client.sms;

import com.lms.api.client.sms.dto.CreateSendNaverSmsRequest;
import com.lms.api.client.sms.dto.CreateSendNaverSmsResponse;
import com.lms.api.client.sms.dto.ListSmsSendRequestResponse;
import com.lms.api.client.sms.dto.ListSmsSendResultResponse;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class SmsApiClientService {

  private final SmsApiClientProperties smsApiClientProperties;
  private final WebClient smsWebClient;

  /**
   * 메시지 발송
   */
  public Mono<CreateSendNaverSmsResponse> send(CreateSendNaverSmsRequest request) {
    String url = "/sms/v2/services/{serviceId}/messages".replace("{serviceId}",
        smsApiClientProperties.getServiceId());

    return smsWebClient.post()
        .uri(url)
        .headers(setHeaders("POST", url))
        .bodyValue(request)
        .retrieve()
        .bodyToMono(CreateSendNaverSmsResponse.class);
  }

  /**
   * 메시지 발송 요청 조회
   */
  public Mono<ListSmsSendRequestResponse> listSendRequest(String requestId) {
    String url = "/sms/v2/services/{serviceId}/messages?requestId={requestId}"
        .replace("{serviceId}", smsApiClientProperties.getServiceId())
        .replace("{requestId}", requestId);

    return smsWebClient.get()
        .uri(url)
        .headers(setHeaders("GET", url))
        .retrieve()
        .bodyToMono(ListSmsSendRequestResponse.class);
  }

  /**
   * 메시지 발송 결과 조회
   */
  public Mono<ListSmsSendResultResponse> listSendResult(String messageId) {
    if (messageId == null) {
      throw new RuntimeException("messageId must not be null");
    }
    String url = "/sms/v2/services/{serviceId}/messages/{messageId}"
        .replace("{serviceId}", smsApiClientProperties.getServiceId())
        .replace("{messageId}", messageId);

    return smsWebClient.get()
        .uri(url)
        .headers(setHeaders("GET", url))
        .retrieve()
        .bodyToMono(ListSmsSendResultResponse.class);
  }

  private Consumer<HttpHeaders> setHeaders(String method, String url) {
    return headers -> {
      String timestamp = Long.toString(System.currentTimeMillis());

      headers.set("x-ncp-apigw-timestamp", timestamp);
      headers.set("x-ncp-apigw-signature-v2", makeSignature(method, url, timestamp));
    };
  }

  private String makeSignature(String method, String url, String timestamp) {
    try {
      String message =
          method + " " + url + "\n" + timestamp + "\n" + smsApiClientProperties.getAccessKey();
      SecretKeySpec signingKey = new SecretKeySpec(
          smsApiClientProperties.getSecretKey().getBytes(StandardCharsets.UTF_8), "HmacSHA256");

      Mac mac = Mac.getInstance("HmacSHA256");
      mac.init(signingKey);

      byte[] rawHmac = mac.doFinal(message.getBytes(StandardCharsets.UTF_8));

      return Base64.encodeBase64String(rawHmac);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
