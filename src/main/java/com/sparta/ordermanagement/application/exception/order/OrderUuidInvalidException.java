package com.sparta.ordermanagement.application.exception.order;

import static com.sparta.ordermanagement.application.exception.message.OrderErrorMessage.ORDER_UUID_INVALID;

import com.sparta.ordermanagement.application.exception.InvalidValueException;

public class OrderUuidInvalidException extends InvalidValueException {

    public OrderUuidInvalidException(String orderUuid) {
        super(String.format(ORDER_UUID_INVALID.getMessage(), orderUuid));
    }
}
