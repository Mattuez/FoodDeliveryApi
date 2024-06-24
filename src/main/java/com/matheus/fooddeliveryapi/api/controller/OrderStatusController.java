package com.matheus.fooddeliveryapi.api.controller;

import com.matheus.fooddeliveryapi.core.openapi.controllersDocumentation.OrderStatusOpenApi;
import com.matheus.fooddeliveryapi.core.security.CheckSecurity;
import com.matheus.fooddeliveryapi.domain.service.OrderTransitionStatusService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders/{orderId}")
public class OrderStatusController implements OrderStatusOpenApi {

    private OrderTransitionStatusService orderTransitionStatusService;


    public OrderStatusController(OrderTransitionStatusService orderTransitionStatusService) {
        this.orderTransitionStatusService = orderTransitionStatusService;
    }

    @Override
    @CheckSecurity.Order.canManage
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/confirmation")
    public void confirm(@PathVariable("orderId") String code) {
        orderTransitionStatusService.confirm(code);
    }

    @Override
    @CheckSecurity.Order.canManage
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/delivery")
    public void deliver(@PathVariable("orderId") String code) {
        orderTransitionStatusService.deliver(code);
    }

    @Override
    @CheckSecurity.Order.canManage
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/cancellation")
    public void cancel(@PathVariable("orderId") String code) {
        orderTransitionStatusService.cancel(code);
    }
}
