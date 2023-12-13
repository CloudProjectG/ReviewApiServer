package com.example.cloudproject.reviewapiserver.dto;

import com.example.cloudproject.reviewapiserver.entity.Review;
import lombok.*;

import java.util.List;
import java.util.UUID;

public class RecentReviewDTO {

    @Getter
    @EqualsAndHashCode()
    @AllArgsConstructor
    @Builder
    public static class Info {

        private Long storeId;
        private String storeName;
        private Byte grade;
        private UUID image;
        private String menu;
        private String comment;
        private List<Integer> hashtags;

        public static Info from(Review review, String storeName) {
            return Info.builder()
                    .storeId(review.getStoreId())
                    .storeName(storeName)
                    .grade(review.getGrade())
                    .image(review.getImageUuid())
                    .menu(review.getMenu())
                    .comment(review.getComment())
                    .hashtags(review.getHashtagIdList())
                    .build();
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {

        private static final Integer DEFAULT_ROW = 15;
        private static final Integer DEFAULT_PAGE = 0;

        private Integer row = DEFAULT_ROW;
        private Integer page = DEFAULT_PAGE;

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {

        private Integer row;
        private Integer page;
        private List<RecentReviewDTO.Info> reviews;

    }
}
