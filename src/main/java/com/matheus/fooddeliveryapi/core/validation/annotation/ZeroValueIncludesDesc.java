package com.matheus.fooddeliveryapi.core.validation.annotation;

import com.matheus.fooddeliveryapi.core.validation.validator.ZeroValueIncludesDescValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(TYPE)
@Retention(RUNTIME)
@Constraint(
        validatedBy = {ZeroValueIncludesDescValidator.class}
)
public @interface ZeroValueIncludesDesc {

    String message() default "Descrição invalida";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String fieldValue();

    String fieldDesc();

    String requiredDesc();
}
