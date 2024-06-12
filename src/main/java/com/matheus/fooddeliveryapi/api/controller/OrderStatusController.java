package com.matheus.fooddeliveryapi.api.controller;

import com.matheus.fooddeliveryapi.domain.service.OrderTransitionStatusService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders/{orderId}")
public class OrderStatusController {

    private OrderTransitionStatusService orderTransitionStatusService;


    public OrderStatusController(OrderTransitionStatusService orderTransitionStatusService) {
        this.orderTransitionStatusService = orderTransitionStatusService;
    }

    @PutMapping("/confirmation")
    public void confirm(@PathVariable("orderId") String code) {
        orderTransitionStatusService.confirm(code);
    }

    @PutMapping("/delivery")
    public void deliver(@PathVariable("orderId") String code) {
        orderTransitionStatusService.deliver(code);
    }

    @PutMapping("/cancellation")
    public void cancel(@PathVariable("orderId") String code) {
        orderTransitionStatusService.cancel(code);
    }
}
