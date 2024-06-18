package com.matheus.fooddeliveryapi.domain.listener;

import com.matheus.fooddeliveryapi.domain.event.OrderCanceledEvent;
import com.matheus.fooddeliveryapi.domain.model.Order;
import com.matheus.fooddeliveryapi.domain.service.EmailSenderService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class ClientNotificationOrderCanceledListener {

    private EmailSenderService emailSenderService;

    public ClientNotificationOrderCanceledListener(EmailSenderService emailSenderService) {
        this.emailSenderService = emailSenderService;
    }

    @TransactionalEventListener
    public void whenOrderCanceled(OrderCanceledEvent event) {

        Order order = event.getOrder();

        var message = EmailSenderService.Message.builder()
                .subject("%s, %s - Pedido Cancelado".formatted(order.getClient().getName(), order.getRestaurant().getName()))
                .body("email/order-canceled.html")
                .variable("order", order)
                .recipient(order.getClient().getEmail())
                .build();

        emailSenderService.send(message);
    }
}
