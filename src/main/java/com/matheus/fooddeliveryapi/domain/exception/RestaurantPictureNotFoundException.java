package com.matheus.fooddeliveryapi.domain.exception;

public class RestaurantPictureNotFoundException extends EntityNotFoundException {

    public RestaurantPictureNotFoundException(String message) {
        super(message);
    }

    public RestaurantPictureNotFoundException(Long restaurantId) {
        this("The restaurant with id %d doesn't have a picture".formatted(restaurantId));
    }
}
