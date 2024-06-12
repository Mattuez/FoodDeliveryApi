package com.matheus.fooddeliveryapi.domain.exception;

public class RestaurantNotFoundException extends EntityNotFoundException{
    public RestaurantNotFoundException(String message) {
        super(message);
    }

    public RestaurantNotFoundException(Long restaurantId) {
        this(String.format("Restaurant with id %d doesn't exist", restaurantId));
    }
}
