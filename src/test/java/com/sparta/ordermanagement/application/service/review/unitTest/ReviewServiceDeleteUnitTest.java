package com.sparta.ordermanagement.application.service.review.unitTest;

import com.sparta.ordermanagement.application.domain.review.Review;
import com.sparta.ordermanagement.application.domain.review.ReviewForDelete;
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
public class ReviewServiceDeleteUnitTest extends BaseReviewServiceUnitTest {

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
    @DisplayName("[리뷰 삭제 성공 단위 테스트] 리뷰 작성자가 자신이 작성한 리뷰를 삭제하면 리뷰가 삭제되었는지 확인한다.")
    public void deleteReview_successTest() {
        // Given
        Review expectedReview = TestData.createReviewWithoutTime(existReview.getReviewUuid(),
            existReview.getRating(), existReview.getContent(), shop, customer);
        ReviewForDelete reviewForDelete = new ReviewForDelete(true, order.getOrderUuid(),
            existReview.getReviewUuid(), customer.getUserStringId());

        Mockito.when(orderService.validateOrderUuidAndGetNotDeletedOrder(order.getOrderUuid()))
            .thenReturn(order);
        Mockito.when(reviewOutputPort.findByReviewUuid(reviewForDelete.reviewUuid()))
            .thenReturn(Optional.of(existReview));
        Mockito.when(reviewOutputPort.deleteReview(reviewForDelete))
            .thenReturn(expectedReview);

        // When
        Review actualReview = reviewService.deleteReview(reviewForDelete);

        // Then
        Assertions.assertAll(
            "리뷰 삭제 필드 검증",
            () -> Assertions.assertNotNull(actualReview, "삭제 리뷰는 null이 되어서는 안됩니다."),
            () -> Assertions.assertEquals(expectedReview.getIsDeleted(), actualReview.getIsDeleted())
        );

        Mockito.verify(reviewOutputPort, Mockito.times(1)).deleteReview(reviewForDelete);
    }
}
