package com.sparta.ordermanagement.application.exception.order;

import static com.sparta.ordermanagement.application.exception.message.OrderErrorMessage.ORDER_DELETED;

import com.sparta.ordermanagement.application.exception.DeletedException;

public class OrderDeletedException extends DeletedException {

    public OrderDeletedException(String orderUuid) {
        super(String.format(ORDER_DELETED.getMessage(), orderUuid));
    }
}
