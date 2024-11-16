package com.sparta.ordermanagement.application.service.review.unitTest;

import com.sparta.ordermanagement.application.domain.review.Review;
import com.sparta.ordermanagement.application.domain.review.ReviewForUpdate;
import com.sparta.ordermanagement.application.domain.user.User;
import com.sparta.ordermanagement.application.exception.review.ReviewDeletedException;
import com.sparta.ordermanagement.application.exception.review.ReviewMismatchReviewerException;
import com.sparta.ordermanagement.application.exception.review.ReviewUuidInvalidException;
import com.sparta.ordermanagement.application.service.TestDataForUnitTest;
import com.sparta.ordermanagement.framework.persistence.entity.user.Role;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceUpdateUnitTest extends BaseReviewServiceUnitTest {

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

        existReview = TestDataForUnitTest.createReview(reviewUuid, rating, reviewContent, shop, customer);
    }

    @Test
    @DisplayName("[리뷰 모든 필드 수정 성공 단위 테스트] 리뷰 작성자가 자신이 작성한 리뷰를 수정하면 수정된 리뷰를 반환한다.")
    public void updateAllFieldsReview_successTest() {
        // Given
        Review expectedReview = TestDataForUnitTest.createReviewWithoutTime(existReview.getReviewUuid(), 4,
            "맛있네요!", shop, customer);

        ReviewForUpdate reviewForUpdate = new ReviewForUpdate(expectedReview.getRating(),
            expectedReview.getContent(), order.getOrderUuid(), existReview.getReviewUuid(),
            existReview.getUser().getUserStringId());

        Mockito.when(orderService.validateOrderUuidAndGetNotDeletedOrder(order.getOrderUuid()))
            .thenReturn(order);
        Mockito.when(reviewOutputPort.findByReviewUuid(reviewForUpdate.reviewUuid()))
            .thenReturn(Optional.of(existReview));
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

        Mockito.verify(reviewOutputPort, Mockito.times(1))
            .updateReview(reviewForUpdate);
    }

    @Test
    @DisplayName("[리뷰 일부 필드 수정 성공 단위 테스트] 리뷰 작성자가 자신이 작성한 리뷰의 별점을 수정하면 수정된 리뷰를 반환한다.")
    public void updatePartialReview_successTest() {
        // Given
        Review expectedReview = TestDataForUnitTest.createReviewWithoutTime(existReview.getReviewUuid(), 4,
            existReview.getContent(), shop, customer);

        ReviewForUpdate reviewForUpdate = new ReviewForUpdate(expectedReview.getRating(),
            existReview.getContent(), order.getOrderUuid(), existReview.getReviewUuid(),
            existReview.getUser().getUserStringId());

        Mockito.when(orderService.validateOrderUuidAndGetNotDeletedOrder(order.getOrderUuid()))
            .thenReturn(order);
        Mockito.when(reviewOutputPort.findByReviewUuid(reviewForUpdate.reviewUuid()))
            .thenReturn(Optional.of(existReview));
        Mockito.when(reviewOutputPort.updateReview(reviewForUpdate)).thenReturn(expectedReview);

        // When
        Review actualReview = reviewService.updateReview(reviewForUpdate);

        // Then
        Assertions.assertAll(
            "리뷰 일부 필드 수정",
            () -> Assertions.assertNotNull(actualReview, "수정된 리뷰는 null이 아니어야 합니다."),
            () -> Assertions.assertEquals(expectedReview.getRating(), actualReview.getRating())
        );

        Mockito.verify(reviewOutputPort, Mockito.times(1)).updateReview(reviewForUpdate);
    }


    @Test
    @DisplayName("[리뷰 수정 실패 단위 테스트] 유효하지 않는 리뷰 식별자에 대한 리뷰 수정 시 예외처리를 한다.")
    public void updateReview_failureTest_invalidReviewUuid() {
        // Given
        String invalidReviewUuid = "invalid-review-uuid";
        ReviewForUpdate reviewForUpdate = new ReviewForUpdate(rating, reviewContent, order.getOrderUuid(),
            invalidReviewUuid, customer.getUserStringId());

        Mockito.when(orderService.validateOrderUuidAndGetNotDeletedOrder(order.getOrderUuid()))
            .thenReturn(order);
        Mockito.when(reviewOutputPort.findByReviewUuid(reviewForUpdate.reviewUuid()))
            .thenThrow(new ReviewUuidInvalidException(reviewForUpdate.reviewUuid()));


        // When & Then
        ReviewUuidInvalidException exception = Assertions.assertThrows(
            ReviewUuidInvalidException.class,
            () -> reviewService.updateReview(reviewForUpdate)
        );

        Assertions.assertEquals(
            String.format("유효하지 않은 리뷰 식별자 입니다.: %s", invalidReviewUuid),
            exception.getMessage()
        );

        Mockito.verify(reviewOutputPort, Mockito.never()).updateReview(reviewForUpdate);
    }

    @Test
    @DisplayName("[리뷰 수정 실패 단위 테스트] 삭제된 리뷰 식별자에 대한 리뷰 수정 시 예외처리를 한다.")
    public void updateReview_failureTest_deletedReview() {
        // Given
        Review deletedReview = TestDataForUnitTest.createDeletedReviewWithoutTime(existReview.getReviewUuid(),
            existReview.getRating(), existReview.getContent(), shop, customer);
        ReviewForUpdate reviewForUpdate = new ReviewForUpdate(rating, reviewContent, order.getOrderUuid(),
            deletedReview.getReviewUuid(), customer.getUserStringId());

        Mockito.when(orderService.validateOrderUuidAndGetNotDeletedOrder(order.getOrderUuid()))
            .thenReturn(order);
        Mockito.when(reviewOutputPort.findByReviewUuid(reviewForUpdate.reviewUuid()))
            .thenThrow(new ReviewDeletedException(reviewForUpdate.reviewUuid()));

        // When & Then
        ReviewDeletedException exception = Assertions.assertThrows(
            ReviewDeletedException.class,
            () -> reviewService.updateReview(reviewForUpdate)
        );

        Assertions.assertEquals(
            String.format("삭제된 리뷰입니다.: %s", reviewForUpdate.reviewUuid()),
            exception.getMessage()
        );

        Mockito.verify(reviewOutputPort, Mockito.never()).updateReview(reviewForUpdate);
    }

    @Test
    @DisplayName("[리뷰 실패 단위 테스트] 리뷰 작성자에게 속하지 않은 리뷰를 수정하려고 하면 예외처리를 한다.")
    public void updateReview_failureTest_notBelongToCustomer() {
        // Given
        User otherCustomer = TestDataForUnitTest.createUser("customer2", Role.CUSTOMER, regionEntity);
        Review otherReview = TestDataForUnitTest.createReviewWithoutTime("other-review-uuid", 5,
            "매우 맛있어요!", shop, otherCustomer);
        ReviewForUpdate reviewForUpdate = new ReviewForUpdate(rating, reviewContent, order.getOrderUuid(),
            otherReview.getReviewUuid(), customer.getUserStringId());

        Mockito.when(orderService.validateOrderUuidAndGetNotDeletedOrder(order.getOrderUuid()))
            .thenReturn(order);
        Mockito.when(reviewOutputPort.findByReviewUuid(reviewForUpdate.reviewUuid()))
            .thenReturn(Optional.of(otherReview));

        // When & Then
        ReviewMismatchReviewerException exception = Assertions.assertThrows(
            ReviewMismatchReviewerException.class,
            () -> reviewService.updateReview(reviewForUpdate)
        );

        Assertions.assertEquals(
            String.format("작성자와 일치하지 않습니다.: %s", customer.getUserStringId()),
            exception.getMessage()
        );

        Mockito.verify(reviewOutputPort, Mockito.never()).updateReview(reviewForUpdate);
    }
}
