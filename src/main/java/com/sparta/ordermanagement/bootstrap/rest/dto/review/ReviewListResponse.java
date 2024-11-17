package com.sparta.ordermanagement.bootstrap.rest.dto.review;

import com.sparta.ordermanagement.application.domain.review.Review;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record ReviewListResponse(
    String reviewUuid,
    String userStringId,
    Integer rating,
    String content,
    LocalDateTime createdAt,
    String createdBy,
    LocalDateTime updatedAt,
    String updatedBy
) {

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

    public static List<ReviewListResponse> from(List<Review> reviews) {
        return reviews.stream().map(ReviewListResponse::from).collect(Collectors.toList());
    }

    public record GetReviewsResponse(
        List<ReviewListResponse> reviews
    ) {
        public static GetReviewsResponse of(List<Review> reviews) {
            return new GetReviewsResponse(ReviewListResponse.from(reviews));
        }
    }
}
