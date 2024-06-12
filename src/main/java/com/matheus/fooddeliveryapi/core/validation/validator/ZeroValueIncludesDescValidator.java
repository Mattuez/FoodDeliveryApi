package com.matheus.fooddeliveryapi.core.validation.validator;

import com.matheus.fooddeliveryapi.core.validation.annotation.ZeroValueIncludesDesc;
import org.springframework.beans.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;
import java.math.BigDecimal;

public class ZeroValueIncludesDescValidator  implements ConstraintValidator<ZeroValueIncludesDesc, Object> {

    private String fieldValue;
    private String fieldDesc;
    private String requiredDesc;


    @Override
    public void initialize(ZeroValueIncludesDesc constraint) {
        this.fieldValue = constraint.fieldValue();
        this.fieldDesc = constraint.fieldDesc();
        this.requiredDesc = constraint.requiredDesc();
    }

    @Override
    public boolean isValid(Object objectToValidate, ConstraintValidatorContext constraintValidatorContext) {
        boolean valid = true;

        try {
            BigDecimal value = (BigDecimal) BeanUtils
                    .getPropertyDescriptor(objectToValidate.getClass(), fieldValue)
                    .getReadMethod()
                    .invoke(objectToValidate);

            String desc = (String) BeanUtils
                    .getPropertyDescriptor(objectToValidate.getClass(), fieldDesc)
                    .getReadMethod()
                    .invoke(objectToValidate);

            if (value != null && value.compareTo(BigDecimal.ZERO) ==  0 && desc != null) {
                valid = desc.toLowerCase().contains(requiredDesc.toLowerCase());
            }
        } catch (Exception e) {
            throw new ValidationException(e);
        }

        return valid;
    }
}
