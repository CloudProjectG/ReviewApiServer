package com.example.cloudproject.reviewapiserver.repository;

import com.example.cloudproject.reviewapiserver.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Review.ReviewPK> {

}
