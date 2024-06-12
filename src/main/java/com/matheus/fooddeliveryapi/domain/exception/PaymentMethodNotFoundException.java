package com.matheus.fooddeliveryapi.domain.exception;

public class PaymentMethodNotFoundException extends EntityNotFoundException{
    public PaymentMethodNotFoundException(String message) {
        super(message);
    }

    public PaymentMethodNotFoundException(Long paymentMethodId) {
        this(String.format("Payment Method with id %d doesn't exist", paymentMethodId));
    }
}
