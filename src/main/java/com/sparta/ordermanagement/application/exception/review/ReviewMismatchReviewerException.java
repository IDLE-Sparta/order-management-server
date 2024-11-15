package com.sparta.ordermanagement.application.exception.review;

import static com.sparta.ordermanagement.application.exception.message.ReviewErrorMessage.REVIEW_MISMATCH_REVIEWER;

import com.sparta.ordermanagement.application.exception.ForbiddenException;

public class ReviewMismatchReviewerException extends ForbiddenException {

    public ReviewMismatchReviewerException(String userStringId) {
        super(String.format(REVIEW_MISMATCH_REVIEWER.getMessage(), userStringId));
    }
}
