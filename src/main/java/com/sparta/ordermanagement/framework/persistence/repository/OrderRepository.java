package com.sparta.ordermanagement.framework.persistence.repository;

import com.sparta.ordermanagement.framework.persistence.entity.order.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<OrderEntity, String> {

    @Query("SELECT o FROM p_order o WHERE o.orderUuid = :orderUuid")
    Optional<OrderEntity> findByOrderUuid(@Param("orderUuid") String orderUuid);

    @Query("SELECT o FROM p_order o WHERE o.orderUuid = :orderUuid AND o.isDeleted = false")
    Optional<OrderEntity> findByOrderUuidAndIsDeletedFalse(String orderUuid);
}
