package com.sparta.ordermanagement.bootstrap.admin.dto;

import com.sparta.ordermanagement.application.admin.vo.ShopForCreate;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ShopUpdateRequest {

    @NotBlank
    String shopCategoryId;

    @NotBlank
    String shopName;

    @NotBlank
    String ownerUserId;

    public ShopForCreate toDomain(String createdUserId) {
        return new ShopForCreate(shopCategoryId, shopName, ownerUserId, createdUserId);
    }
}
