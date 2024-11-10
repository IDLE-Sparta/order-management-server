package com.sparta.ordermanagement.application.service;

import com.sparta.ordermanagement.application.domain.shop.ShopForCreate;
import com.sparta.ordermanagement.application.output.ShopOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ShopService {

    private final ShopOutputPort shopOutputPort;
    private final ShopCategoryService shopCategoryService;

    public String createShop(ShopForCreate shopForCreate) {
        shopCategoryService.validateCategoryId(shopForCreate.shopCategoryId());
        return shopOutputPort.saveShop(shopForCreate);
    }
}
