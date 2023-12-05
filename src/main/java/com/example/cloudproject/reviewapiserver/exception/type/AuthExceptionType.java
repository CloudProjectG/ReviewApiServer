package com.example.cloudproject.reviewapiserver.exception.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AuthExceptionType implements BaseExceptionType {
    UNAUTHORIZED_TOKEN("A001", HttpStatus.UNAUTHORIZED, "인증에 실패했습니다.")
    ;

    private String errorCode;
    private HttpStatus httpStatus;
    private String message;

}
