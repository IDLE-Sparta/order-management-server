package com.sparta.ordermanagement.bootstrap.rest.controller;

import com.sparta.ordermanagement.application.domain.order.OrderForCreate;
import com.sparta.ordermanagement.application.domain.order.OrderForUpdate;
import com.sparta.ordermanagement.application.domain.order.OrderPayment;
import com.sparta.ordermanagement.application.service.OrderService;
import com.sparta.ordermanagement.bootstrap.auth.UserDetailsImpl;
import com.sparta.ordermanagement.bootstrap.rest.dto.order.OrderCreateRequest;
import com.sparta.ordermanagement.bootstrap.rest.dto.order.OrderDetailResponse;
import com.sparta.ordermanagement.bootstrap.rest.dto.order.OrderUpdateRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "사용자 - 주문 관리", description = "CUSTOMER 이상의 권한 필요")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/orders")
public class OrderCommandController {

    private final OrderService orderService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public OrderDetailResponse createOrder(@RequestBody OrderCreateRequest orderCreateRequest,
                                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        /* 반환 타입 수정*/
        OrderForCreate orderForCreate = orderCreateRequest.toDomain(userDetails.getUserStringId());
        OrderPayment orderPayment =  orderService.createOrder(orderForCreate);

        return OrderDetailResponse.from(orderPayment);
    }

    @PreAuthorize("!hasRole('CUSTOMER')")
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{orderUuid}")
    public String updateOrderState(@PathVariable(value ="orderUuid") String orderId,
                                   @RequestBody OrderUpdateRequest orderUpdateRequest,
                                   @AuthenticationPrincipal UserDetailsImpl userDetails) {

        OrderForUpdate orderForUpdate = orderUpdateRequest.toDomain(orderId, userDetails.getUserStringId());
        return orderService.updateOrderState(orderForUpdate);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("{orderId}/cancel")
    public String cancelOrder(@PathVariable("orderId") String orderId,
                              @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return orderService.cancelOrder(orderId, userDetails.getUserStringId());
    }
}
