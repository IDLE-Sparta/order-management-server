package com.sparta.ordermanagement.application.exception.order;

import static com.sparta.ordermanagement.application.exception.message.OrderErrorMessage.ORDER_MISMATCH_REVIEWER;

import com.sparta.ordermanagement.application.exception.ForbiddenException;

public class OrderMismatchReviewerException extends ForbiddenException {

    public OrderMismatchReviewerException(String userStringId) {
        super(String.format(ORDER_MISMATCH_REVIEWER.getMessage(), userStringId));
    }
}
