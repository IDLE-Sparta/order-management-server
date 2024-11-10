package com.sparta.ordermanagement.bootstrap.rest.dto.shop;

import com.sparta.ordermanagement.application.domain.shop.ShopForUpdate;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ShopUpdateRequest {

    @NotBlank
    private String shopCategoryId;

    @NotBlank
    private String shopName;

    public ShopForUpdate toDomain(String shopId, String updatedUserId) {
        return new ShopForUpdate(shopId, shopCategoryId, shopName, updatedUserId);
    }
}
