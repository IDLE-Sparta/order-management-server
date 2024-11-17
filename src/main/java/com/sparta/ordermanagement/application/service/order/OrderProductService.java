package com.sparta.ordermanagement.application.service.order;

import com.sparta.ordermanagement.application.domain.orderproduct.OrderProduct;
import com.sparta.ordermanagement.application.output.order.OrderProductOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderProductService {

    private final OrderProductOutputPort orderProductOutputPort;

    public List<OrderProduct> findOrderProductsByOrderId(String orderId) {

        return orderProductOutputPort.findOrderProductsByOrderId(orderId);
    }
}
