package com.sparta.ordermanagement.application.service;

import com.sparta.ordermanagement.application.domain.order.Order;
import com.sparta.ordermanagement.application.domain.order.OrderState;
import com.sparta.ordermanagement.application.domain.product.Product;
import com.sparta.ordermanagement.application.domain.review.Review;
import com.sparta.ordermanagement.application.domain.shop.Shop;
import com.sparta.ordermanagement.application.domain.shop.ShopCategory;
import com.sparta.ordermanagement.application.domain.user.User;
import com.sparta.ordermanagement.framework.persistence.entity.order.OrderType;
import com.sparta.ordermanagement.framework.persistence.entity.product.ProductState;
import com.sparta.ordermanagement.framework.persistence.entity.region.RegionEntity;
import com.sparta.ordermanagement.framework.persistence.entity.user.Role;
import java.time.LocalDateTime;

public class TestDataForUnitTest {

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

    public static Order createOrder(String orderUuid, Shop shop, User customer) {
        return new Order(orderUuid, OrderState.PENDING, OrderType.ONLINE, "서울시 강남구 스파르타동",
            "수저 포크 필요 없어요." ,shop.getUuid(), shop.getShopName(), customer.getUserStringId(), LocalDateTime.now(), false);
    }

    public static Order createDeletedOrder(String orderUuid, Shop shop, User customer) {
        return new Order(orderUuid, OrderState.PENDING, OrderType.ONLINE, "서울시 강남구 스파르타동",
            "수저 포크 필요 없어요." ,shop.getUuid(), shop.getShopName(), customer.getUserStringId(), LocalDateTime.now(), true);
    }

    public static Review createReview(String reviewUuid, Integer rating, String content, Shop shop, User user) {
        return new Review(null, reviewUuid, rating, content, shop, user, false, LocalDateTime.now(),
            user.getUserStringId(), LocalDateTime.now(), user.getUserStringId());
    }

    public static Review createReviewWithoutTime(String reviewUuid, Integer rating, String content, Shop shop, User user) {
        return new Review(null, reviewUuid, rating, content, shop, user, false);
    }

    public static Review createDeletedReviewWithoutTime(String reviewUuid, Integer rating, String content, Shop shop, User user) {
        return new Review(null, reviewUuid, rating, content, shop, user, false);
    }
}
