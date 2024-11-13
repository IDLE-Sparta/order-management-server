package com.sparta.ordermanagement.framework.admin.repository;

import com.sparta.ordermanagement.framework.persistence.entity.shop.ShopCategoryEntity;
import com.sparta.ordermanagement.framework.persistence.entity.shop.ShopEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminShopRepository extends JpaRepository<ShopEntity, Long> {

    Page<ShopEntity> findAllByShopNameContaining(String keyword, Pageable pageable);

    Page<ShopEntity> findAllByShopCategoryEntity(ShopCategoryEntity category, Pageable pageable);
}
