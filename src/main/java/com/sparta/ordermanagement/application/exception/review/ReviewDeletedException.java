package com.sparta.ordermanagement.application.exception.review;

import static com.sparta.ordermanagement.application.exception.message.ReviewErrorMessage.REVIEW_DELETED;

import com.sparta.ordermanagement.application.exception.DeletedException;

public class ReviewDeletedException extends DeletedException {

    public ReviewDeletedException(String reviewUuid) {
        super(String.format(REVIEW_DELETED.getMessage(), reviewUuid));
    }
}
