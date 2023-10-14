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
        return new ResponseEntity<>(
                ErrorCode.NOT_FOUND.getMessage()
                ,ErrorCode.NOT_FOUND.getStatus()
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationError(MethodArgumentNotValidException e){
        log.error("{} : {}", this.getClass().getSimpleName(),e.getMessage());
        return new ResponseEntity<>(
                e.getBindingResult().getAllErrors().get(0).getDefaultMessage()
                ,ErrorCode.NOT_ACCEPTABLE.getStatus()
        );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolation(DataIntegrityViolationException e){
        log.error("{} : {}", e.getClass().getSimpleName(), e.getMessage());
        return new ResponseEntity<>(
                ErrorCode.CONFLICT.getMessage()
                ,ErrorCode.CONFLICT.getStatus()
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e){
        log.error("{} : {}", e.getClass().getSimpleName(), e.getMessage());
        return new ResponseEntity<>(
                ErrorCode.INTERNAL_ERROR.getMessage()
                ,ErrorCode.INTERNAL_ERROR.getStatus()
        );
    }



}
