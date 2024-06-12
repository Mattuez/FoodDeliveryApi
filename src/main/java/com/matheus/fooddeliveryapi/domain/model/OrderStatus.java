package com.matheus.fooddeliveryapi.domain.model;

import java.util.List;

public enum OrderStatus {

    CREATED,
    CONFIRMED(CREATED),
    DELIVERED(CONFIRMED),
    CANCELED(CREATED);

    private final List<OrderStatus> allowedTransitions;

    OrderStatus(OrderStatus... orderStatus) {
        this.allowedTransitions = List.of(orderStatus);
    }

    public boolean canChangeTo(OrderStatus newOrderStatus) {
        return newOrderStatus.allowedTransitions.contains(this);
    }
}
