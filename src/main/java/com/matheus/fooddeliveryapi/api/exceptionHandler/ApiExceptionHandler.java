package com.matheus.fooddeliveryapi.api.exceptionHandler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import com.matheus.fooddeliveryapi.core.validation.ValidationException;
import com.matheus.fooddeliveryapi.domain.exception.BusinessException;
import com.matheus.fooddeliveryapi.domain.exception.EntityBeingUsedException;
import com.matheus.fooddeliveryapi.domain.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String GENERIC_ERROR_MESSAGE_FINAL_USER = "An unexpected system error has occurred. " +
            "Try again and if the problem persists, contact your system administrator";

    private MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,
                                                                      HttpHeaders headers, HttpStatus status,
                                                                      WebRequest request) {
        return ResponseEntity.status(status).headers(headers).build();
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
                                                                   HttpStatus status, WebRequest request) {
        String detail = String.format(
                "The resource '%s', that you tried to access, doesn't exist.", ex.getRequestURL()
        );
        ApiError error = createApiErrorBuilder(status, ApiErrorType.RESOURCE_NOT_FOUND, detail).build();

        return handleExceptionInternal(ex, error, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        Throwable rootCause = ExceptionUtils.getRootCause(ex);

        if (rootCause instanceof InvalidFormatException) {
            return handleInvalidFormatException((InvalidFormatException) rootCause, headers, status, request);
        } if (rootCause instanceof PropertyBindingException) {
            return handlePropertyBindingException((PropertyBindingException) rootCause, headers, status, request);
        }

        ApiErrorType errorType= ApiErrorType.MESSAGE_NOT_READABLE;
        String detail = "The request body is invalid. Check for syntax errors.";
        ApiError error = createApiErrorBuilder(status, errorType, detail)
                .userMessage(GENERIC_ERROR_MESSAGE_FINAL_USER)
                .build();

        return handleExceptionInternal(ex, error, headers, status, request);
    }

    private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex, HttpHeaders headers,
                                                                HttpStatus status, WebRequest request) {
        String reference = ex.getPath().stream()
                .map(ref -> ref.getFieldName())
                .collect(Collectors.joining("."));

        ApiErrorType errorType = ApiErrorType.MESSAGE_NOT_READABLE;
        String detail = String.format("The property '%s' received the value '%s', that it's not a valid type. " +
                "Replace it with a value compatible with a '%s' type.",
                reference, ex.getValue() ,ex.getTargetType().getSimpleName());

        ApiError error = createApiErrorBuilder(status, ApiErrorType.MESSAGE_NOT_READABLE, detail)
                .userMessage(GENERIC_ERROR_MESSAGE_FINAL_USER)
                .build();

        return handleExceptionInternal(ex, error, headers, status, request);
    }

    private ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        String reference = ex.getPath().stream()
                .map(ref -> ref.getFieldName())
                .collect(Collectors.joining("."));

        ApiErrorType errorType = ApiErrorType.INVALID_PROPERTY;
        String detail = String.format("The property '%s' is not valid, remove it.", reference);

        ApiError error = createApiErrorBuilder(status, errorType, detail)
                .userMessage(GENERIC_ERROR_MESSAGE_FINAL_USER)
                .build();

        return handleExceptionInternal(ex, error, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
                                                        HttpStatus status, WebRequest request) {

        if (ex instanceof  MethodArgumentTypeMismatchException) {
            return handleMethodArgumentTypeMismatchException(
                    (MethodArgumentTypeMismatchException) ex, headers, status, request
            );
        }

        return super.handleTypeMismatch(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {

        return handleValidationInternal(ex, headers, status, request, ex.getBindingResult());
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status,
                                                         WebRequest request) {

        return handleValidationInternal(ex, headers, status, request, ex.getBindingResult());
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handleValidationException(ValidationException ex, WebRequest request) {

        return handleValidationInternal(ex, new HttpHeaders(), HttpStatus.BAD_REQUEST, request, ex.getBindingResult());
    }

    public ResponseEntity<Object> handleValidationInternal(Exception ex,
                                                           HttpHeaders headers, HttpStatus status,
                                                           WebRequest request, BindingResult bindingResult) {

        ApiErrorType errorType = ApiErrorType.INVALID_DATA;
        String detail = "One or more fields are not valid. Please fill in correctly and try again.";

        List<ApiError.Object> apiErrorObjects = bindingResult.getAllErrors().stream()
                .map(objectError -> {
                    String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());

                    String name = objectError.getObjectName();

                    if (objectError instanceof FieldError) {
                        name = ((FieldError) objectError).getField();
                    }

                    return ApiError.Object.builder()
                            .name(name)
                            .userMessage(message)
                            .build();
                }).toList();

        ApiError error = createApiErrorBuilder(status, errorType, detail)
                .userMessage(detail)
                .objects(apiErrorObjects)
                .build();

        return handleExceptionInternal(ex, error, headers, status, request);
    }

    public ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex,
                                                                            HttpHeaders headers, HttpStatus status,
                                                                            WebRequest request) {

        ApiErrorType errorType = ApiErrorType.INVALID_PARAMETER;
        String detail = String.format("The url parameter '%s' was assigned the value '%s', which is an invalid type. " +
                        "Correct it and enter a value compatible with type '%s'.",
                ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());

        ApiError error = createApiErrorBuilder(status, errorType, detail)
                .userMessage(GENERIC_ERROR_MESSAGE_FINAL_USER)
                .build();

        return handleExceptionInternal(ex, error, headers, status, request);
    }


    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {

        HttpStatus status = HttpStatus.FORBIDDEN;
        ApiErrorType errorType = ApiErrorType.ACCESS_DENIED;
        String detail = ex.getMessage();

        ApiError error = createApiErrorBuilder(status, errorType, detail)
                .userMessage("You dont have credentials to do this")
                .build();

        return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {

        HttpStatus status = HttpStatus.NOT_FOUND;
        ApiErrorType errorType = ApiErrorType.RESOURCE_NOT_FOUND;
        String detail = ex.getMessage();

        ApiError error = createApiErrorBuilder(status, errorType, detail)
                .userMessage(ex.getMessage())
                .build();

        return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleBusinessException(BusinessException ex, WebRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        ApiErrorType type = ApiErrorType.BUSINESS_ERROR;
        String detail = ex.getMessage();

        ApiError error = createApiErrorBuilder(status, type, detail)
                .userMessage(ex.getMessage())
                .build();

        return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntityBeingUsedException.class)
    public ResponseEntity<Object> handleEntityBeingUsedException(EntityBeingUsedException ex, WebRequest request) {

        HttpStatus status = HttpStatus.CONFLICT;
        ApiErrorType errorType = ApiErrorType.ENTITY_BEING_USED;
        String detail = ex.getMessage();

        ApiError error = createApiErrorBuilder(status, errorType, detail)
                .userMessage(ex.getMessage())
                .build();

        return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ApiErrorType errorType = ApiErrorType.SYSTEM_ERROR;

        ApiError error = createApiErrorBuilder(status, errorType, GENERIC_ERROR_MESSAGE_FINAL_USER).build();

        ex.printStackTrace();

        return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {
        if (body == null) {
            body = ApiError.builder()
                    .tittle(status.getReasonPhrase())
                    .status(status.value())
                    .userMessage(GENERIC_ERROR_MESSAGE_FINAL_USER)
                    .timeStamp(OffsetDateTime.now())
                    .build();
        } else if (body instanceof String) {
            body = ApiError.builder()
                    .tittle((String) body)
                    .status(status.value())
                    .userMessage(GENERIC_ERROR_MESSAGE_FINAL_USER)
                    .timeStamp(OffsetDateTime.now())
                    .build();
        }

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    private ApiError.ApiErrorBuilder createApiErrorBuilder(HttpStatus status, ApiErrorType type, String detail) {
        return ApiError.builder()
                .status(status.value())
                .type(type.getUri())
                .tittle(type.getTittle())
                .detail(detail)
                .timeStamp(OffsetDateTime.now());
    }
}
