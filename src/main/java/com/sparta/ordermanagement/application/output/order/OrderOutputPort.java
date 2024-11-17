package com.sparta.ordermanagement.application.output.order;

import com.sparta.ordermanagement.application.domain.order.Order;
import com.sparta.ordermanagement.application.domain.order.OrderForCreate;
import com.sparta.ordermanagement.application.domain.order.OrderForUpdate;
import com.sparta.ordermanagement.application.domain.order.TotalOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface OrderOutputPort {

    String saveOrder(OrderForCreate orderForCreate);

    Optional<TotalOrder> findByOrderIdAndUserId(String orderId, String userId);

    String updateOrderState(OrderForUpdate orderForUpdate);

    String cancelOrder(Order order, String orderId);

    Optional<Order> findByOrderUuid(String orderUuid);

    Page<TotalOrder> findByUserId(Pageable pageable, String userId);

    Optional<Order> findByOrderUuidAndIsDeletedFalse(String orderUuid);
}
