package com.github.essien.banquemisr.irrigation.rest;

import com.github.essien.banquemisr.irrigation.out.Response;
import com.github.essien.banquemisr.irrigation.out.Status;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author bodmas
 * @since Nov 25, 2022.
 */
@RestControllerAdvice
public class ExceptionTranslator {

    private static final Logger log = LoggerFactory.getLogger(ExceptionTranslator.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> processServiceException(Exception e) {
        log.warn("", e);
        return ResponseEntity.badRequest().body(Response.builder().withMessage(e.getMessage()).withStatus(Status.fail).build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public Response methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<org.springframework.validation.FieldError> fieldErrors = result.getFieldErrors();

        return processFieldErrors(fieldErrors);
    }

    private Response processFieldErrors(List<FieldError> fieldErrors) {
        for (org.springframework.validation.FieldError fieldError : fieldErrors) {
            return Response.builder().withStatus(Status.fail).withMessage(fieldError.getDefaultMessage()).build();
        }
        return null;
    }
}