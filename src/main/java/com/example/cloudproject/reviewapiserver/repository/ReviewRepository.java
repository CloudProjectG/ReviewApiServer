package com.example.cloudproject.reviewapiserver.repository;

import com.example.cloudproject.reviewapiserver.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Review.ReviewPK> {

    Page<Review> findAllByStoreIdAndIsHiddenFalse(Long storeId, Pageable pageable);
    Page<Review> findAllByUserId(Long userId, Pageable pageable);

}
