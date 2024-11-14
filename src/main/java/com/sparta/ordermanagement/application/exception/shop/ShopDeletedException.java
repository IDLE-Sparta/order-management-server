package com.sparta.ordermanagement.application.exception.shop;

import static com.sparta.ordermanagement.application.exception.message.ShopErrorMessage.SHOP_DELETED;

import com.sparta.ordermanagement.application.exception.DeletedException;

public class ShopDeletedException extends DeletedException {

    public ShopDeletedException(String shopUuid) {
        super(String.format(SHOP_DELETED.getMessage(), shopUuid));
    }
}
