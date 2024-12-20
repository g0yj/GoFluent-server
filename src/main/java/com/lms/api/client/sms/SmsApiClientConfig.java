package com.lms.api.client.sms;

import io.netty.handler.logging.LogLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.logging.AdvancedByteBufFormat;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class SmsApiClientConfig {

  private final SmsApiClientProperties smsApiClientProperties;
  private final WebClient.Builder webClientBuilder;

  @Bean
  public WebClient smsWebClient() {
    HttpClient httpClient = HttpClient.create()
        .wiretap("reactor.netty.http.client.HttpClient", LogLevel.DEBUG,
            AdvancedByteBufFormat.TEXTUAL);

    return webClientBuilder.baseUrl(smsApiClientProperties.getUrl())
        .clientConnector(new ReactorClientHttpConnector(httpClient))
        .defaultHeader("Content-Type", "application/json")
        .defaultHeader("x-ncp-iam-access-key", smsApiClientProperties.getAccessKey())
        .build();
  }
}
