package com.sparta.ordermanagement.bootstrap.rest.controller;

import com.sparta.ordermanagement.application.domain.review.Review;
import com.sparta.ordermanagement.application.domain.review.ReviewForCreate;
import com.sparta.ordermanagement.application.domain.review.ReviewForDelete;
import com.sparta.ordermanagement.application.domain.review.ReviewForUpdate;
import com.sparta.ordermanagement.application.service.ReviewService;
import com.sparta.ordermanagement.bootstrap.auth.UserDetailsImpl;
import com.sparta.ordermanagement.bootstrap.rest.dto.review.ReviewDeleteResponse;
import com.sparta.ordermanagement.bootstrap.rest.dto.review.ReviewUpdateRequest;
import com.sparta.ordermanagement.bootstrap.rest.dto.review.ReviewCreateRequest;
import com.sparta.ordermanagement.bootstrap.rest.dto.review.ReviewCreateResponse;
import com.sparta.ordermanagement.bootstrap.rest.dto.review.ReviewUpdateResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/orders/{orderUuid}/reviews")
public class ReviewCommandController {

    // TODO: 추후에 지울 예정
    private static final String TEST_CREATED_USER_ID = "0000";

    private final ReviewService reviewService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ReviewCreateResponse createReview(
        @PathVariable(value = "orderUuid") String orderUuid,
        @Valid @RequestBody ReviewCreateRequest reviewCreateRequest,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {

        ReviewForCreate reviewForCreate = reviewCreateRequest.toDomain(
            orderUuid,
            userDetails.getUserStringId()
        );
        Review review = reviewService.createReview(reviewForCreate);

        return ReviewCreateResponse.from(review);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{reviewUuid}")
    public ReviewUpdateResponse updateReview(
        @PathVariable(value = "orderUuid") String orderUuid,
        @PathVariable(value = "reviewUuid") String reviewUuid,
        @Valid @RequestBody ReviewUpdateRequest reviewUpdateRequest,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {

        ReviewForUpdate reviewForUpdate = reviewUpdateRequest.toDomain(
            orderUuid,
            reviewUuid,
            userDetails.getUserStringId()
        );
        Review review = reviewService.updateReview(reviewForUpdate);

        return ReviewUpdateResponse.from(review);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{reviewUuid}")
    public ReviewDeleteResponse deleteReview(
        @PathVariable(value = "orderUuid") String orderUuid,
        @PathVariable(value = "reviewUuid") String reviewUuid
    ) {

        // TODO: 작성자가 주문한 사람과 동일한 지 확인

        ReviewForDelete reviewForDelete = new ReviewForDelete(
            true,
            orderUuid,
            reviewUuid,
            TEST_CREATED_USER_ID
        );
        Review review = reviewService.deleteReview(reviewForDelete);

        return ReviewDeleteResponse.from(review);
    }
}
