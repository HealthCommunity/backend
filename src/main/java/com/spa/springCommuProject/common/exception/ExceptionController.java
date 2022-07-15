package com.spa.springCommuProject.common.exception;

import com.spa.springCommuProject.common.CommonResponse;
import java.util.Objects;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<CommonResponse<?>> loginExceptionHandler(RuntimeException exception) {
        return ResponseEntity.badRequest().body(CommonResponse.from(exception));
    }

}
