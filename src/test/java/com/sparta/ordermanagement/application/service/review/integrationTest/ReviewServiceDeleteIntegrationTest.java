package com.sparta.ordermanagement.application.service.review.integrationTest;

import com.sparta.ordermanagement.application.domain.review.Review;
import com.sparta.ordermanagement.application.domain.review.ReviewForDelete;
import com.sparta.ordermanagement.application.exception.review.ReviewUuidInvalidException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ReviewServiceDeleteIntegrationTest extends BaseReviewServiceIntegrationTest {

    @Test
    @DisplayName("[리뷰 삭제 성공 통합 테스트] 리뷰 작성자가 자신의 리뷰를 삭제하면 리뷰의 삭제 여부를 확인한다.")
    public void deleteReview_successTest() {
        ReviewForDelete reviewForDelete = new ReviewForDelete(
            true,
            orderEntity.getOrderUuid(),
            reviewEntity.getReviewUuid(),
            customerEntity.getUserStringId()
        );

        Review review = reviewService.deleteReview(reviewForDelete);

        Assertions.assertAll(
            () -> Assertions.assertNotNull(review),
            () -> Assertions.assertTrue(review.getIsDeleted())
        );
    }

    @Test
    @DisplayName("[리뷰 삭제 실패 통합 테스트] 리뷰 작성자가 유효하지 않은 리뷰 식별자를 통해 리뷰를 삭제하면 예외처리를 한다.")
    public void deleteReview_failureTest_invalidReviewUuid() {
        ReviewForDelete reviewForDelete = new ReviewForDelete(
            true,
            orderEntity.getOrderUuid(),
            "invalid-review-uuid",
            customerEntity.getUserStringId()
        );

        ReviewUuidInvalidException exception = Assertions.assertThrows(
            ReviewUuidInvalidException.class,
            () -> reviewService.deleteReview(reviewForDelete)
        );

        Assertions.assertEquals(
            String.format("유효하지 않은 리뷰 식별자 입니다.: %s", reviewForDelete.reviewUuid()),
            exception.getMessage()
        );
    }
}
