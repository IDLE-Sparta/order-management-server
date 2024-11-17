package com.sparta.ordermanagement.framework.persistence.repository.order;

import com.sparta.ordermanagement.framework.persistence.entity.orderproduct.OrderProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderProductRepository extends JpaRepository<OrderProductEntity, String> {

    @Query("SELECT op FROM p_order_product op WHERE op.orderEntity.orderUuid = :orderUuid")
    List<OrderProductEntity> findByOrderEntity_OrderUuid(@Param("orderUuid")String orderId);

    @Query("SELECT op FROM p_order_product op WHERE op.orderEntity.orderUuid IN :orderUuids")
    List<OrderProductEntity> findByOrderEntity_OrderUuidIn(@Param("orderUuids")List<String> orderIds);
}
