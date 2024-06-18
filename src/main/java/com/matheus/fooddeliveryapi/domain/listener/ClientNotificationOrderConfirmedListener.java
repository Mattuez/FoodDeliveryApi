package com.matheus.fooddeliveryapi.domain.listener;

import com.matheus.fooddeliveryapi.domain.event.OrderConfirmedEvent;
import com.matheus.fooddeliveryapi.domain.model.Order;
import com.matheus.fooddeliveryapi.domain.service.EmailSenderService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class ClientNotificationOrderConfirmedListener {

    private EmailSenderService emailSenderService;

    public ClientNotificationOrderConfirmedListener(EmailSenderService emailSenderService) {
        this.emailSenderService = emailSenderService;
    }

    @TransactionalEventListener
    public void whenOrderConfirmed(OrderConfirmedEvent event) {

        Order order = event.getOrder();

        var message = EmailSenderService.Message.builder()
                .subject("%s, %s - Pedido Confirmado".formatted(order.getClient().getName(), order.getRestaurant().getName()))
                .body("email/order-confirmed.html")
                .variable("order", order)
                .recipient(order.getClient().getEmail())
                .build();

        emailSenderService.send(message);
    }
}
