package com.sparta.ordermanagement.bootstrap.rest.exception.exceptions;

public class ShopNotFoundException extends DataNotFoundException {

    public ShopNotFoundException(String shopId) {
        super("shopId에 해당하는 가게가 존재하지 않습니다. : " + shopId);
    }
}
