package com.matheus.fooddeliveryapi.domain.exception;

public class CuisineNotFoundException extends EntityNotFoundException{
    public CuisineNotFoundException(String message) {
        super(message);
    }

    public CuisineNotFoundException(Long cuisineId) {
        this(String.format("Cuisine with id %d doesn't exist", cuisineId));
    }
}
