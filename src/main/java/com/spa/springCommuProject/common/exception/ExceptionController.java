package com.spa.springCommuProject.common.exception;

import com.spa.springCommuProject.common.CommonResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception exception, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity.ok().body(CommonResponse.from(new NoElementException()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<CommonResponse<?>> loginExceptionHandler(RuntimeException exception) {
        return ResponseEntity.ok().body(CommonResponse.from(exception));
    }

    @ExceptionHandler(JoinException.class)
    public ResponseEntity<CommonResponse<?>> joinExceptionHandler(JoinException exception) {
        return ResponseEntity.ok().body(CommonResponse.from(exception));
    }

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<CommonResponse<?>> loginExceptionHandler(LoginException exception) {
        return ResponseEntity.ok().body(CommonResponse.from(exception));
    }

    @ExceptionHandler(NotFoundSelectException.class)
    public ResponseEntity<CommonResponse<?>> selectExceptionHandler(NotFoundSelectException exception) {
        return ResponseEntity.ok().body(CommonResponse.from(exception));
    }

    @ExceptionHandler(ElseException.class)
    public ResponseEntity<CommonResponse<?>> elseExceptionHandler(ElseException exception) {
        return ResponseEntity.ok().body(CommonResponse.from(exception));
    }

    @ExceptionHandler(NoThreePowerException.class)
    public ResponseEntity<CommonResponse<?>> threePowerExceptionHandler(NoThreePowerException exception) {
        return ResponseEntity.ok().body(CommonResponse.from(exception));
    }

    @ExceptionHandler(NoElementException.class)
    public ResponseEntity<CommonResponse<?>> elementExceptionHandler(NoElementException exception) {
        return ResponseEntity.ok().body(CommonResponse.from(exception));
    }

}
