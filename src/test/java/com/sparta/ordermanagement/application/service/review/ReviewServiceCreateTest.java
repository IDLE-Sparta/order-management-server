package com.sparta.ordermanagement.application.service.review;

import com.sparta.ordermanagement.application.domain.review.Review;
import com.sparta.ordermanagement.application.domain.review.ReviewForCreate;
import com.sparta.ordermanagement.application.exception.order.OrderUuidInvalidException;
import com.sparta.ordermanagement.application.service.TestData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceCreateTest extends BaseReviewServiceTest {

    private String reviewUuid;
    private Integer rating;
    private String reviewContent;

    @BeforeEach
    void setUp() {
        super.setUp();
        reviewUuid = "review-uuid";
        rating = 5;
        reviewContent = "정말 맛있네요!";
    }


    @Test
    @DisplayName("[리뷰 생성 성공 테스트] 주문한 고객이 리뷰 작성에 성공하면 가게 식별자와 리뷰 식별자 및 작성자 아이디를 반환한다.")
    public void createReview_successTest() {
        // Given
        Review expectedReview = TestData.createReviewWithoutTime(reviewUuid, rating, reviewContent, shop, customer);

        ReviewForCreate reviewForCreate = new ReviewForCreate(rating, reviewContent, order.getOrderUuid(), customer.getUserStringId());

        Mockito.when(orderService.validateOrderUuidAndGetNotDeletedOrder(ArgumentMatchers.eq(order.getOrderUuid())))
            .thenReturn(order);
        Mockito.when(shopService.validateShopUuidAndGetNotDeletedShop(ArgumentMatchers.eq(order.getShopId())))
            .thenReturn(shop);
        Mockito.when(reviewOutputPort.saveReview(ArgumentMatchers.eq(reviewForCreate),
            ArgumentMatchers.eq(shop.getUuid()))).thenReturn(expectedReview);

        // When
        Review actualReview = reviewService.createReview(reviewForCreate);

        // Then
        Assertions.assertAll(
            "Review 생성 필드 검증",
            () -> Assertions.assertNotNull(actualReview, "실제 Review는 null이 아니어야 합니다."),
            () -> Assertions.assertEquals(expectedReview.getReviewUuid(), actualReview.getReviewUuid()),
            () -> Assertions.assertEquals(expectedReview.getRating(), actualReview.getRating()),
            () -> Assertions.assertEquals(expectedReview.getContent(), actualReview.getContent()),
            () -> Assertions.assertEquals(expectedReview.getShop().getUuid(), actualReview.getShop().getUuid()),
            () -> Assertions.assertEquals(expectedReview.getUser().getUserStringId(), actualReview.getUser().getUserStringId())
        );

        Mockito.verify(reviewOutputPort, Mockito.times(1)).saveReview(reviewForCreate, shop.getUuid());
    }

    @Test
    @DisplayName("[리뷰 작성 실패 테스트] 유효하지 않는 주문 식별자로 리뷰 생성 시 예외처리를 한다.")
    public void createReview_failureTest_invalidOrderUuid() {
        // Given
        String invalidOrderUuid = "invalid-order-uuid";

        ReviewForCreate reviewForCreate = new ReviewForCreate(rating, reviewContent, invalidOrderUuid, customer.getUserStringId());

        Mockito.when(orderService.validateOrderUuidAndGetNotDeletedOrder(ArgumentMatchers.eq(invalidOrderUuid)))
            .thenThrow(new OrderUuidInvalidException(invalidOrderUuid));

        // When & Then
        OrderUuidInvalidException exception = Assertions.assertThrows(
            OrderUuidInvalidException.class,
            () -> reviewService.createReview(reviewForCreate)
        );

        Assertions.assertEquals(
            String.format("유효하지 않은 주문 식별자 입니다. : %s", invalidOrderUuid),
            exception.getMessage()
        );

        Mockito.verifyNoInteractions(shopService, reviewOutputPort);
    }
}
