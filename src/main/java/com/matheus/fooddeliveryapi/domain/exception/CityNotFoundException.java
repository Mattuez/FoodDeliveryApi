package com.matheus.fooddeliveryapi.domain.exception;

public class CityNotFoundException extends EntityNotFoundException{
    public CityNotFoundException(String message) {
        super(message);
    }

    public CityNotFoundException(Long cityId) {
        this(String.format("City with id %d doesn't exist", cityId));
    }
}
