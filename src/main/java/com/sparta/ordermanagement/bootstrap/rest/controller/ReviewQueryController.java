package com.sparta.ordermanagement.bootstrap.rest.controller;

import com.sparta.ordermanagement.application.domain.order.Order;
import com.sparta.ordermanagement.application.domain.review.Review;
import com.sparta.ordermanagement.application.service.ReviewService;
import com.sparta.ordermanagement.bootstrap.auth.UserDetailsImpl;
import com.sparta.ordermanagement.bootstrap.rest.dto.review.ReviewDetailResponse;
import com.sparta.ordermanagement.bootstrap.rest.exception.exceptions.OrderNotFoundException;
import com.sparta.ordermanagement.bootstrap.rest.exception.exceptions.ReviewNotFoundException;
import com.sparta.ordermanagement.framework.persistence.adapter.OrderPersistenceAdapter;
import com.sparta.ordermanagement.framework.persistence.adapter.ReviewPersistenceAdapter;
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

    private final ReviewPersistenceAdapter reviewPersistenceAdapter;
    private final OrderPersistenceAdapter orderPersistenceAdapter;
    private final ReviewService reviewService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{reviewUuid}")
    public ReviewDetailResponse findOne(
        @PathVariable(value = "orderUuid") String orderUuid,
        @PathVariable(value = "reviewUuid") String reviewUuid,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {

        Order order = orderPersistenceAdapter.findByOrderUuidAndIsDeletedFalse(orderUuid)
            .orElseThrow(() -> new OrderNotFoundException(orderUuid));

        Review review = reviewPersistenceAdapter.findByReviewUuidAndShopIdAndIsDeletedFalse(reviewUuid, order.getShopId())
            .orElseThrow(() -> new ReviewNotFoundException(order.getShopId(), reviewUuid));

        reviewService.validateReviewBelongToUser(review, userDetails.getUserStringId());

        return ReviewDetailResponse.from(review);
    }
}
