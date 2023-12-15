package com.example.cloudproject.reviewapiserver.dto;

import lombok.*;

public class UserDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class AuthorizedRequest {
        private String token;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class AuthorizedResponse {
        private Long PK;
        private Boolean isKhu;
    }
}
