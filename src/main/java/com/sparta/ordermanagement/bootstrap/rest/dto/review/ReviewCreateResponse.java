package com.sparta.ordermanagement.bootstrap.rest.dto.review;

import com.sparta.ordermanagement.application.domain.review.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewCreateResponse {

    private final String shopUuid;
    private final String reviewUuid;
    private final String userId;

    public static ReviewCreateResponse from(Review review) {
        return new ReviewCreateResponse(
            review.getShop().getUuid(),
            review.getReviewUuid(),
            review.getUser().getUserStringId()
        );
    }
}
