package com.sparta.ordermanagement.application.service;

import com.sparta.ordermanagement.application.domain.order.OrderState;
import com.sparta.ordermanagement.application.domain.product.Product;
import com.sparta.ordermanagement.framework.persistence.entity.order.OrderEntity;
import com.sparta.ordermanagement.framework.persistence.entity.order.OrderType;
import com.sparta.ordermanagement.framework.persistence.entity.product.ProductEntity;
import com.sparta.ordermanagement.framework.persistence.entity.product.ProductState;
import com.sparta.ordermanagement.framework.persistence.entity.region.RegionEntity;
import com.sparta.ordermanagement.framework.persistence.entity.review.ReviewEntity;
import com.sparta.ordermanagement.framework.persistence.entity.shop.ShopCategoryEntity;
import com.sparta.ordermanagement.framework.persistence.entity.shop.ShopEntity;
import com.sparta.ordermanagement.framework.persistence.entity.user.Role;
import com.sparta.ordermanagement.framework.persistence.entity.user.UserEntity;

public class TestDataForIntegrationTest {

    public static RegionEntity createRegionEntity() {
        return new RegionEntity(null, "서울시 강남구 스파르타동");
    }

    public static UserEntity createUserEntity(String userStringId, Role role,
        RegionEntity regionEntity) {
        return new UserEntity(null, userStringId, "qwer1234#", role, regionEntity);
    }

    public static ShopCategoryEntity createShopCategoryEntity(String categoryName) {
        return new ShopCategoryEntity(null, categoryName);
    }

    public static ShopEntity createShopEntity(String shopUuid, UserEntity userEntity,
        String shopName, ShopCategoryEntity shopCategoryEntity) {
        return new ShopEntity(null, shopUuid, userEntity, shopName, 4.5, 300, shopCategoryEntity);
    }

    public static ProductEntity createProductEntity(String productUuid, String productName,
        Integer productPrice, String productDescription, ShopEntity shopEntity) {
        return new ProductEntity(null, productUuid, productName, productPrice, productDescription,
            ProductState.SHOW, shopEntity);
    }

    public static OrderEntity createOrderEntity(String orderUuid, ShopEntity shopEntity, UserEntity userEntity) {
        return new OrderEntity(orderUuid, OrderState.PENDING, OrderType.ONLINE,
            "서울시 강남구 스파르타동", "초인종 눌러주세요.", shopEntity, userEntity);
    }

    public static ReviewEntity createReviewEntity(String reviewUuid, Integer rating, String content,
        ShopEntity shopEntity, UserEntity userEntity) {
        return new ReviewEntity(null, reviewUuid, rating, content, shopEntity, userEntity);
    }
}
