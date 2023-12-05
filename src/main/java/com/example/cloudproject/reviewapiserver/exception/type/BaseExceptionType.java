package com.example.cloudproject.reviewapiserver.exception.type;

import org.springframework.http.HttpStatus;

public interface BaseExceptionType {

    String getErrorCode();
    HttpStatus getHttpStatus();
    String getMessage();

}
