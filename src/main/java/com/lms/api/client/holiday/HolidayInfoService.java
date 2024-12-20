package com.lms.api.client.holiday;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lms.api.client.holiday.dto.HolidayRequest;
import com.lms.api.client.holiday.dto.HolidayResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Service
public class HolidayInfoService {

  private final OkHttpClient httpClient;
  private final ObjectMapper objectMapper;

  @Value("${lms.client.holiday.service-key}")
  public String serviceKey;

  public HolidayInfoService(WebClient.Builder webClientBuilder) {
    this.httpClient = new OkHttpClient();
    this.objectMapper = new ObjectMapper();
  }

  public HolidayResponse getHolidayInfo(HolidayRequest request) throws IOException {
    // URI 빌더를 사용하여 URL을 생성합니다.
    HttpUrl.Builder urlBuilder = HttpUrl.parse("http://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/getHoliDeInfo")
        .newBuilder();
    urlBuilder.addQueryParameter("solYear", request.getSolYear());
    urlBuilder.addQueryParameter("solMonth", request.getSolMonth());
    urlBuilder.addQueryParameter("_type", "json");
    urlBuilder.addQueryParameter("numOfRows", String.valueOf(request.getNumOfRows()));
    urlBuilder.addQueryParameter("ServiceKey", serviceKey);

    // 완성된 URL
    String url = urlBuilder.build().toString();

    // 요청 객체 생성
    Request apiRequest = new Request.Builder()
        .url(url)
        .build();

    // 요청 실행
    try (Response response = httpClient.newCall(apiRequest).execute()) {
      if (response.isSuccessful() && response.body() != null) {
        // 응답을 String으로 받음
        String string = response.body().string();
        System.out.println("Response: " + string);

        return objectMapper.readValue(string, HolidayResponse.class);
      } else {
        throw new IOException("Unexpected code " + response);
      }
    }
  }

}
