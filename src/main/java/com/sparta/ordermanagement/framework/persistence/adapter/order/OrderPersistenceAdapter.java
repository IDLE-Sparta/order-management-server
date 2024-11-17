package com.sparta.ordermanagement.framework.persistence.adapter.order;

import com.sparta.ordermanagement.application.domain.order.Order;
import com.sparta.ordermanagement.application.domain.order.OrderForCreate;
import com.sparta.ordermanagement.application.domain.order.OrderForUpdate;
import com.sparta.ordermanagement.application.domain.order.TotalOrder;
import com.sparta.ordermanagement.application.exception.order.ProductIdInvalidException;
import com.sparta.ordermanagement.application.output.order.OrderOutputPort;
import com.sparta.ordermanagement.framework.persistence.entity.order.OrderEntity;
import com.sparta.ordermanagement.framework.persistence.entity.orderproduct.OrderProductEntity;
import com.sparta.ordermanagement.framework.persistence.entity.product.ProductEntity;
import com.sparta.ordermanagement.framework.persistence.entity.shop.ShopEntity;
import com.sparta.ordermanagement.framework.persistence.entity.user.UserEntity;

import com.sparta.ordermanagement.framework.persistence.repository.order.OrderProductRepository;
import com.sparta.ordermanagement.framework.persistence.repository.order.OrderRepository;
import com.sparta.ordermanagement.framework.persistence.repository.product.ProductRepository;
import com.sparta.ordermanagement.framework.persistence.repository.shop.ShopRepository;
import com.sparta.ordermanagement.framework.persistence.repository.user.UserRepository;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class OrderPersistenceAdapter implements OrderOutputPort {

    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ShopRepository shopRepository;

    public Optional<Order> findByOrderUuid(String orderUuid) {
        return orderRepository.findByOrderUuid(orderUuid)
            .map(OrderEntity::toDomain)
            .or(Optional::empty);
    }

    public Optional<Order> findByOrderUuidAndIsDeletedFalse(String orderUuid) {
        return orderRepository.findByOrderUuidAndIsDeletedFalse(orderUuid)
            .map(OrderEntity::toDomain)
            .or(Optional::empty);
    }

    @Transactional
    @Override
    public String saveOrder(OrderForCreate orderForCreate) {

        ShopEntity shopEntity = shopRepository.findByShopUuid(orderForCreate.shopId()).get();
        UserEntity userEntity = userRepository.findByUserStringId(orderForCreate.userId()).get();
        OrderEntity orderEntity = orderRepository.save(OrderEntity.from(orderForCreate, shopEntity, userEntity));
        createOrderWithProducts(orderForCreate, orderEntity);

        return orderEntity.getOrderUuid();
    }

    @Override
    public Optional<TotalOrder> findByOrderIdAndUserId(String orderId, String userId) {
        return orderRepository.findByOrderUuidAndUserEntity_UserStringId(orderId, userId)
                .map(OrderEntity::totalOrder)
                .or(Optional::empty);
    }

    @Transactional
    @Override
    public String updateOrderState(OrderForUpdate orderForUpdate) {
        OrderEntity orderEntity = orderRepository.findByOrderUuid(orderForUpdate.orderId()).get();
        orderEntity.updateState(orderForUpdate);

        return orderEntity.getOrderUuid();
    }

    @Transactional
    @Override
    public String cancelOrder(Order order, String userId) {
        OrderEntity orderEntity = orderRepository.findByOrderUuid(order.getOrderUuid()).get();
        orderEntity.cancelOrder(userId);

        return orderEntity.getOrderUuid();
    }

    private void createOrderWithProducts(OrderForCreate orderForCreate, OrderEntity orderEntity) {
        List<OrderProductEntity> orderProductEntities = orderForCreate.productList().stream()
                .map(orderProductForCreate -> {
                    ProductEntity productEntity = productRepository.findByProductUuid(orderProductForCreate.productId())
                            .orElseThrow(() -> new ProductIdInvalidException(orderProductForCreate.productId()));

                    return OrderProductEntity.from(orderForCreate, orderEntity, productEntity);
                })
                .collect(Collectors.toList());

        orderProductRepository.saveAll(orderProductEntities);
    }

    public Page<TotalOrder> findByUserId(Pageable pageable, String userId) {
        return orderRepository.findByUserEntity_UserStringId(userId, pageable)
                .map(OrderEntity::totalOrder);
    }

    public Page<TotalOrder> findByShopId(String shopId, Pageable pageable) {
        return orderRepository.findByShopEntity_ShopUuid(shopId, pageable)
                .map(OrderEntity::totalOrder);
    }
}
