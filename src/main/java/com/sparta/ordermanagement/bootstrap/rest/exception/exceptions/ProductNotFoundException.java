package com.sparta.ordermanagement.bootstrap.rest.exception.exceptions;

import static com.sparta.ordermanagement.bootstrap.rest.exception.exceptions.message.ProductErrorMessage.PRODUCT_NOT_FOUND;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductNotFoundException extends DataNotFoundException {

    public ProductNotFoundException(String productUuid) {
        super(String.format(PRODUCT_NOT_FOUND.getMessage(), productUuid));
    }
}
