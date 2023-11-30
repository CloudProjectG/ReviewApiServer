package com.example.cloudproject.reviewapiserver.controller;

import com.example.cloudproject.reviewapiserver.dto.StoreReviewDTO;
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
    public ResponseEntity<StoreReviewDTO.Response> getStoreReviews(@PathVariable Long storeId,
                                                                  @ModelAttribute StoreReviewDTO.Request requestDTO) {

        requestDTO.setStoreId(storeId);

        StoreReviewDTO.Response responseDTO = reviewService.getStoreReviews(requestDTO);

        return ResponseEntity.ok(responseDTO);
    }

}
