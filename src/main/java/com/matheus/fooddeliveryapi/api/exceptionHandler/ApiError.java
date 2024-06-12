package com.matheus.fooddeliveryapi.api.exceptionHandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Getter
public class ApiError {

    private Integer status;
    private String type;
    private String tittle;
    private String detail;

    private String userMessage;
    private OffsetDateTime timeStamp;

    private List<Object> objects;


    @Getter
    @Builder
    public static class Object {
        private String name;
        private String userMessage;

        @Override
        public String toString() {
            return "(name='" + name + '\'' +
                    ", userMessage='" + userMessage + ")";
        }
    }
}
