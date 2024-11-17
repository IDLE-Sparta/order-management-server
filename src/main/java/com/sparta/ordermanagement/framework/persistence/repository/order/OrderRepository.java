package com.sparta.ordermanagement.framework.persistence.repository.order;

import com.sparta.ordermanagement.framework.persistence.entity.order.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Range;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<OrderEntity, String> {

    @Query("SELECT o FROM p_order o WHERE o.orderUuid = :orderUuid")
    Optional<OrderEntity> findByOrderUuid(@Param("orderUuid") String orderUuid);

    @Query("SELECT o FROM p_order o WHERE o.orderUuid = :orderUuid AND o.isDeleted = false")
    Optional<OrderEntity> findByOrderUuidAndIsDeletedFalse(String orderUuid);

    @Query("SELECT o FROM p_order o WHERE o.orderUuid =:orderUuid AND o.userEntity.userStringId = :userStringId")
    Optional<OrderEntity> findByOrderUuidAndUserEntity_UserStringId(@Param("orderUuid") String orderUuid, @Param("userStringId") String userStringId);

    @Query("SELECT o FROM p_order o WHERE o.userEntity.userStringId = :userStringId")
    Page<OrderEntity> findByUserEntity_UserStringId(@Param("userStringId") String userStringId, Pageable pageable);

    @Query("SELECT o FROM p_order o WHERE o.shopEntity.shopUuid = :shopUuid")
    Page<OrderEntity> findByShopEntity_ShopUuid(@Param("shopUuid") String shopUuid, Pageable pageable);
}
