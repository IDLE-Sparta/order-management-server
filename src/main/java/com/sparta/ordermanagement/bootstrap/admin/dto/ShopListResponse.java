package com.sparta.ordermanagement.bootstrap.admin.dto;

import com.sparta.ordermanagement.framework.persistence.entity.shop.ShopEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ShopListResponse {

    private Long shopId;
    private String shopUuid;
    private String userId;
    private String shopName;
    private double rating;

    private String shopCategoryId;
    private String shopCategoryName;

    private History history;

    public static ShopListResponse from(ShopEntity shopEntity) {
        return new ShopListResponse(
            shopEntity.getId(),
            shopEntity.getShopUuid(),
            shopEntity.getUserEntity().getUserStringId(),
            shopEntity.getShopName(),
            shopEntity.getRating(),
            shopEntity.getShopCategoryEntity().getId(),
            shopEntity.getShopCategoryEntity().getShopCategoryName(),
            History.from(shopEntity)
        );
    }
}
