package com.sparta.ordermanagement.bootstrap.rest.controller.review;

import com.sparta.ordermanagement.application.domain.review.Review;
import com.sparta.ordermanagement.application.domain.review.ReviewForRead;
import com.sparta.ordermanagement.application.service.review.ReviewService;
import com.sparta.ordermanagement.bootstrap.auth.UserDetailsImpl;
import com.sparta.ordermanagement.bootstrap.rest.dto.review.ReviewDetailResponse;
import com.sparta.ordermanagement.framework.persistence.adapter.order.OrderPersistenceAdapter;
import com.sparta.ordermanagement.framework.persistence.adapter.review.ReviewPersistenceAdapter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "사용자 - 리뷰 조회", description = "권한 불필요")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders/{orderUuid}/reviews")
@RestController
public class ReviewQueryController {

    private final ReviewPersistenceAdapter reviewPersistenceAdapter;
    private final OrderPersistenceAdapter orderPersistenceAdapter;
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
