package com.sparta.ordermanagement.framework.persistence.repository;

import com.sparta.ordermanagement.framework.persistence.entity.shop.ShopEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ShopRepository extends JpaRepository<ShopEntity, String> {

    @Query("SELECT s FROM p_shop s JOIN FETCH s.shopCategoryEntity WHERE s.id = :shopId")
    Optional<ShopEntity> findByIdWithCategory(@Param("shopId") String shopId);
}
