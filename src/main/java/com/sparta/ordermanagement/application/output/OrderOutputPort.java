package com.sparta.ordermanagement.application.output;

import com.sparta.ordermanagement.application.domain.order.Order;
import com.sparta.ordermanagement.application.domain.order.OrderForCreate;
import com.sparta.ordermanagement.application.domain.order.OrderForUpdate;
import java.util.Optional;

public interface OrderOutputPort {

    Order saveOrder(OrderForCreate orderForCreate);

    Optional<Order> findByOrderId(String orderId);

    String updateOrderState(OrderForUpdate orderForUpdate);

    String cancelOrder(Order order);

    Optional<Order> findByOrderUuid(String orderUuid);
}
