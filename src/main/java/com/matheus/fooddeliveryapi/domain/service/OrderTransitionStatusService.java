package com.matheus.fooddeliveryapi.domain.service;

import com.matheus.fooddeliveryapi.domain.model.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderTransitionStatusService {

    private OrderEmissionService orderEmissionService;
    private EmailSenderService emailSenderService;

    public OrderTransitionStatusService(OrderEmissionService orderEmissionService,
                                        EmailSenderService emailSenderService) {
        this.orderEmissionService = orderEmissionService;
        this.emailSenderService = emailSenderService;
    }

    @Transactional
    public void confirm(String code) {
        Order order = orderEmissionService.search(code);
        order.confirm();

        var message = EmailSenderService.Message.builder()
                .subject("%s, %s - Pedido Confirmado".formatted(order.getClient().getName(), order.getRestaurant().getName()))
                .body("order-confirmed.html")
                .variable("order", order)
                .recipient(order.getClient().getEmail())
                .build();

        emailSenderService.send(message);
    }

    @Transactional
    public void deliver(String code) {
        Order order = orderEmissionService.search(code);

        order.deliver();
    }

    @Transactional
    public void cancel(String code) {
        Order order = orderEmissionService.search(code);

        order.cancel();
    }
}
