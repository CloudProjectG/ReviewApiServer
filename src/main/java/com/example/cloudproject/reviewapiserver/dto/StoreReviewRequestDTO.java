package com.example.cloudproject.reviewapiserver.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreReviewRequestDTO {

    private static final Integer DEFAULT_ROW = 15;
    private static final Integer DEFAULT_PAGE = 0;

    private Long storeId;
    private Integer row = DEFAULT_ROW;
    private Integer page = DEFAULT_PAGE;

}
