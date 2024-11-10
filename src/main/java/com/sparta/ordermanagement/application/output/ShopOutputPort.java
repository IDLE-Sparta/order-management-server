package com.sparta.ordermanagement.application.output;

import com.sparta.ordermanagement.application.domain.shop.Shop;
import com.sparta.ordermanagement.application.domain.shop.ShopForCreate;
import com.sparta.ordermanagement.application.domain.shop.ShopForUpdate;
import java.util.Optional;

public interface ShopOutputPort {

    String saveShop(ShopForCreate shopForCreate);

    Optional<Shop> findById(String shopId);

    String updateShop(ShopForUpdate shopForUpdate);
}
