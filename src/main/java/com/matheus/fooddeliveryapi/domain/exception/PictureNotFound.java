package com.matheus.fooddeliveryapi.domain.exception;

public class PictureNotFound extends EntityNotFoundException{
    public PictureNotFound(String message) {
        super(message);
    }

    public PictureNotFound(Long productId, Long restaurantId) {
        this("The product %d from restaurant %d doesn't have picture"
                .formatted(productId, restaurantId));
    }
}
