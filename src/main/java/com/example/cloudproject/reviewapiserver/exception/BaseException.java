package com.example.cloudproject.reviewapiserver.exception;

import com.example.cloudproject.reviewapiserver.exception.type.BaseExceptionType;

public abstract class BaseException extends RuntimeException {

    public abstract BaseExceptionType getExceptionType();

}
