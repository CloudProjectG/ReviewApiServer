package com.example.cloudproject.reviewapiserver.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreReviewRequestDTO {

    private Long storeId;
    private Integer row;
    private Integer page;

}
