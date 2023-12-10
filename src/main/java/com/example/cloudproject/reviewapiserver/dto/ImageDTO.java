package com.example.cloudproject.reviewapiserver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public class ImageDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class DownloadRequest {
        private UUID imageUuid;

        public String getKey() {
            return imageUuid.toString();
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class DownloadResponse {
        private String contentType;
        private byte[] content;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UploadRequest {
        private UUID imageUuid;
        private MultipartFile image;

        public String getKey() {
            return imageUuid.toString();
        }
    }

    @Data
    @NoArgsConstructor
    @Builder
    public static class UploadResponse {

    }

}
