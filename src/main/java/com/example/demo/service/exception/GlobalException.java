package com.example.demo.service.exception;

import com.example.demo.domain.RestReponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(value = idInvalidException.class)
    public ResponseEntity<RestReponse<Object>> handelIdInvalidException(idInvalidException idInvalidException) {
        RestReponse<Object> res = new RestReponse<Object>();
        res.setStatusCode(HttpStatus.BAD_REQUEST.value());
        res.setError("CALL API FAILED");
        res.setMessage(idInvalidException.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }
}
