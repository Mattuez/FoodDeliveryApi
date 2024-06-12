package com.matheus.fooddeliveryapi.domain.exception;

public class OrderNotFoundException extends EntityNotFoundException {

    public OrderNotFoundException(String code) {
        super((String.format("Order with id %s doesn't exist", code)));
    }

    public OrderNotFoundException(Long orderId) {
        super((String.format("Order with id %d doesn't exist", orderId)));
    }
}
