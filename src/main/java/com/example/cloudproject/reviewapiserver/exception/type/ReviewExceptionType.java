package com.example.cloudproject.reviewapiserver.exception.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ReviewExceptionType implements BaseExceptionType {
    STORE_NOT_FOUND("R001", HttpStatus.NOT_FOUND, "리뷰를 저장할 가게를 찾을 수 없습니다."),
    ONLY_ONE_REVIEW_PER_STORE("R002", HttpStatus.BAD_REQUEST, "가게 당 하나의 리뷰만 작성할 수 있습니다.")
    ;

    private final String errorCode;
    private final HttpStatus httpStatus;
    private final String message;

}
