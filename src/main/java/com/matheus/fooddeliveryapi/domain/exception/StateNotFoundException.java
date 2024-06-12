package com.matheus.fooddeliveryapi.domain.exception;

public class StateNotFoundException extends EntityNotFoundException{
    public StateNotFoundException(String message) {
        super(message);
    }

    public StateNotFoundException(Long stateId) {
        this(String.format("State with id %d doesn't exist", stateId));
    }
}
