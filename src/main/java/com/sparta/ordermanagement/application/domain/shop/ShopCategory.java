package com.sparta.ordermanagement.application.domain.shop;

public class ShopCategory {

    private String id;
    private String shopCategoryName;

    public ShopCategory(String id, String shopCategoryName) {
        this.id = id;
        this.shopCategoryName = shopCategoryName;
    }

    public String getId() {
        return id;
    }

    public String getShopCategoryName() {
        return shopCategoryName;
    }
}
