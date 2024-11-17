package com.sparta.ordermanagement.bootstrap.rest.controller;

import com.sparta.ordermanagement.application.domain.payment.Payment;
import com.sparta.ordermanagement.application.domain.payment.PaymentForUpdate;
import com.sparta.ordermanagement.application.service.PaymentService;
import com.sparta.ordermanagement.bootstrap.auth.UserDetailsImpl;
import com.sparta.ordermanagement.bootstrap.rest.dto.payment.PaymentDetailResponse;
import com.sparta.ordermanagement.bootstrap.rest.dto.payment.PaymentRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "사용자 - 결제 관리", description = "CUSTOMER 이상의 권한 필요")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/orders/payments")
public class PaymentCommandController {

    private final PaymentService paymentService;

    @ResponseStatus(HttpStatus.CREATED)
    @PatchMapping()
    public PaymentDetailResponse processPayment(@RequestBody PaymentRequest paymentRequest,
                                                @AuthenticationPrincipal UserDetailsImpl userDetails) {

        PaymentForUpdate paymentForUpdate = paymentRequest.toDomain(userDetails.getUserStringId());
        Payment payment = paymentService.processPayment(paymentForUpdate);

        return PaymentDetailResponse.fromPayment(payment);
    }
}
