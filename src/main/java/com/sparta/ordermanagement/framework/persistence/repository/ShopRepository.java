package com.sparta.ordermanagement.framework.persistence.repository;

import com.sparta.ordermanagement.framework.persistence.entity.shop.ShopEntity;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ShopRepository extends JpaRepository<ShopEntity, String> {

    @Query("""
        SELECT s FROM p_shop s
        JOIN FETCH s.shopCategoryEntity
        WHERE s.shopUuid = :shopId AND s.isDeleted = FALSE
        """)
    Optional<ShopEntity> findByIdWithCategory(@Param("shopId") String shopId);

    @Query("""
        SELECT s FROM p_shop s
        JOIN FETCH s.shopCategoryEntity
        WHERE s.shopCategoryEntity.id = :categoryId AND s.isDeleted = FALSE
        """)
    Page<ShopEntity> findAllByCategoryId(@Param("categoryId") String categoryId, Pageable pageable);

    @Query("""
        SELECT s FROM p_shop s
        JOIN FETCH s.shopCategoryEntity
        WHERE s.isDeleted = FALSE
        """)
    Page<ShopEntity> findAllByDeletedIsFalse(Pageable pageable);

    Optional<ShopEntity> findByShopUuid(String uuid);
}
