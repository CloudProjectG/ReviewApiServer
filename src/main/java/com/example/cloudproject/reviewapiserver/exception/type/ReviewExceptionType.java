package com.example.cloudproject.reviewapiserver.exception.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ReviewExceptionType implements BaseExceptionType {
    REVIEW_SAVE_FAILURE("R001", HttpStatus.BAD_REQUEST, "리뷰 저장을 실패했습니다."),
    ONLY_ONE_REVIEW_PER_STORE("R002", HttpStatus.BAD_REQUEST, "가게 당 하나의 리뷰만 작성할 수 있습니다."),
    REVIEW_NOT_FOUND("R003", HttpStatus.BAD_REQUEST, "저장한 리뷰를 찾을 수 없습니다.")
    ;

    private final String errorCode;
    private final HttpStatus httpStatus;
    private final String message;

}
