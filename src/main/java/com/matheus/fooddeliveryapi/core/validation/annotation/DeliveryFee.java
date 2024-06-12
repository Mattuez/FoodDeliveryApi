package com.matheus.fooddeliveryapi.core.validation.annotation;

import javax.validation.Constraint;
import javax.validation.OverridesAttribute;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(
        validatedBy = {}
)
@PositiveOrZero
@NotNull
public @interface DeliveryFee {

    @OverridesAttribute(constraint = PositiveOrZero.class, name = "message")
    String message() default "{invalid.DeliveryFee}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
