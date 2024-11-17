package com.sparta.ordermanagement.application.output.order;

import com.sparta.ordermanagement.application.domain.orderproduct.OrderProduct;
import com.sparta.ordermanagement.framework.persistence.entity.orderproduct.OrderProductEntity;

import java.util.List;

public interface OrderProductOutputPort {
    List<OrderProduct> findOrderProductsByOrderId(String orderId);

    List<OrderProduct> findByOrder_OrderUuidIn(List<String> orderIds);
}
