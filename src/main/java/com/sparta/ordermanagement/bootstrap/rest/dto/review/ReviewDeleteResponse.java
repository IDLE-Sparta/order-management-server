package com.sparta.ordermanagement.bootstrap.rest.dto.review;

import com.sparta.ordermanagement.application.domain.review.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewDeleteResponse {

    private String shopUuid;
    private String reviewUuid;
    private String userId;

    public static ReviewDeleteResponse from(Review review) {
        return new ReviewDeleteResponse(
            review.getShop().getUuid(),
            review.getReviewUuid(),
            review.getUser().getUserStringId()
        );
    }
}
