package com.sparta.ordermanagement.bootstrap.rest.exception.exceptions.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductErrorMessage {

    PRODUCT_NOT_FOUND("존재하지 않는 상품입니다.: %s");

    private final String message;
}
