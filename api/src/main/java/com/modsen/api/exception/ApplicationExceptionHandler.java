package com.modsen.api.exception;

import com.modsen.core.service.exception.InvalidMeetingException;
import com.modsen.core.service.exception.NotFoundEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {
    private static final List<String> AVAILABLE_LOCALES = Arrays.asList("en_US", "ru_RU");
    private static final Locale DEFAULT_LOCALE = new Locale("en", "US");


    private final ResourceBundleMessageSource bundleMessageSource;

    @Autowired
    public ApplicationExceptionHandler(ResourceBundleMessageSource bundleMessageSource) {
        this.bundleMessageSource = bundleMessageSource;
    }

    private ExceptionResponseBody buildExceptionResponseBody(String errorMessage, String errorCode){
        return new ExceptionResponseBody(errorMessage, errorCode);
    }

    private ExceptionResponseBody buildExceptionResponseBody(String errorMessage, String errorCode, Object invalidValue){
        return new ExceptionResponseBody(errorMessage, errorCode, invalidValue);
    }

    private String localize(String message, Locale locale){
        if (!AVAILABLE_LOCALES.contains(locale.toString())){
            locale = DEFAULT_LOCALE;
        }
        return bundleMessageSource.getMessage(message,null,locale);
    }


    @ExceptionHandler(NotFoundEntityException.class)
    public ResponseEntity<ExceptionResponseBody> notFoundEntityHandler(NotFoundEntityException e, Locale locale){
        return  ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(buildExceptionResponseBody(localize(e.getMessage(),locale), "40401", e.getInvalidValue()));
    }

    @ExceptionHandler(InvalidMeetingException.class)
    public ResponseEntity<ExceptionResponseBody> invalidMeetingHandler(InvalidMeetingException e, Locale locale){
        System.out.println("invalidMeetingHandler");
        return  ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(buildExceptionResponseBody(localize(e.getMessage(),locale), "42201"));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ExceptionResponseBody> noHandlerFoundExceptionHandler(MethodArgumentTypeMismatchException e){
        String errorMessage = "invalid.request";
        return new ResponseEntity<>(buildExceptionResponseBody(localize(errorMessage, DEFAULT_LOCALE),"40010"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponseBody> exceptionHandler(Exception e){
        String errorMessage = "server.error.message";
        return new ResponseEntity<>(buildExceptionResponseBody(localize(errorMessage, DEFAULT_LOCALE),"5001"), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
