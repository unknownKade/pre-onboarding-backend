package com.wanted.preonboardingbackend.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    NOT_FOUND(HttpStatus.NOT_FOUND, "정보를 찾지 못했습니다.")
    , NOT_ACCEPTABLE(HttpStatus.NOT_ACCEPTABLE, "요청을 확인하십시오.")
    , CONFLICT(HttpStatus.CONFLICT, "입력한 정보를 확인하십시오.")
    , INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류가 났습니다.")
    ;

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message){
        this.status = status;
        this.message = message;
    }
}
