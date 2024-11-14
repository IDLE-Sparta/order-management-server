package com.sparta.ordermanagement.bootstrap.rest.dto.review;

import com.sparta.ordermanagement.application.domain.review.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewUpdateResponse {

    // TODO : User 추가하기

    private final String shopUuid;
    private final String reviewUuid;
    private final String userId;

    public static ReviewUpdateResponse from(Review review) {
        return new ReviewUpdateResponse(
            review.getShop().getUuid(),
            review.getReviewUuid(),
            "0000" // TODO : 추후에 수정하기
        );
    }
}
