package com.example.cloudproject.reviewapiserver.dto;

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
}
