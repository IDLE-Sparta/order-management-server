package com.sparta.ordermanagement.bootstrap.rest.controller;

import com.sparta.ordermanagement.application.domain.review.Review;
import com.sparta.ordermanagement.application.domain.review.ReviewForRead;
import com.sparta.ordermanagement.application.service.ReviewService;
import com.sparta.ordermanagement.bootstrap.auth.UserDetailsImpl;
import com.sparta.ordermanagement.bootstrap.rest.dto.review.ReviewDetailResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders/{orderUuid}/reviews")
@RestController
public class ReviewQueryController {

    private final ReviewService reviewService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{reviewUuid}")
    public ReviewDetailResponse findReviewByReviewUuid(
        @PathVariable(value = "orderUuid") String orderUuid,
        @PathVariable(value = "reviewUuid") String reviewUuid,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {

        ReviewForRead reviewForRead = new ReviewForRead(
            orderUuid,
            reviewUuid,
            userDetails.getUserStringId()
        );
        Review review = reviewService.getReview(reviewForRead);

        return ReviewDetailResponse.from(review);
    }
}
