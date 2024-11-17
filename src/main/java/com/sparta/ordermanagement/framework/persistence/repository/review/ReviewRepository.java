package com.sparta.ordermanagement.framework.persistence.repository.review;


import com.sparta.ordermanagement.framework.persistence.entity.review.ReviewEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {

    Optional<ReviewEntity> findByReviewUuid(String reviewUuid);

    @Query("SELECT r FROM p_review r WHERE r.reviewUuid = :reviewUuid AND r.shopEntity.shopUuid = :shopId AND r.isDeleted = false")
    Optional<ReviewEntity> findByReviewUuidAndShopIdAndIsDeletedFalse(@Param("reviewUuid") String reviewUuid, @Param("shopId") String shopId);
}
