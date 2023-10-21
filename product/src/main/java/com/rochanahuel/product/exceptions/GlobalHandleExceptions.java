package com.rochanahuel.product.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalHandleExceptions {

    @ExceptionHandler(SkuInUseException.class)
    public ResponseEntity<Map<String, String>> skuInUserExceptionHandler(SkuInUseException ex) {

        Map<String, String> resp = new HashMap<>();

        resp.put("ERROR: ", ex.getMessage());

        return new ResponseEntity<>(resp, HttpStatus.CONFLICT);
    }
    @ExceptionHandler(ProductNotFound.class)
    public ResponseEntity<Map<String, String>> productNotFoundHandler(ProductNotFound ex) {

        Map<String, String> resp = new HashMap<>();

        resp.put("ERROR: ", ex.getMessage());

        return new ResponseEntity<>(resp, HttpStatus.NOT_FOUND);
    }
}
