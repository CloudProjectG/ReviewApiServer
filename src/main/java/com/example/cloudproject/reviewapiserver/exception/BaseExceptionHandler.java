package com.example.cloudproject.reviewapiserver.exception;

import com.example.cloudproject.reviewapiserver.dto.ExceptionDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BaseExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ExceptionDTO> handleBaseException(BaseException baseException) {
        ExceptionDTO exceptionDTO = ExceptionDTO.builder()
                .errorCode(baseException.getExceptionType().getErrorCode())
                .message(baseException.getExceptionType().getMessage())
                .build();

        return ResponseEntity
                .status(baseException.getExceptionType().getHttpStatus())
                .body(exceptionDTO);
    }

}
