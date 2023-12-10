package com.example.cloudproject.reviewapiserver.exception.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ImageExceptionType implements BaseExceptionType {

    IMAGE_NOT_FOUND("I001", HttpStatus.BAD_REQUEST, "이미지를 찾을 수 없습니다."),
    IMAGE_EXIST("I002", HttpStatus.BAD_REQUEST, "이미 등록된 이미지 경로입니다."),
    UPLOAD_FAILURE("I003", HttpStatus.INTERNAL_SERVER_ERROR, "이미지 업로드를 실패했습니다."),
    DOWNLOAD_FAILURE("I004", HttpStatus.INTERNAL_SERVER_ERROR, "이미지 다운로드를 실패했습니다.");

    private final String errorCode;
    private final HttpStatus httpStatus;
    private final String message;

}
