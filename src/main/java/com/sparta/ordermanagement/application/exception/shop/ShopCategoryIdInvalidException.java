package com.sparta.ordermanagement.application.exception.shop;

import static com.sparta.ordermanagement.application.exception.message.ShopErrorMessage.CATEGORY_ID_INVALID;

import com.sparta.ordermanagement.application.exception.InvalidValueException;

public class ShopCategoryIdInvalidException extends InvalidValueException {

    public ShopCategoryIdInvalidException(String shopCategoryId) {
        super(CATEGORY_ID_INVALID.getMessage() + shopCategoryId);
    }
}
