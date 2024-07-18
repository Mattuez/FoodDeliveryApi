package com.matheus.fooddeliveryapi.core.validation.annotation;

import com.matheus.fooddeliveryapi.core.validation.validator.HexadecimalColorValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(
        validatedBy = {HexadecimalColorValidator.class}
)
public @interface HexadecimalColor {
    String message() default "Must be the 6 digits of a Hexadecimal color or null";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
