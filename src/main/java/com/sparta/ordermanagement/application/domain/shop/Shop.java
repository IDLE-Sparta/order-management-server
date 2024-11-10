package com.sparta.ordermanagement.application.domain.shop;


public class Shop {

    private String id;
    private ShopCategory category;
    private String shopName;
    private double rating;

    public Shop(String id, ShopCategory category, String shopName, double rating) {
        this.id = id;
        this.category = category;
        this.shopName = shopName;
        this.rating = rating;
    }

    public boolean isSameCategory(String shopCategoryId) {
        return category.getId().equals(shopCategoryId);
    }

    public String getId() {
        return id;
    }

    public ShopCategory getCategory() {
        return category;
    }

    public String getShopName() {
        return shopName;
    }

    public double getRating() {
        return rating;
    }
}
