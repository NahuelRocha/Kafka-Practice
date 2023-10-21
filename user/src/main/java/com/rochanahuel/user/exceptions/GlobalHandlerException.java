package com.rochanahuel.user.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(EmailCurrentlyInUse.class)
    public ResponseEntity<Map<String, String>> handleEmailCurrentlyInUseEx(EmailCurrentlyInUse ex) {

        Map<String, String> resp = new HashMap<>();

        resp.put("ERROR: ", ex.getMessage());

        return new ResponseEntity<>(resp, HttpStatus.CONFLICT);
    }
}
