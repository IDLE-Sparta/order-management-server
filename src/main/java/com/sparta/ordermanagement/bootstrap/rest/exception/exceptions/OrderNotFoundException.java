package com.sparta.ordermanagement.bootstrap.rest.exception.exceptions;

import static com.sparta.ordermanagement.bootstrap.rest.exception.exceptions.message.OrderErrorMessage.ORDER_NOT_FOUNT;

public class OrderNotFoundException extends DataNotFoundException{

    public OrderNotFoundException(String orderUuid) {
        super(String.format(ORDER_NOT_FOUNT.getMessage(), orderUuid));
    }
}
