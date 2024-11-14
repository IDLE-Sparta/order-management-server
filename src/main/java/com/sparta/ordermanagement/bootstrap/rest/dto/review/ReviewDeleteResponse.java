package com.sparta.ordermanagement.bootstrap.rest.dto.review;

import com.sparta.ordermanagement.application.domain.review.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewDeleteResponse {

    // TODO: User 추가하기

    private String shopUuid;
    private String reviewUuid;
    private String userId;

    public static ReviewDeleteResponse from(Review review) {
        return new ReviewDeleteResponse(
            review.getShop().getUuid(),
            review.getReviewUuid(),
            "0000" // TODO: 추후에 삭제할 예정
        );
    }
}
