package com.matheus.fooddeliveryapi.core.validation.validator;

import com.matheus.fooddeliveryapi.core.validation.annotation.Multiple;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class MultipleValidator implements ConstraintValidator<Multiple, Number> {

    private int multipleNumber;

    @Override
    public void initialize(Multiple constraintAnnotation) {
        this.multipleNumber = constraintAnnotation.number();
    }

    @Override
    public boolean isValid(Number value, ConstraintValidatorContext constraintValidatorContext) {
        boolean valid = true;

        if (value != null) {
            BigDecimal bigDecimalValue = BigDecimal.valueOf(value.doubleValue());
            BigDecimal bigDecimalMultipleNumber = BigDecimal.valueOf(multipleNumber);
            BigDecimal remainder = bigDecimalValue.remainder(bigDecimalMultipleNumber);

            valid = remainder.compareTo(BigDecimal.ZERO) == 0;
        }

        return valid;
    }
}
