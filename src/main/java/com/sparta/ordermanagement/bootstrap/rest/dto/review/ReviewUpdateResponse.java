package com.sparta.ordermanagement.bootstrap.rest.dto.review;

import com.sparta.ordermanagement.application.domain.review.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewUpdateResponse {

    private final String shopUuid;
    private final String reviewUuid;
    private final String userId;

    public static ReviewUpdateResponse from(Review review) {
        return new ReviewUpdateResponse(
            review.getShop().getUuid(),
            review.getReviewUuid(),
            review.getUser().getUserStringId()
        );
    }
}
