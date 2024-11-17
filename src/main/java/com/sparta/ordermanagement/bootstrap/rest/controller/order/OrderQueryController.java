package com.sparta.ordermanagement.bootstrap.rest.controller.order;

import com.sparta.ordermanagement.application.domain.order.TotalOrder;
import com.sparta.ordermanagement.bootstrap.auth.UserDetailsImpl;
import com.sparta.ordermanagement.bootstrap.rest.dto.order.OrderResponse;
import com.sparta.ordermanagement.bootstrap.rest.exception.exceptions.OrderNotFoundException;
import com.sparta.ordermanagement.bootstrap.rest.pagination.offset.PaginationConstraint;
import com.sparta.ordermanagement.framework.persistence.adapter.order.OrderPersistenceAdapter;
import com.sparta.ordermanagement.framework.persistence.adapter.shop.ShopPersistenceAdapter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "사용자 - 주문 조회", description = "CUSTOMER 이상의 권한 필요")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/orders")
public class OrderQueryController {

    private final OrderPersistenceAdapter orderPersistenceAdapter;
    private final ShopPersistenceAdapter shopPersistenceAdapter;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{orderId}")
    public OrderResponse findOrder(@PathVariable(value = "orderId") String orderId,
                                   @AuthenticationPrincipal UserDetailsImpl userDetails) {

        TotalOrder totalOrder = orderPersistenceAdapter.findByOrderIdAndUserId(orderId, userDetails.getUserStringId())
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        return OrderResponse.from(totalOrder);
    }

    @ResponseStatus(HttpStatus.OK)
    @PaginationConstraint
    @GetMapping
    public Page<OrderResponse> findAll(Pageable pageable, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return orderPersistenceAdapter
                .findByUserId(pageable, userDetails.getUserStringId()).map(OrderResponse::from);

    }

    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('OWNER')")
    @PaginationConstraint
    @GetMapping("/owner")
    public Page<OrderResponse> findAllByShop(Pageable pageable, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        String shopId = shopPersistenceAdapter.findByUserId(userDetails.getUserStringId());

        return orderPersistenceAdapter.findByShopId(shopId, pageable).map(OrderResponse::from);
    }
}
