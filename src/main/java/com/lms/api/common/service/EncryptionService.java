package com.lms.api.common.service;

import com.lms.api.client.consul.EncryptionKeyService;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

//@Service
public class EncryptionService {

  private final EncryptionKeyService encryptionKeyService;
  private SecretKeySpec secretKey;

//  @Autowired
    public EncryptionService(EncryptionKeyService encryptionKeyService) {
        this.encryptionKeyService = encryptionKeyService;
        initializeSecretKey();
    }

    private void initializeSecretKey() {
        try {
            byte[] key = Base64.getDecoder().decode(encryptionKeyService.getEncryptionKey());
            this.secretKey = new SecretKeySpec(key, "AES");
        } catch (Exception e) {
            throw new RuntimeException("비밀 키 초기화 실패", e);
        }
    }

    
  public String encrypt(String data) {
    try {
      Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
      cipher.init(Cipher.ENCRYPT_MODE, secretKey);
      byte[] encryptedBytes = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
      return Base64.getEncoder().encodeToString(encryptedBytes);
    } catch (Exception e) {
      throw new RuntimeException("암호화 실패", e);
    }
  }

  public String decrypt(String encryptedData) {
    try {
      Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
      cipher.init(Cipher.DECRYPT_MODE, secretKey);
      byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
      return new String(decryptedBytes, StandardCharsets.UTF_8);
    } catch (Exception e) {
      throw new RuntimeException("복호화 실패", e);
    }
  }
}
