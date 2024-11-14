package com.sparta.ordermanagement.application.domain.shop;


public class Shop {

    private Long id;
    private String uuid;
    private ShopCategory category;
    private String shopName;
    private double rating;
    private boolean isDeleted;

    public Shop(Long id, String uuid, ShopCategory category, String shopName, double rating, boolean isDeleted) {
        this.id = id;
        this.uuid = uuid;
        this.category = category;
        this.shopName = shopName;
        this.rating = rating;
        this.isDeleted = isDeleted;
    }

    public boolean isSameCategory(String shopCategoryId) {
        return category.getId().equals(shopCategoryId);
    }

    public Long getId() {
        return id;
    }

    public String getUuid() {
        return uuid;
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

    public boolean getIsDeleted() {
        return isDeleted;
    }
}
