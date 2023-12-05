package com.example.cloudproject.reviewapiserver.controller;

import com.example.cloudproject.reviewapiserver.dto.ReviewDTO;
import com.example.cloudproject.reviewapiserver.dto.StoreReviewDTO;
import com.example.cloudproject.reviewapiserver.exception.AuthException;
import com.example.cloudproject.reviewapiserver.exception.type.AuthExceptionType;
import com.example.cloudproject.reviewapiserver.service.ReviewService;
import jakarta.servlet.http.HttpServletRequest;
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
    public ResponseEntity<StoreReviewDTO.Response> getStoreReviews(@PathVariable("storeId") Long storeId,
                                                                  @ModelAttribute StoreReviewDTO.Request requestDTO) {

        requestDTO.setStoreId(storeId);

        StoreReviewDTO.Response responseDTO = reviewService.getStoreReviews(requestDTO);

        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("{storeId}")
    public ResponseEntity<ReviewDTO.CreateResponse> postReview(@PathVariable("storeId") Long storeId,
                                                               @ModelAttribute ReviewDTO.CreateRequest requestDTO,
                                                               HttpServletRequest servletRequest) {

        Long userId = (Long) servletRequest.getAttribute("userId");
        if (userId == -1) {
            throw new AuthException(AuthExceptionType.UNAUTHORIZED_TOKEN);
        }

        requestDTO.setStoreId(storeId);
        requestDTO.setUserId(userId);

        return ResponseEntity.ok(reviewService.createReview(requestDTO));
    }

}
