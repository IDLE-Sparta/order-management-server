package com.sparta.ordermanagement.bootstrap.rest.dto.review;

import com.sparta.ordermanagement.application.domain.review.Review;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewDetailResponse {

    // TODO: User 추가하기

    private final String reviewUuid;
//    private final String userUuid;
    private final String shopUuid;
    private final String shopName;
    private final Integer rating;
    private final String content;
    private final LocalDateTime createdAt;
    private final String createdBy;
    private final LocalDateTime updatedAt;
    private final String updatedBy;

    public static ReviewDetailResponse from(Review review) {
        return new ReviewDetailResponse(
            review.getReviewUuid(),
            review.getShop().getUuid(),
            review.getShop().getShopName(),
            review.getRating(),
            review.getContent(),
            review.getCreatedAt(),
            review.getCreatedBy(),
            review.getUpdatedAt(),
            review.getUpdatedBy()
        );
    }
}
