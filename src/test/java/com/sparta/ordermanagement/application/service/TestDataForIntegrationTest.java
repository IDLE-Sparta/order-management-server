package com.sparta.ordermanagement.application.service;

import com.sparta.ordermanagement.framework.persistence.entity.region.RegionEntity;
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

}
