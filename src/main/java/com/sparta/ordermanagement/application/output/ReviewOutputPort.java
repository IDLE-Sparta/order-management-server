package com.sparta.ordermanagement.application.output;

import com.sparta.ordermanagement.application.domain.review.Review;
import com.sparta.ordermanagement.application.domain.review.ReviewForCreate;
import com.sparta.ordermanagement.application.domain.review.ReviewForDelete;
import com.sparta.ordermanagement.application.domain.review.ReviewForUpdate;
import java.util.Optional;

public interface ReviewOutputPort {

    Review saveReview(ReviewForCreate reviewForCreate, String shopUuid);

    Review updateReview(ReviewForUpdate reviewForUpdate);

    Optional<Review> findByReviewUuid(String reviewUuid);

    Review deleteReview(ReviewForDelete reviewForDelete);
}
