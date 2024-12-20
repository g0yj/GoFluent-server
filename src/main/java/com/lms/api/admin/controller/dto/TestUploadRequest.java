package com.lms.api.admin.controller.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TestUploadRequest {
    String id;
    MultipartFile file;
}
