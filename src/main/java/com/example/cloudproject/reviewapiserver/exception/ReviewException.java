package com.example.cloudproject.reviewapiserver.exception;

import com.example.cloudproject.reviewapiserver.exception.type.BaseExceptionType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewException extends BaseException {

    private BaseExceptionType exceptionType;

}