package com.sparta.ordermanagement.framework.persistence.repository.payment;

import com.sparta.ordermanagement.framework.persistence.entity.payment.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<PaymentEntity, String> {

    Optional<PaymentEntity> findByPaymentUuid(String paymentUuid);
}
