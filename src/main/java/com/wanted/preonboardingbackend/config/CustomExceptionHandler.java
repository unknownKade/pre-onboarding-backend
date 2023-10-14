package com.wanted.preonboardingbackend.config;

import lombok.extern.slf4j.Slf4j;
import com.wanted.preonboardingbackend.common.ErrorCode;
import com.wanted.preonboardingbackend.common.DataException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(DataException.DataNotFound.class)
    public ResponseEntity<String> handleDataNotFound(DataException.DataNotFound e){
        log.error("{} : {}", this.getClass().getSimpleName(),e.getMessage());
        return ResponseEntity
                .status(ErrorCode.NOT_FOUND.getStatus())
                .body(ErrorCode.NOT_FOUND.getMessage());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationError(MethodArgumentNotValidException e){
        log.error("{} : {}", this.getClass().getSimpleName(),e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        return ResponseEntity
                .status(ErrorCode.NOT_ACCEPTABLE.getStatus())
                .body(ErrorCode.NOT_ACCEPTABLE.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolation(DataIntegrityViolationException e){
        log.error("{} : {}", e.getClass().getSimpleName(), e.getMessage());
        return ResponseEntity
                .status(ErrorCode.CONFLICT.getStatus())
                .body(ErrorCode.CONFLICT.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e){
        log.error("{} : {}", e.getClass().getSimpleName(), e.getMessage());
        return ResponseEntity
                .status(ErrorCode.INTERNAL_ERROR.getStatus())
                .body(ErrorCode.INTERNAL_ERROR.getMessage());
    }



}
