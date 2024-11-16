package com.sparta.ordermanagement.application.service.review.integrationTest;

import com.sparta.ordermanagement.application.domain.review.Review;
import com.sparta.ordermanagement.application.domain.review.ReviewForUpdate;
import com.sparta.ordermanagement.application.exception.review.ReviewUuidInvalidException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ReviewServiceUpdateIntegrationTest extends BaseReviewServiceIntegrationTest {

    @Test
    @DisplayName("[리뷰 수정 성공 통합 테스트] 리뷰 작성자가 자신의 리뷰를 수정하면 수정한 리뷰를 반환한다.")
    public void updateReview_successTest() {
        ReviewForUpdate reviewForUpdate = new ReviewForUpdate(
            3,
            "그저그래요.",
            orderEntity.getOrderUuid(),
            reviewEntity.getReviewUuid(),
            customerEntity.getUserStringId()
        );

        Review review = reviewService.updateReview(reviewForUpdate);

        Assertions.assertAll(
            () -> Assertions.assertNotNull(review),
            () -> Assertions.assertEquals(reviewForUpdate.rating(), review.getRating()),
            () -> Assertions.assertEquals(reviewForUpdate.content(), review.getContent())
        );
    }

    @Test
    @DisplayName("[리뷰 수정 실패 통합 테스트] 리뷰 작성자가 유효하지 않은 리뷰 식별자를 통해 리뷰를 수정하면 예외처리 한다.")
    public void updateReview_failureTest_invalidReviewUuid() {
        ReviewForUpdate reviewForUpdate = new ReviewForUpdate(
            3,
            "그저그래요.",
            orderEntity.getOrderUuid(),
            "invalid-review-uuid",
            customerEntity.getUserStringId()
        );

        ReviewUuidInvalidException exception = Assertions.assertThrows(
            ReviewUuidInvalidException.class,
            () -> reviewService.updateReview(reviewForUpdate)
        );

        Assertions.assertEquals(
            String.format("유효하지 않은 리뷰 식별자 입니다.: %s", reviewForUpdate.reviewUuid()),
            exception.getMessage()
        );
    }
}
