package com.sparta.ordermanagement.application.service;

import com.sparta.ordermanagement.application.domain.order.*;
import com.sparta.ordermanagement.application.domain.user.User;
import com.sparta.ordermanagement.application.exception.order.InvalidOrderException;
import com.sparta.ordermanagement.application.exception.order.OrderCancellationTimeExceededException;
import com.sparta.ordermanagement.application.exception.order.OrderMismatchReviewerException;
import com.sparta.ordermanagement.application.exception.order.OrderStateChangedException;
import com.sparta.ordermanagement.application.exception.order.OrderDeletedException;
import com.sparta.ordermanagement.application.exception.order.OrderUuidInvalidException;
import com.sparta.ordermanagement.application.exception.order.UnauthorizedAccessException;
import com.sparta.ordermanagement.application.exception.payment.UserIdInvalidException;
import com.sparta.ordermanagement.application.output.OrderOutputPort;
import com.sparta.ordermanagement.application.output.UserOutputPort;
import com.sparta.ordermanagement.framework.persistence.entity.order.OrderType;
import com.sparta.ordermanagement.framework.persistence.entity.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderOutputPort orderOutPutPort;
    private final UserOutputPort userOutputPort;
    private final ShopService shopService;
    private final ProductService productService;
    private final PaymentService paymentService;

    /* User, Product 기능 통합 후 각 id 검증 메서드 추가 필요
     * Return 타입 변경해서 orderId, paymentId를 같이 반환하도록 수정*/
    public OrderPayment createOrder(OrderForCreate orderForCreate) {
        /* 유저 검증 후 UserRole에 따라 orderType 확인 후 권한에 맞지 않으면 주문 등록 못하게 수정 */
        User user = validateUserIdAndGet(orderForCreate.userId());
        checkUserRoleForOrderType(user.getRole(), orderForCreate.orderType());

        productService.validateProductsAndGetProductList(orderForCreate.productList());
        // OrderService가 ShopService를 의존성 주입하지 않고도 검증할 수 있도록 리팩토링 필요
        shopService.validateShopIdAndGetShop(orderForCreate.shopId());

        String orderId = orderOutPutPort.saveOrder(orderForCreate);
        String paymentId = paymentService.createPayment(orderId);

        return new OrderPayment(orderId, paymentId);
    }

    /* 주문 상태 수정 시 권한 확인*/
    public String updateOrderState(OrderForUpdate orderForUpdate) {
        /* 주문 상태가 cancel일 경우 어디서 못하게 막는 게 좋을까?*/
        validateOrderIdAndGetOrder(orderForUpdate.orderId());

        if (orderForUpdate.orderState().equals(OrderState.CANCELED)) {
            throw new OrderStateChangedException(orderForUpdate.orderId());
        }

        return orderOutPutPort.updateOrderState(orderForUpdate);
    }

    public String cancelOrder(String orderId, String userId) {
        Order order = validateOrderIdAndGetOrder(orderId);
        paymentService.validateOrderCheckCanceled(order);
        validateOrderOwnership(userId, order.getUserId());

        LocalDateTime orderTime = order.getCreatedAt();
        LocalDateTime currentTime = LocalDateTime.now();
        Duration duration = Duration.between(orderTime, currentTime);

        if (duration.toMinutes() > 5) {
            throw new OrderCancellationTimeExceededException(orderId);
        }

        return orderOutPutPort.cancelOrder(order);
    }

    private Order validateOrderIdAndGetOrder(String orderId) {
        return orderOutPutPort.findByOrderId(orderId)
                .orElseThrow(() -> new InvalidOrderException(orderId));
    }

    public Order validateOrderUuidAndGetOrder(String orderId) {
        return orderOutPutPort.findByOrderId(orderId)
                .orElseThrow(() -> new OrderUuidInvalidException(orderId));
    }

    public Order validateOrderUuidAndGetNotDeletedOrder(String orderUuid) {
        Order order = validateOrderUuidAndGetOrder(orderUuid);
        if (order.getIsDeleted()) {
            throw new OrderDeletedException(orderUuid);
        }
        return order;
    }

    private User validateUserIdAndGet(String userId) {
        return userOutputPort.findByUserStringId(userId)
                .orElseThrow(() -> new UserIdInvalidException(userId));
    }

    private void checkUserRoleForOrderType(Role role, OrderType orderType) {
        if (orderType == OrderType.OFFLINE && role == Role.CUSTOMER) {
            throw new UnauthorizedAccessException();
        }
    }

    private void validateOrderOwnership(String userId, String orderUserId) {
        if (orderUserId.equals(userId)) {
            return;
        }
        throw new UnauthorizedAccessException();
    }

    public void validateOrderBelongToUser(Order order, String userStringId) {
        if (!order.isSameReviewer(userStringId)) {
            throw new OrderMismatchReviewerException(userStringId);
        }
    }
}
