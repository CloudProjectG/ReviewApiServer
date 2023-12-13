package com.example.cloudproject.reviewapiserver.service;

import com.example.cloudproject.reviewapiserver.dto.*;
import com.example.cloudproject.reviewapiserver.entity.Review;
import com.example.cloudproject.reviewapiserver.exception.ReviewException;
import com.example.cloudproject.reviewapiserver.exception.type.ReviewExceptionType;
import com.example.cloudproject.reviewapiserver.repository.ReviewRepository;
import com.example.cloudproject.reviewapiserver.util.WebClientUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ImageService imageService;
    private final WebClientUtil webClientUtil;

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

    public ReviewDTO.CreateResponse createReview(ReviewDTO.CreateRequest requestDTO) {
        Review review = requestDTO.toEntity();

        if (reviewRepository.existsById(review.getPK())) {
            throw new ReviewException(ReviewExceptionType.ONLY_ONE_REVIEW_PER_STORE);
        }

        try {
            reviewRepository.saveAndFlush(review);
        } catch (DataAccessException dae) {
            throw new ReviewException(ReviewExceptionType.REVIEW_SAVE_FAILURE);
        }

        updateTop3HashtagAndAverageGrade(requestDTO.getStoreId());

        return ReviewDTO.CreateResponse.from(review);
    }

    public ReviewDTO.UpdateResponse updateReview(ReviewDTO.UpdateRequest requestDTO) {
        Review review = requestDTO.toEntity();
        Review.ReviewPK reviewPK = review.getPK();

        UUID beforeImageUuid = reviewRepository.getReferenceById(reviewPK).getImageUuid();

        try {
            reviewRepository.saveAndFlush(review);
        } catch (DataAccessException dae) {
            throw new ReviewException(ReviewExceptionType.REVIEW_SAVE_FAILURE);
        }

        if (requestDTO.getDoChangeImage()) {
            imageService.deleteImage(beforeImageUuid);
        }

        updateTop3HashtagAndAverageGrade(requestDTO.getStoreId());

        return ReviewDTO.UpdateResponse.from(review);
    }

    public ReviewDTO.RemoveResponse removeReview(ReviewDTO.RemoveRequest requestDTO) {
        Review.ReviewPK reviewPK = Review.ReviewPK.builder()
                .storeId(requestDTO.getStoreId())
                .userId(requestDTO.getUserId())
                .build();

        Review review = reviewRepository.findById(reviewPK)
                        .orElseThrow(() -> new ReviewException(ReviewExceptionType.REVIEW_NOT_FOUND));

        reviewRepository.deleteById(reviewPK);

        if (review.getImageUuid() != null) {
            imageService.deleteImage(review.getImageUuid());
        }

        updateTop3HashtagAndAverageGrade(requestDTO.getStoreId());

        return ReviewDTO.RemoveResponse.from(review);
    }

    public MyReviewDTO.Response getMyReviews(MyReviewDTO.Request requestDTO) {
        Pageable pageable = PageRequest.of(
                requestDTO.getPage(),
                requestDTO.getRow(),
                Sort.by(Sort.Direction.DESC, "createdAt")
        );

        List<Review> reviewList = reviewRepository.findAllByUserId(requestDTO.getUserId(), pageable)
                .getContent();

        List<Long> storeIdList = reviewList.stream()
                .map(Review::getStoreId)
                .toList();

        Map<Long, String> storeIdNameMap = getStoreIdNameMap(storeIdList);

        List<MyReviewDTO.Info> myReviewList = reviewList.stream()
                .map(review -> MyReviewDTO.Info.from(review, storeIdNameMap.get(review.getStoreId())))
                .toList();

        return MyReviewDTO.Response.builder()
                .row(requestDTO.getRow())
                .page(requestDTO.getPage())
                .reviews(myReviewList)
                .build();
    }

    public RecentReviewDTO.Response getRecentReview(RecentReviewDTO.Request requestDTO) {
        Pageable pageable = PageRequest.of(
                requestDTO.getPage(),
                requestDTO.getRow(),
                Sort.by(Sort.Direction.DESC, "createdAt")
        );

        List<Review> reviewList = reviewRepository.findAll(pageable)
                .getContent();

        List<Long> storeIdList = reviewList.stream()
                .map(Review::getStoreId)
                .toList();

        Map<Long, String> storeIdNameMap = getStoreIdNameMap(storeIdList);

        List<RecentReviewDTO.Info> recentReviewList = reviewList.stream()
                .map(review -> RecentReviewDTO.Info.from(review, storeIdNameMap.get(review.getStoreId())))
                .toList();

        return RecentReviewDTO.Response.builder()
                .row(requestDTO.getRow())
                .page(requestDTO.getPage())
                .reviews(recentReviewList)
                .build();
    }
    private void updateTop3HashtagAndAverageGrade(Long storeId) {
        List<Review> reviewList = reviewRepository.findAllByStoreId(storeId);

        List<Integer> top3HashtagList = reviewList.stream()
                .flatMap(review -> review.getHashtagIdList().stream())
                .collect(Collectors.groupingBy(hashtag -> hashtag, Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.<Integer, Long>comparingByValue().reversed())
                .limit(3)
                .map(Map.Entry::getKey)
                .toList();

        Byte averageGrade = reviewList.stream()
                .map(Review::getGrade)
                .collect(Collectors.averagingInt(grade -> grade))
                .byteValue();

        ReviewDTO.HashtagAndGradeInfo infoDTO = ReviewDTO.HashtagAndGradeInfo.builder()
                .hashtags(top3HashtagList)
                .grade(averageGrade)
                .build();

        ReviewDTO.UpdateHashtagAndGradeRequest requestDTO = ReviewDTO.UpdateHashtagAndGradeRequest.builder()
                .storeId(storeId)
                .info(infoDTO)
                .build();

        webClientUtil.patchHashtagAndAverageGradeToStore(requestDTO);
    }

    private Map<Long, String> getStoreIdNameMap(List<Long> storeIdList) {
        if (storeIdList.isEmpty()) {
            return Collections.emptyMap();
        }

        StoreNameDTO.Request storeRequestDTO = StoreNameDTO.Request.builder()
                .storeIdList(storeIdList)
                .build();

        return webClientUtil.getStoreNameList(storeRequestDTO)
                .getResult().stream()
                .collect(Collectors.toMap(StoreNameDTO.Info::getId, StoreNameDTO.Info::getName));
    }

}
