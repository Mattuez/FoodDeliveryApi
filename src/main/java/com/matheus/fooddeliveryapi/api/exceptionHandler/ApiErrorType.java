package com.matheus.fooddeliveryapi.api.exceptionHandler;

import lombok.Getter;

@Getter
public enum ApiErrorType {

    RESOURCE_NOT_FOUND("/resource-not-found", "Resource not found"),
    ENTITY_BEING_USED("/entity-being-used", "Entity being used"),
    BUSINESS_ERROR("/business-error", "A business rule was violated"),
    MESSAGE_NOT_READABLE("/message-not-readable", "Message not readable"),
    INVALID_PROPERTY("/invalid-property", "Body has a invalid property"),
    INVALID_PARAMETER("/invalid-parameter", "Invalid parameter in the URL"),
    SYSTEM_ERROR("/system-error", "A system error occurred"),
    ACCESS_DENIED("/access-denied", "Access denied"),
    INVALID_DATA("/invalid-data", "Invalid Data");

    private String uri;
    private String tittle;

    ApiErrorType(String path, String tittle) {
        this.uri = "https://fooddeliveryapi.com.br" + path;
        this.tittle = tittle;
    }
}
