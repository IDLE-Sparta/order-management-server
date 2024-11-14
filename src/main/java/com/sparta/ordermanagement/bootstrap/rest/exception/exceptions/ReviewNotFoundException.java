package com.sparta.ordermanagement.bootstrap.rest.exception.exceptions;

import static com.sparta.ordermanagement.bootstrap.rest.exception.exceptions.message.ReviewErrorMessage.REVIEW_NOT_FOUND;

public class ReviewNotFoundException extends DataNotFoundException {

    public ReviewNotFoundException(String shopUuid, String reviewUuid) {
        super(String.format(REVIEW_NOT_FOUND.getMessage(), shopUuid, reviewUuid));
    }
}
