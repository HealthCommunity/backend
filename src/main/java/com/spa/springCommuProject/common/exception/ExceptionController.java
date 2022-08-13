package com.spa.springCommuProject.common.exception;

import com.spa.springCommuProject.common.CommonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<CommonResponse<?>> loginExceptionHandler(RuntimeException exception) {
        return ResponseEntity.badRequest().body(CommonResponse.from(exception));
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<CommonResponse<?>> joinExceptionHandler(IllegalStateException exception) {
        return ResponseEntity.badRequest().body(CommonResponse.from(exception));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<CommonResponse<?>> loginExceptionHandler(NoSuchElementException exception) {
        return ResponseEntity.badRequest().body(CommonResponse.from(exception));
    }

}
