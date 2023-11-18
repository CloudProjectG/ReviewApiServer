package com.example.cloudproject.reviewapiserver.service;

import com.example.cloudproject.reviewapiserver.dto.StoreReviewDTO;
import com.example.cloudproject.reviewapiserver.dto.StoreReviewRequestDTO;
import com.example.cloudproject.reviewapiserver.dto.StoreReviewResponseDTO;
import com.example.cloudproject.reviewapiserver.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public StoreReviewResponseDTO getStoreReviews(StoreReviewRequestDTO storeReviewRequestDTO) {
        Pageable pageable = PageRequest.of(
                storeReviewRequestDTO.getPage(),
                storeReviewRequestDTO.getRow(),
                Sort.by(Sort.Direction.DESC, "createdAt")
        );

        Page<StoreReviewDTO> page = reviewRepository
                .findAllByStoreIdAndIsHiddenFalse(storeReviewRequestDTO.getStoreId(), pageable)
                .map(StoreReviewDTO::from);

        return StoreReviewResponseDTO.builder()
                .page(storeReviewRequestDTO.getPage())
                .row(storeReviewRequestDTO.getRow())
                .reviews(page.getContent())
                .build();
    }

}
