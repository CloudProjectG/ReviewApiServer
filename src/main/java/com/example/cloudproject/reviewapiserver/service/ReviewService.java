package com.example.cloudproject.reviewapiserver.service;

import com.example.cloudproject.reviewapiserver.dto.StoreReviewDTO;
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

    public StoreReviewDTO.Response getStoreReviews(StoreReviewDTO.Request requestDTO) {
        Pageable pageable = PageRequest.of(
                requestDTO.getPage(),
                requestDTO.getRow(),
                Sort.by(Sort.Direction.DESC, "createdAt")
        );

        Page<StoreReviewDTO.Info> page = reviewRepository
                .findAllByStoreIdAndIsHiddenFalse(requestDTO.getStoreId(), pageable)
                .map(StoreReviewDTO.Info::from);

        return StoreReviewDTO.Response.builder()
                .page(requestDTO.getPage())
                .row(requestDTO.getRow())
                .reviews(page.getContent())
                .build();
    }

}
