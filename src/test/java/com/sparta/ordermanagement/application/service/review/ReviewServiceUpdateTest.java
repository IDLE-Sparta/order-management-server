package com.sparta.ordermanagement.application.service.review;

import com.sparta.ordermanagement.application.domain.review.Review;
import com.sparta.ordermanagement.application.domain.review.ReviewForUpdate;
import com.sparta.ordermanagement.application.service.TestData;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceUpdateTest extends BaseReviewServiceTest {

    private String reviewUuid;
    private Integer rating;
    private String reviewContent;

    private Review existReview;

    @BeforeEach
    void setUp() {
        super.setUp();
        reviewUuid = "review-uuid";
        rating = 5;
        reviewContent = "정말 맛있네요!";

        existReview = TestData.createReview(reviewUuid, rating, reviewContent, shop, customer);
    }

    @Test
    @DisplayName("[리뷰 모든 필드 수정 성공 테스트] 리뷰 작성자가 자신이 작성한 리뷰를 수정하면 수정된 리뷰를 반환한다.")
    public void updateReview_successTest() {
        // Given
        Review expectedReview = TestData.createReviewWithoutTime(existReview.getReviewUuid(), 4,
            "맛있네요!", shop, customer);

        ReviewForUpdate reviewForUpdate = new ReviewForUpdate(existReview.getRating(),
            existReview.getContent(), order.getOrderUuid(), existReview.getReviewUuid(),
            existReview.getUser().getUserStringId());

        Mockito.when(orderService.validateOrderUuidAndGetNotDeletedOrder(order.getOrderUuid())).thenReturn(order);
        Mockito.when(reviewOutputPort.findByReviewUuid(reviewForUpdate.reviewUuid())).thenReturn(Optional.of(existReview));
        Mockito.when(reviewOutputPort.updateReview(reviewForUpdate)).thenReturn(expectedReview);

        // When
        Review actualReview = reviewService.updateReview(reviewForUpdate);

        // Then
        Assertions.assertAll(
            "리뷰 모든 필드 수정",
            () -> Assertions.assertNotNull(actualReview, "수정된 리뷰는 null이 아니어야 합니다."),
            () -> Assertions.assertEquals(expectedReview.getRating(), actualReview.getRating()),
            () -> Assertions.assertEquals(expectedReview.getContent(), actualReview.getContent())
        );

        Mockito.verify(reviewOutputPort, Mockito.times(1)).updateReview(reviewForUpdate);
    }
}
