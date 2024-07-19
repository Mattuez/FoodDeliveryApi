package com.matheus.fooddeliveryapi.domain.exception;

public class CuisinePictureNotFoundException extends EntityNotFoundException {

    public CuisinePictureNotFoundException(String message) {
        super(message);
    }

    public CuisinePictureNotFoundException(Long cuisineId) {
        this("The cuisine with id %d doesn't have a picture".formatted(cuisineId));
    }
}
