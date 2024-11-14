package com.sparta.ordermanagement.application.exception.review;

import static com.sparta.ordermanagement.application.exception.message.ReviewErrorMessage.REVIEW_UUID_INVALID;

import com.sparta.ordermanagement.application.exception.InvalidValueException;

public class ReviewUuidInvalidException extends InvalidValueException {

    public ReviewUuidInvalidException(String reviewUuid) {
        super(String.format(REVIEW_UUID_INVALID.getMessage(), reviewUuid));
    }
}
