package com.sparta.ordermanagement.application.output;

import com.sparta.ordermanagement.application.domain.shop.ShopForCreate;

public interface ShopOutputPort {

    String saveShop(ShopForCreate shopForCreate);
}
