package com.sparta.ordermanagement.application.service;

import com.sparta.ordermanagement.application.domain.order.Order;
import com.sparta.ordermanagement.application.domain.review.Review;
import com.sparta.ordermanagement.application.domain.review.ReviewForCreate;
import com.sparta.ordermanagement.application.domain.review.ReviewForUpdate;
import com.sparta.ordermanagement.application.domain.shop.Shop;
import com.sparta.ordermanagement.application.exception.review.ReviewUuidInvalidException;
import com.sparta.ordermanagement.application.output.ReviewOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewOutputPort reviewOutputPort;
    private final OrderService orderService;
    private final ShopService shopService;

    public Review createReview(ReviewForCreate reviewForCreate) {

        Order order = orderService.validateOrderUuidAndGetOrder(reviewForCreate.orderUuid());
        Shop shop = shopService.validateShopUuidAndGetShop(order.getShopId());

        // TODO: User 확인

        return reviewOutputPort.saveReview(reviewForCreate, shop.getUuid());
    }

    public Review updateReview(ReviewForUpdate reviewForUpdate) {

        Order order = orderService.validateOrderUuidAndGetOrder(reviewForUpdate.orderUuid());
        shopService.validateShopUuid(order.getShopId());

        validateReviewUuid(reviewForUpdate.reviewUuid());

        // TODO: User 확인 && User가 Order에 속해있는지 확인

        return reviewOutputPort.updateReview(reviewForUpdate);
    }

    private void validateReviewUuid(String reviewUuid) {
        reviewOutputPort.findByReviewUuid(reviewUuid)
            .orElseThrow(() -> new ReviewUuidInvalidException(reviewUuid));
    }
}
