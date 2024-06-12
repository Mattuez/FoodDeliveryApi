package com.matheus.fooddeliveryapi.domain.exception;

public abstract class EntityNotFoundException extends BusinessException{

    public EntityNotFoundException(String message) {
        super(message);
    }
}
