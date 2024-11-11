package com.sparta.ordermanagement.bootstrap.rest.dto.shop;

import com.sparta.ordermanagement.application.domain.shop.Shop;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ShopDetailResponse {

    private final String shopId;
    private final String shopName;
    private final String shopCategoryId;
    private final String categoryName;
    private final Double rating;

    public static ShopDetailResponse from(Shop shop) {
        return new ShopDetailResponse(
            shop.getUuid(),
            shop.getShopName(),
            shop.getCategory().getId(),
            shop.getCategory().getShopCategoryName(),
            shop.getRating()
        );
    }
}
