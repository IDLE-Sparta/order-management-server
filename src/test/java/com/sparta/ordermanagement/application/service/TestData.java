package com.sparta.ordermanagement.application.service;

import com.sparta.ordermanagement.application.domain.product.Product;
import com.sparta.ordermanagement.application.domain.shop.Shop;
import com.sparta.ordermanagement.application.domain.shop.ShopCategory;
import com.sparta.ordermanagement.application.domain.user.User;
import com.sparta.ordermanagement.framework.persistence.entity.product.ProductState;
import com.sparta.ordermanagement.framework.persistence.entity.region.RegionEntity;
import com.sparta.ordermanagement.framework.persistence.entity.user.Role;

public class TestData {

    public static User createUser(String userId, Role role, RegionEntity regionEntity) {
        return new User(null, userId, "qwer1234#", role, regionEntity);
    }

    public static ShopCategory createShopCategory(String categoryUuid, String categoryName) {
        return new ShopCategory(categoryUuid, categoryName);
    }

    public static Shop createShop(String shopUuid, ShopCategory shopCategory, String shopName, String ownerStringId) {
        return new Shop(null, shopUuid, shopCategory, shopName, 4.5, false, ownerStringId);
    }

    public static Product createProduct(String productUuid, String productName, Integer price, Shop shop) {
        return new Product(null, productUuid, productName, price, "맛있는 " + productName,
            ProductState.SHOW, shop, false);
    }

    public static Product createHiddenProduct(String productUuid, String productName, Integer price, Shop shop) {
        return new Product(null, productUuid, productName, price, "맛있는 " + productName,
            ProductState.HIDE, shop, false);
    }

    public static Product createDeleteProduct(String productUuid, String productName, int price, Shop shop) {
        return new Product(null, productUuid, productName, price, "맛있는 " + productName,
            ProductState.SHOW, shop, true);
    }
}
