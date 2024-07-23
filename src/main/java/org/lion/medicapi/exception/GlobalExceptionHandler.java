package org.lion.medicapi.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(APIException.class)
    public ResponseEntity<?> apiException(APIException exception) {
        log.error("api exception", exception);
        return ResponseEntity.status(exception.getHttpStatus()).body(exception.getMessage());
    }
}
