package com.example.cloudproject.reviewapiserver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class ReviewDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UpdateHashtagAndGradeRequest {

        private Long storeId;
        private HashtagAndGradeInfo info;

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class HashtagAndGradeInfo {

        private List<Integer> hashtags;
        private Byte grade;
    }
}
