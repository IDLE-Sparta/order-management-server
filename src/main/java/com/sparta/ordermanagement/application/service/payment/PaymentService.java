package com.sparta.ordermanagement.application.service.payment;

import com.sparta.ordermanagement.application.domain.order.Order;
import com.sparta.ordermanagement.application.domain.order.OrderState;
import com.sparta.ordermanagement.application.domain.orderproduct.OrderProduct;
import com.sparta.ordermanagement.application.domain.payment.Payment;
import com.sparta.ordermanagement.application.domain.payment.PaymentForUpdate;
import com.sparta.ordermanagement.application.exception.order.InvalidOrderException;
import com.sparta.ordermanagement.application.exception.order.OrderStateChangedException;
import com.sparta.ordermanagement.application.exception.payment.UserIdInvalidException;
import com.sparta.ordermanagement.application.exception.payment.UserOrderInvalidException;
import com.sparta.ordermanagement.application.output.order.OrderOutputPort;
import com.sparta.ordermanagement.application.output.payment.PaymentOutputPort;
import com.sparta.ordermanagement.application.output.user.UserOutputPort;
import com.sparta.ordermanagement.application.service.order.OrderProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PaymentService {

    private final PaymentOutputPort paymentOutputPort;
    private final UserOutputPort userOutputPort;
    private final OrderOutputPort orderOutputPort;
    private final OrderProductService orderProductService;

    public String createPayment(String orderId) {

        return paymentOutputPort.savePayment(orderId);
    }

    /* 결제를 위해 update를 보내면 orderId를 통해 orderEntity의 orderProduct 정보를 가져와서 결제 금액을 산정
    * 그러려면 여기서도 orderOutPort의 역할이 필요하다 혹은 orderService에서 기능을 만들고 불러와서 쓴다.
    * 또 다른 방안 orderProductService를 만들고 orderId를 기준으로 해당하는 OrderProduct 정보를 가져온다.*/
    public Payment processPayment(PaymentForUpdate paymentForUpdate) {

        Order order = OrderUserIdInvalidAndGetOrder(paymentForUpdate.orderUuid(), paymentForUpdate.updatedUserId());
        validateOrderCheckCanceled(order);
        List<OrderProduct> orderProducts = orderProductService.findOrderProductsByOrderId(paymentForUpdate.orderUuid());

        int amount = calculateTotalOrderPrice(orderProducts);
        return paymentOutputPort.processPayment(paymentForUpdate, amount);
    }

    private Order OrderUserIdInvalidAndGetOrder(String orderId, String userId) {
        userOutputPort.findByUserStringId(userId)
                .orElseThrow(() -> new UserIdInvalidException(userId));

        Order order = orderOutputPort.findByOrderUuid(orderId)
                .orElseThrow(() -> new InvalidOrderException(orderId));

        if (order.getUserId().equals(userId)) {
            return order;
        }

        throw new UserOrderInvalidException(userId);
    }

    private int calculateTotalOrderPrice(List<OrderProduct> orderProducts) {
        return orderProducts.stream().mapToInt(OrderProduct::getOrderPrice).sum();
                /* count 랑 OrderPrice 합쳐서 계산 할 경우
                .mapToInt(orderProduct -> orderProduct.getCount() * orderProduct.getOrderPrice())
                .sum(); */
    }

    public void validateOrderCheckCanceled(Order order) {
        if (order.getOrderState().equals(OrderState.CANCELED)) {
            throw new OrderStateChangedException(order.getOrderUuid());
        }
    }
}
