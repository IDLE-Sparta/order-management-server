package com.sparta.ordermanagement.application.output;

import com.sparta.ordermanagement.application.domain.review.Review;
import com.sparta.ordermanagement.application.domain.review.ReviewForCreate;

public interface ReviewOutputPort {

    Review saveReview(ReviewForCreate reviewForCreate, String shopUuid);
}
