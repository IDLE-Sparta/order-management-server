package com.sparta.ordermanagement.framework.persistence.repository;


import com.sparta.ordermanagement.framework.persistence.entity.review.ReviewEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {

    Optional<ReviewEntity> findByReviewUuid(String reviewUuid);
}
