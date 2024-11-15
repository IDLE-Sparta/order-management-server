package com.sparta.ordermanagement.bootstrap.rest.dto.review;

import com.sparta.ordermanagement.application.domain.review.Review;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewListResponse {

    private final String reviewUuid;
    private final String userStringId;
    private final Integer rating;
    private final String content;
    private final LocalDateTime createdAt;
    private final String createdBy;
    private final LocalDateTime updatedAt;
    private final String updatedBy;

    public static ReviewListResponse from(Review review) {
        return new ReviewListResponse(
            review.getReviewUuid(),
            review.getUser().getUserStringId(),
            review.getRating(),
            review.getContent(),
            review.getCreatedAt(),
            review.getCreatedBy(),
            review.getUpdatedAt(),
            review.getUpdatedBy()
        );
    }
}
