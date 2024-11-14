package com.sparta.ordermanagement.bootstrap.rest.exception.exceptions.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderErrorMessage {

    ORDER_NOT_FOUNT("상품이 존재하지 않습니다.: %s");

    private final String message;
}
