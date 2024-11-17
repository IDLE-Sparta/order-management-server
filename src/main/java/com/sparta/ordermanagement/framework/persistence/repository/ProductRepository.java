package com.sparta.ordermanagement.framework.persistence.repository;

import com.sparta.ordermanagement.framework.persistence.entity.product.ProductEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    Optional<ProductEntity> findByProductUuid(String productUuid);

    @Query("select p from p_product p "
        + "where p.productUuid = :productUuid "
        + "and p.productState = 'SHOw'"
        + "and p.isDeleted = false")
    Optional<ProductEntity> findByProductUuidAndIsDeletedFalseAndProductStateShow(String productUuid);
}
