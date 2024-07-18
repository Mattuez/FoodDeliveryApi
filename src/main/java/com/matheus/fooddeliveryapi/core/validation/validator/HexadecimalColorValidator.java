package com.matheus.fooddeliveryapi.core.validation.validator;

import com.matheus.fooddeliveryapi.core.validation.annotation.HexadecimalColor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class HexadecimalColorValidator implements ConstraintValidator<HexadecimalColor, String> {
    private static final String validCharacters = "0123456789ABCDEF";

    @Override
    public void initialize(HexadecimalColor constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String color, ConstraintValidatorContext constraintValidatorContext) {
        if (color == null) {
            return true;
        }

        if (color.length() != 6) {
            return false;
        }

        for (char character : color.toCharArray()) {
            if (validCharacters.indexOf(Character.toUpperCase(character)) == -1) {
                return false;
            }
        }

        return true;
    }
}
