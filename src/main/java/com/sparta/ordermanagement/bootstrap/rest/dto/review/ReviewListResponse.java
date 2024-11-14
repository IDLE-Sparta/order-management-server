package com.sparta.ordermanagement.bootstrap.rest.dto.review;

import com.sparta.ordermanagement.application.domain.review.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewListResponse {

    // TODO: User 추가하기

    private final String reviewUuid;
    private final String userStringId;
    private final Integer rating;
    private final String content;

    public static ReviewListResponse from(Review review) {
        return new ReviewListResponse(
            review.getReviewUuid(),
            "0000", // 추후 삭제하고 user로 대체
            review.getRating(),
            review.getContent()
        );
    }
}
