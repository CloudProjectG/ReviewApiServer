package com.example.cloudproject.reviewapiserver.dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreReviewResponseDTO {

    private Integer row;
    private Integer page;
    private List<StoreReviewDTO> reviews;

}
