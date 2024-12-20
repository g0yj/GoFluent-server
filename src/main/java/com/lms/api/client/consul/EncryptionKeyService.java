package com.lms.api.client.consul;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import lombok.Data;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;

//@Service
public class EncryptionKeyService {

  private final OkHttpClient client;
  private final String keyUrl;
  private final ObjectMapper objectMapper;

  public EncryptionKeyService(@Value("${encryption.key-url}") String keyUrl) {
    this.client = new OkHttpClient();
    this.keyUrl = keyUrl;
    this.objectMapper = new ObjectMapper();
  }

  public String getEncryptionKey() {
    Request request = new Request.Builder().url(keyUrl).build();

    try (Response response = client.newCall(request).execute()) {
      if (!response.isSuccessful()) throw new IOException("예상치 못한 코드 " + response);

      String responseBody = response.body().string();
      KeyResponse[] keyResponses = objectMapper.readValue(responseBody, KeyResponse[].class);

      if (keyResponses.length > 0) {
        // String encodedValue = keyResponses[0].getValue();
        // return new String(Base64.getDecoder().decode(encodedValue));
        return keyResponses[0].getValue();
      }
    } catch (IOException e) {
      throw new RuntimeException("암호화 키를 가져오는 데 실패했습니다.", e);
    }

    throw new RuntimeException("암호화 키를 찾을 수 없습니다.");
  }

  @Data
  private static class KeyResponse {
    @JsonProperty("LockIndex")
    private int lockIndex;
    
    @JsonProperty("Key")
    private String key;
    
    @JsonProperty("Flags")
    private int flags;
    
    @JsonProperty("Value")
    private String value;
    
    @JsonProperty("CreateIndex")
    private long createIndex;
    
    @JsonProperty("ModifyIndex")
    private long modifyIndex;
  }
}
