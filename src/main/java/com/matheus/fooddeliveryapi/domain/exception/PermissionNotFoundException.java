package com.matheus.fooddeliveryapi.domain.exception;

public class PermissionNotFoundException extends EntityNotFoundException{
    public PermissionNotFoundException(String message) {
        super(message);
    }

    public PermissionNotFoundException(Long permissionId) {
        this(String.format("Permission with id %d doesn't exist", permissionId));
    }
}
