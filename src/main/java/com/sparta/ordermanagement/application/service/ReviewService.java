package com.sparta.ordermanagement.application.service;

import com.sparta.ordermanagement.application.domain.order.Order;
import com.sparta.ordermanagement.application.domain.review.Review;
import com.sparta.ordermanagement.application.domain.review.ReviewForCreate;
import com.sparta.ordermanagement.application.domain.shop.Shop;
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

        Order order = orderService.validateOrderUuidAndGet(reviewForCreate.orderUuid());
        Shop shop = shopService.validateShopUuidAndGetShop(order.getShopId());

        // TODO: User 확인

        return reviewOutputPort.saveReview(reviewForCreate, shop.getUuid());
    }
}
