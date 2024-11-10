package com.sparta.ordermanagement.application.service;

import com.sparta.ordermanagement.application.domain.shop.Shop;
import com.sparta.ordermanagement.application.domain.shop.ShopForCreate;
import com.sparta.ordermanagement.application.domain.shop.ShopForUpdate;
import com.sparta.ordermanagement.application.exception.shop.ShopIdInvalidException;
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

    public String updateShop(ShopForUpdate shopForUpdate) {

        Shop shop = validateShopIdAndGetShop(shopForUpdate.shopId());

        if (!shop.isSameCategory(shopForUpdate.shopCategoryId())) {
            shopCategoryService.validateCategoryId(shopForUpdate.shopCategoryId());
        }
        return shopOutputPort.updateShop(shopForUpdate);
    }

    private Shop validateShopIdAndGetShop(String shopId) {
        return shopOutputPort.findById(shopId)
            .orElseThrow(() -> new ShopIdInvalidException(shopId));
    }
}
