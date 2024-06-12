package com.matheus.fooddeliveryapi.infraestructure.service.report;

public class ReportException extends RuntimeException{

    public ReportException(String message, Throwable cause) {
        super(message, cause);
    }
}
