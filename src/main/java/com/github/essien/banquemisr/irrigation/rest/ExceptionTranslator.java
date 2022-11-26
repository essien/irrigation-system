package com.github.essien.banquemisr.irrigation.rest;

import com.github.essien.banquemisr.irrigation.exception.DuplicateLandException;
import com.github.essien.banquemisr.irrigation.exception.LandNotFoundException;
import com.github.essien.banquemisr.irrigation.out.Response;
import com.github.essien.banquemisr.irrigation.out.Status;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
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

    @ExceptionHandler(LandNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response landNotFoundException(LandNotFoundException ex) {
        return Response.builder().withStatus(Status.fail).withMessage("Land not found")
                .withData(Collections.singletonMap("landId", ex.getLandId())).build();
    }

    @ExceptionHandler(DuplicateLandException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response duplicateLandException(DuplicateLandException ex) {
        return Response.builder().withStatus(Status.fail).withMessage("A land with this ID already exists")
                .withData(Collections.singletonMap("landId", ex.getLandId())).build();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response httpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return Response.builder().withStatus(Status.fail).withMessage("Unable to read content. Cross-check request body").build();
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
