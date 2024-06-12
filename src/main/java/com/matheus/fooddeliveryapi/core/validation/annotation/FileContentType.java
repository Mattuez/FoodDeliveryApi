package com.matheus.fooddeliveryapi.core.validation.annotation;

import com.matheus.fooddeliveryapi.core.validation.validator.FileContentTypeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(
        validatedBy = {FileContentTypeValidator.class}
)
public @interface FileContentType {

    String message() default "Arquivo deve ser JPG ou JPEG";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default{};

    String[] allowedMediaType();
}
