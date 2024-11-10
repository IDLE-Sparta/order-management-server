package com.sparta.ordermanagement.application.domain.shop;

public record ShopForCreate(String shopCategoryId,
                            String shopName,
                            String ownerUserId,
                            String createdUserId) {

}
