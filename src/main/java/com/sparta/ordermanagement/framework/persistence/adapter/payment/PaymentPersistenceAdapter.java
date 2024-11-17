package com.sparta.ordermanagement.framework.persistence.adapter.payment;

import com.sparta.ordermanagement.application.domain.payment.Payment;
import com.sparta.ordermanagement.application.domain.payment.PaymentForUpdate;
import com.sparta.ordermanagement.application.exception.payment.PaymentFailedException;
import com.sparta.ordermanagement.application.exception.payment.PaymentProcessingException;
import com.sparta.ordermanagement.application.output.payment.PaymentOutputPort;
import com.sparta.ordermanagement.framework.persistence.entity.order.OrderEntity;
import com.sparta.ordermanagement.framework.persistence.entity.payment.PaymentEntity;
import com.sparta.ordermanagement.framework.persistence.repository.order.OrderRepository;
import com.sparta.ordermanagement.framework.persistence.repository.payment.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class PaymentPersistenceAdapter implements PaymentOutputPort {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    @Transactional
    @Override
    public String savePayment(String orderId) {

        OrderEntity orderEntity = orderRepository.findByOrderUuid(orderId).get();
        PaymentEntity paymentEntity = paymentRepository.save(PaymentEntity.from(orderEntity));
        orderEntity.updatePayment(paymentEntity);

        return paymentEntity.getPaymentUuid();
    }

    @Transactional
    @Override
    public Payment processPayment(PaymentForUpdate paymentForUpdate, int amount) {
        PaymentEntity paymentEntity = paymentRepository.findByPaymentUuid(paymentForUpdate.paymentUuid()).get();

        try {
            paymentEntity.processPayment(paymentForUpdate, amount);
        } catch (PaymentProcessingException e) {
            paymentEntity.failedState();

            throw new PaymentFailedException(e);
        }

        return  paymentEntity.toDomain();
    }
}
