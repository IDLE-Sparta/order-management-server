package com.sparta.ordermanagement.framework.persistence.entity.order;

import com.sparta.ordermanagement.application.domain.order.*;
import com.sparta.ordermanagement.application.domain.orderproduct.OrderProduct;
import com.sparta.ordermanagement.bootstrap.admin.dto.OrderUpdateRequest;
import com.sparta.ordermanagement.framework.persistence.entity.BaseEntity;
import com.sparta.ordermanagement.framework.persistence.entity.orderproduct.OrderProductEntity;
import com.sparta.ordermanagement.framework.persistence.entity.payment.PaymentEntity;
import com.sparta.ordermanagement.framework.persistence.entity.shop.ShopEntity;
import com.sparta.ordermanagement.framework.persistence.entity.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "p_order")
public class OrderEntity extends BaseEntity {

    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, name = "uuid")
    private String orderUuid;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(255)")
    private OrderState orderState;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(255)")
    private OrderType orderType;

    @Column(columnDefinition = "varchar(255)")
    private String deliveryAddress;

    @Column(columnDefinition = "varchar(255)")
    private String requestOrder;

    @JoinColumn(nullable = false)
    @ManyToOne
    private ShopEntity shopEntity;

    @JoinColumn(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity userEntity;

    @JoinColumn
    @OneToOne(mappedBy = "orderEntity", fetch = FetchType.LAZY)
    private PaymentEntity paymentEntity;

    @OneToMany(mappedBy = "orderEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderProductEntity> orderProducts = new ArrayList<>();

    private OrderEntity(OrderState orderState,
                        OrderType orderType, String deliveryAddress,
                        String requestOrder, ShopEntity shopEntity, UserEntity userEntity) {

        id = null;
        this.orderState = orderState;
        this.orderType = orderType;
        this.deliveryAddress = deliveryAddress;
        this.requestOrder = requestOrder;
        this.shopEntity = shopEntity;
        this.userEntity = userEntity;
    }

    public OrderEntity(String orderUuid, OrderState orderState,
                        OrderType orderType, String deliveryAddress,
                        String requestOrder, ShopEntity shopEntity, UserEntity userEntity) {

        id = null;
        this.orderUuid = orderUuid;
        this.orderState = orderState;
        this.orderType = orderType;
        this.deliveryAddress = deliveryAddress;
        this.requestOrder = requestOrder;
        this.shopEntity = shopEntity;
        this.userEntity = userEntity;
    }

    @PrePersist
    private void prePersistence() {
        orderUuid = UUID.randomUUID().toString();
    }

    public Order toDomain() {

        return new Order(orderUuid, orderState, orderType,
                deliveryAddress, requestOrder, shopEntity.getShopUuid(),
                shopEntity.getShopName(), userEntity.getUserStringId(),
                super.getCreatedAt(), super.isDeleted());
    }

    public static OrderEntity from(OrderForCreate orderForCreate, ShopEntity shopEntity, UserEntity userEntity) {

        return new OrderEntity(
                orderForCreate.orderState(),
                orderForCreate.orderType(),
                orderForCreate.deliveryAddress(),
                orderForCreate.requestOrder(),
                shopEntity,
                userEntity);
    }

    public void updateState(OrderForUpdate orderForUpdate) {
        orderState = orderForUpdate.orderState();
        super.updateFrom(orderForUpdate.updateUserId());
    }

    public void cancelOrder(String userId) {

        this.orderState = OrderState.CANCELED;
        super.updateFrom(userId);
    }

    public void updateFrom(OrderUpdateRequest orderUpdateRequest, String userStringId) {

        orderState = orderUpdateRequest.getOrderState();
        orderType = orderUpdateRequest.getOrderType();
        super.updateFrom(userStringId);
    }

    public void deleteFrom(String deletedUserId) {
        super.deleteFrom(deletedUserId);
    }

    public void updatePayment(PaymentEntity paymentEntity) {
        this.paymentEntity = paymentEntity;
    }

    public TotalOrder totalOrder() {
        List<OrderProduct> orderProductList = orderProducts.stream().map(OrderProductEntity::toDomain)
                .toList();

        return new TotalOrder(orderUuid, userEntity.getUserStringId(), shopEntity.getShopUuid(),
                shopEntity.getShopName(), orderState, orderType, deliveryAddress, requestOrder,
                orderProductList, paymentEntity.getPaymentUuid(), paymentEntity.getPaymentState(),
                paymentEntity.getAmount(), paymentEntity.getPgProvider(), super.isDeleted());
    }
}
