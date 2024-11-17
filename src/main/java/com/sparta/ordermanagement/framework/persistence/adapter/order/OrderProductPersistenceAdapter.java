package com.sparta.ordermanagement.framework.persistence.adapter.order;

import com.sparta.ordermanagement.application.domain.orderproduct.OrderProduct;
import com.sparta.ordermanagement.application.output.order.OrderProductOutputPort;
import com.sparta.ordermanagement.framework.persistence.entity.orderproduct.OrderProductEntity;
import com.sparta.ordermanagement.framework.persistence.repository.order.OrderProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class OrderProductPersistenceAdapter implements OrderProductOutputPort {

    private final OrderProductRepository orderProductRepository;

    @Transactional
    @Override
    public List<OrderProduct> findOrderProductsByOrderId(String orderId) {

        return orderProductRepository.findByOrderEntity_OrderUuid(orderId)
                .stream().map(OrderProductEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public List<OrderProduct> findByOrder_OrderUuidIn(List<String> orderIds) {
        return orderProductRepository.findByOrderEntity_OrderUuidIn(orderIds)
                .stream().map(OrderProductEntity::toDomain)
                .collect(Collectors.toList());
    }
}
