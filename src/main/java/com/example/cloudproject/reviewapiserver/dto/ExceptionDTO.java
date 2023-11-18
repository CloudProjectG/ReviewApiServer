package com.example.cloudproject.reviewapiserver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ExceptionDTO {

    private String errorCode;
    private String message;

}
