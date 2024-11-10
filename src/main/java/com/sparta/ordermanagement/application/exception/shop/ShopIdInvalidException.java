package com.sparta.ordermanagement.application.exception.shop;

import static com.sparta.ordermanagement.application.exception.message.ShopErrorMessage.SHOP_ID_INVALID;

import com.sparta.ordermanagement.application.exception.InvalidValueException;

public class ShopIdInvalidException extends InvalidValueException {

    public ShopIdInvalidException(String shopId) {
        super(String.format(SHOP_ID_INVALID.getMessage(), shopId));
    }
}
