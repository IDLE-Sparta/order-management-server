package com.sparta.ordermanagement.application.domain.shop;

public record ShopForUpdate(String shopId,
                            String shopCategoryId,
                            String shopName,
                            String updateUserId) {

}
