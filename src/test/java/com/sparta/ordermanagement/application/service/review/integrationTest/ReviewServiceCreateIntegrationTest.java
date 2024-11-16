package com.sparta.ordermanagement.application.service.review.integrationTest;

import com.sparta.ordermanagement.application.domain.review.Review;
import com.sparta.ordermanagement.application.domain.review.ReviewForCreate;
import com.sparta.ordermanagement.application.exception.order.OrderUuidInvalidException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ReviewServiceCreateIntegrationTest extends BaseReviewServiceIntegrationTest {

    @Test
    @DisplayName("[리뷰 생성 성공 통합 테스트] 주문자가 리뷰를 생성하면 생성한 리뷰를 돌려준다.")
    public void createReview_successTest() {
        ReviewForCreate reviewForCreate = new ReviewForCreate(
            5,
            "정말 맛있어요!",
            orderEntity.getOrderUuid(),
            customerEntity.getUserStringId()
        );

        Review review = reviewService.createReview(reviewForCreate);

        Assertions.assertAll(
            () -> Assertions.assertNotNull(review),
            () -> Assertions.assertEquals(5, review.getRating()),
            () -> Assertions.assertEquals("정말 맛있어요!", review.getContent())
        );
    }

    @Test
    @DisplayName("[리뷰 생성 실패 통합 테스트] 주문자가 유효하지 주문의 식별자로 리뷰를 생성하면 예외처리를 한다.")
    public void createReview_failureTest_invalidOrderUuid() {
        String invalidOrderUuid = "invalid-order-Uuid";
        ReviewForCreate reviewForCreate = new ReviewForCreate(
            5,
            "정말 맛있어요!",
            invalidOrderUuid,
            customerEntity.getUserStringId()
        );

        OrderUuidInvalidException exception = Assertions.assertThrows(
            OrderUuidInvalidException.class,
            () -> reviewService.createReview(reviewForCreate)
        );

        Assertions.assertEquals(
            String.format("유효하지 않은 주문 식별자 입니다. : %s", reviewForCreate.orderUuid()),
            exception.getMessage()
        );
    }
}
