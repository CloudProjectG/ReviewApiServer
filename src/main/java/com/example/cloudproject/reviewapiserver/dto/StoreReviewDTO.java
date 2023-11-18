package com.example.cloudproject.reviewapiserver.dto;

import com.example.cloudproject.reviewapiserver.entity.Review;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@EqualsAndHashCode()
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreReviewDTO {
    private Byte grade;
    private UUID imageUuid;
    private String menu;
    private String comment;
    private List<Short> hashtags;

    public static StoreReviewDTO from(Review review) {
        return StoreReviewDTO.builder()
                .grade(review.getGrade())
                .imageUuid(review.getImageUuid())
                .menu(review.getMenu())
                .comment(review.getComment())
                .hashtags(review.getHashtagIdList())
                .build();
    }
}
