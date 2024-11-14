package com.sparta.ordermanagement.framework.admin.repository;

import com.sparta.ordermanagement.framework.persistence.entity.shop.ShopCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminShopCategoryRepository extends JpaRepository<ShopCategoryEntity, String> {

}
