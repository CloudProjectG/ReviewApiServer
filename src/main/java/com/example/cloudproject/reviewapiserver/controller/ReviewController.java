package com.example.cloudproject.reviewapiserver.controller;

import com.example.cloudproject.reviewapiserver.dto.StoreReviewRequestDTO;
import com.example.cloudproject.reviewapiserver.dto.StoreReviewResponseDTO;
import com.example.cloudproject.reviewapiserver.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/{storeId}")
    public ResponseEntity<StoreReviewResponseDTO> getStoreReviews(@PathVariable Long storeId,
                                                                  @ModelAttribute StoreReviewRequestDTO storeReviewRequestDTO) {

        storeReviewRequestDTO.setStoreId(storeId);

        StoreReviewResponseDTO responseDTO = reviewService.getStoreReviews(storeReviewRequestDTO);

        return ResponseEntity.ok(responseDTO);
    }

}
