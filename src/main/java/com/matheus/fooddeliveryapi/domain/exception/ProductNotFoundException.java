package com.matheus.fooddeliveryapi.domain.exception;


public class ProductNotFoundException extends EntityNotFoundException{
    public ProductNotFoundException(String message) {
        super(message);
    }

    public ProductNotFoundException(Long productId, Long restaurantId) {
        this("There is no product registration with id %d for the restaurant with code %d"
                .formatted(productId, restaurantId));
    }
}
