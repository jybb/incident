/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package org.example.incident.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author jessica.jia
 * @version : GlobalExceptionHandler.java, v 0.1 2024年11月13日 4:36 pm jessica.jia Exp $
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ClientRequestException.class)
    public ResponseEntity handleCustomException(Exception ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("error", ex.getMessage());

        return new ResponseEntity(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleParameterException(Exception ex) {
        Map<String, Object> body = new HashMap<>();
        MethodArgumentNotValidException methodArgumentNotValidException = (MethodArgumentNotValidException) ex;
        String message = methodArgumentNotValidException.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(","));
        body.put("error", message);

        return new ResponseEntity(body, HttpStatus.BAD_REQUEST);
    }
}