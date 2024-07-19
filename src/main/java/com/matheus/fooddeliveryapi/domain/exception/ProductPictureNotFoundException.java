package com.matheus.fooddeliveryapi.domain.exception;

public class ProductPictureNotFoundException extends EntityNotFoundException {
    public ProductPictureNotFoundException(String message) {
        super(message);
    }

    public ProductPictureNotFoundException(Long productId, Long restaurantId) {
        this("The product %d from restaurant %d doesn't have picture"
                .formatted(productId, restaurantId));
    }
}
