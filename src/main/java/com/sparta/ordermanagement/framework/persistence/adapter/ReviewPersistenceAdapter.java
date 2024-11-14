package com.sparta.ordermanagement.framework.persistence.adapter;

import com.sparta.ordermanagement.application.domain.review.Review;
import com.sparta.ordermanagement.application.domain.review.ReviewForCreate;
import com.sparta.ordermanagement.application.domain.review.ReviewForDelete;
import com.sparta.ordermanagement.application.domain.review.ReviewForUpdate;
import com.sparta.ordermanagement.application.exception.shop.ShopUuidInvalidException;
import com.sparta.ordermanagement.application.output.ReviewOutputPort;
import com.sparta.ordermanagement.framework.persistence.entity.review.ReviewEntity;
import com.sparta.ordermanagement.framework.persistence.entity.shop.ShopEntity;
import com.sparta.ordermanagement.framework.persistence.repository.ReviewRepository;
import com.sparta.ordermanagement.framework.persistence.repository.ShopRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class ReviewPersistenceAdapter implements ReviewOutputPort {

    private final ReviewRepository reviewRepository;
    private final ShopRepository shopRepository;

    public Optional<Review> findByReviewUuid(String reviewUuid) {
        return reviewRepository.findByReviewUuid(reviewUuid)
            .map(ReviewEntity::toDomain)
            .or(Optional::empty);
    }

    public Optional<Review> findByReviewUuidAndShopIdAndIsDeletedFalse(String reviewUuid, String shopId) {
        return reviewRepository.findByReviewUuidAndShopIdAndIsDeletedFalse(reviewUuid, shopId)
            .map(ReviewEntity::toDomain)
            .or(Optional::empty);
    }

    @Override
    public Review saveReview(ReviewForCreate reviewForCreate, String shopUuid) {

        ShopEntity shopEntity = shopRepository.findByShopUuid(shopUuid)
            .orElseThrow(() -> new ShopUuidInvalidException(shopUuid));

        // TODO: UserEntity 추가

        return reviewRepository.save(ReviewEntity.from(reviewForCreate, shopEntity)).toDomain();
    }

    @Transactional
    @Override
    public Review updateReview(ReviewForUpdate reviewForUpdate) {

        ReviewEntity reviewEntity = getReviewByReviewUuid(reviewForUpdate.reviewUuid());
        reviewEntity.updateReview(reviewForUpdate);

        return reviewEntity.toDomain();
    }

    @Transactional
    @Override
    public Review deleteReview(ReviewForDelete reviewForDelete) {

        ReviewEntity reviewEntity = getReviewByReviewUuid(reviewForDelete.reviewUuid());
        reviewEntity.deleteReview(reviewForDelete.userId());

        return reviewEntity.toDomain();
    }

    private ReviewEntity getReviewByReviewUuid(String reviewUuid) {
        return reviewRepository.findByReviewUuid(reviewUuid).get();
    }
}
