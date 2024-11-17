package com.sparta.ordermanagement.bootstrap.admin.controller;

import com.sparta.ordermanagement.application.admin.AdminOrderService;
import com.sparta.ordermanagement.bootstrap.admin.dto.OrderResponse;
import com.sparta.ordermanagement.bootstrap.admin.dto.OrderUpdateRequest;
import com.sparta.ordermanagement.bootstrap.admin.dto.OrderUpdateResponse;
import com.sparta.ordermanagement.bootstrap.auth.UserDetailsImpl;
import com.sparta.ordermanagement.bootstrap.rest.pagination.offset.PaginationConstraint;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "관리자 - 주문 관리", description = "MANAGER 이상의 권한 필요")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/v1/orders")
public class adminOrderController {

    private final AdminOrderService adminOrderService;

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{orderId}")
    @PreAuthorize("hasAnyRole('MANAGER', 'MASTER')")
    public OrderUpdateResponse adminUpdateOrder(@PathVariable String orderId, @RequestBody OrderUpdateRequest orderUpdateRequest,
                                                @AuthenticationPrincipal UserDetailsImpl userDetails) {

        System.out.println(orderUpdateRequest.getOrderState());
        System.out.println(orderUpdateRequest.getOrderType());
        return adminOrderService.updateOrder(orderId, orderUpdateRequest, userDetails.getUserStringId());
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{orderId}")
    @PreAuthorize("hasAnyRole('MANAGER', 'MASTER')")
    public String DeleteOrder(@PathVariable String orderId,
                              @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return adminOrderService.deleteOrder(orderId, userDetails.getUserStringId());
    }

    @PaginationConstraint
    @GetMapping
    @PreAuthorize("hasAnyRole('MANAGER', 'MASTER')")
    public Page<OrderResponse> findAll(Pageable pageable) {

        return adminOrderService.findAll(pageable)
                .map(OrderResponse::from);
    }

    @PaginationConstraint

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{orderId}")
    @PreAuthorize("hasAnyRole('MANAGER', 'MASTER')")
    public OrderResponse findByOrderId(@PathVariable String orderId) {

        return adminOrderService.findOrder(orderId);
    }
}
