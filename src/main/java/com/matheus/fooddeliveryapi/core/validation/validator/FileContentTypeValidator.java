package com.matheus.fooddeliveryapi.core.validation.validator;


import com.matheus.fooddeliveryapi.core.validation.annotation.FileContentType;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class FileContentTypeValidator implements ConstraintValidator<FileContentType, MultipartFile> {

    private List<String> validTypes;

    @Override
    public void initialize(FileContentType constraintAnnotation) {
        this.validTypes = List.of(constraintAnnotation.allowedMediaType());
    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext constraintValidatorContext) {
        return validTypes.contains(multipartFile.getContentType());
    }
}
