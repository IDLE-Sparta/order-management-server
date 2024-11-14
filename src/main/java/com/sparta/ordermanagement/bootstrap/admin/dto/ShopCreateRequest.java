package com.sparta.ordermanagement.bootstrap.admin.dto;

import com.sparta.ordermanagement.application.admin.vo.ShopForCreate;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ShopCreateRequest {

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
