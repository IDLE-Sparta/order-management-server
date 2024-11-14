package com.sparta.ordermanagement.application.service;

import com.sparta.ordermanagement.application.domain.order.Order;
import com.sparta.ordermanagement.application.domain.order.OrderForCreate;
import com.sparta.ordermanagement.application.domain.order.OrderForUpdate;
import com.sparta.ordermanagement.application.domain.order.OrderState;
import com.sparta.ordermanagement.application.exception.order.InvalidOrderException;
import com.sparta.ordermanagement.application.exception.order.OrderCancellationTimeExceededException;
import com.sparta.ordermanagement.application.exception.order.OrderStateChangedException;
import com.sparta.ordermanagement.application.exception.order.OrderDeletedException;
import com.sparta.ordermanagement.application.exception.order.OrderUuidInvalidException;
import com.sparta.ordermanagement.application.output.OrderOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderOutputPort orderOutPutPort;
    private final ShopService shopService;
    private final ProductService productService;

    /* User, Product 기능 통합 후 각 id 검증 메서드 추가 필요*/
    public String createOrder(OrderForCreate orderForCreate) {
        /* User ID 기반 검증은 User 기능과 통합 후 사용 - 현재는 생략*/
        /* 유저 권한에 따라 ON/OFF 상태 확인하는 검증 메서드 추가 필요*/

        productService.validateProductUuidAndGetProduct(orderForCreate.productId());

        // OrderService가 ShopService를 의존성 주입하지 않고도 검증할 수 있도록 리팩토링 필요
        shopService.validateShopIdAndGetShop(orderForCreate.shopId());

        return orderOutPutPort.saveOrder(orderForCreate).getOrderUuid();
    }

    /* 업데이트 기능
    * 1. 주문 상태 변경 승인, 완료, 취소 - 취소는 주문 등록 후 5분 이내일 때만 가능
    * 2. 주문상품 변경 - 변경(기존 상품에서 다른 상품으로 변경일 때와 추가일 때 차이 고려), 추가
    * 3. 수량 변경 - 수량 변경에서 주문상품 변경은 해당 상품의 수량을 0으로 처리하는 방법 고려
    * 4. 주문 취소 되었을 때 결제도 취소되도록 설정되어야 함 - 아직 결제 없으니까 추후 생길 때 이 부분 추가
    *  수정할 때 주문 상태의 경우 CUSTOMER는 취소만 가능하게 OWNER부터 모든 상태 변경 가능하도록
    * 주문 상품 변경에 대한 서비스 코드는 OrderProductService 에서 가져와서 쓰기, 이 서비스 코드에서는 주문 상태 변경만 고려*/
    public String updateOrderState(OrderForUpdate orderForUpdate) {

        /* 주문 상태가 cancel일 경우 어디서 못하게 막는 게 좋을까?*/
        validateOrderIdAndGetOrder(orderForUpdate.orderId());

        if (orderForUpdate.orderState().equals(OrderState.CANCELED)) {
            throw new OrderStateChangedException(orderForUpdate.orderId());
        }

        return orderOutPutPort.updateOrderState(orderForUpdate);
    }

    public String cancelOrder(String orderId) {
        // 유저 검증 부분 일단 생략 - 5분 제한 시간 확인은 어디서 하는 게 좋을까?
        Order order = validateOrderIdAndGetOrder(orderId);

        // 일단 order에서 생성 시간을 가져왔다고 치고 기능 개발
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
}
