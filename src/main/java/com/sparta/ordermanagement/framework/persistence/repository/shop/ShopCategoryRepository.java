package com.sparta.ordermanagement.framework.persistence.repository.shop;

import com.sparta.ordermanagement.framework.persistence.entity.shop.ShopCategoryEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopCategoryRepository extends JpaRepository<ShopCategoryEntity, String> {

    Optional<ShopCategoryEntity> findByShopCategoryName(String categoryName);
}
